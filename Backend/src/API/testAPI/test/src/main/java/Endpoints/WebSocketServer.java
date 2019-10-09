package Endpoints;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 *
 * @author Vamsi Krishna Calpakkam Wrote skeleton for code
 * @author Timothy Ngo Implemented game specific methods
 *
 */
@ServerEndpoint("/websocket/{username}")//Add password parameter /{password}
@Component
public class WebSocketServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<String, User> userObjectMap = new HashMap<>();
    private static Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException // Add , @PathParam("password") String psw
    {
        if(checkIfValidAccount(username, "psw")) {//add psw variable here
            onClose(session);//Switch to close method
            logger.info("Entered into Open");

            User newUser = new User(username);//Creates a new user object
            sessionUsernameMap.put(session, username);
            usernameSessionMap.put(username, session);
            userObjectMap.put(username, newUser);

            String message = "User:" + username + " has Joined the Chat";
            broadcast(message);
        }

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        String username = sessionUsernameMap.get(session);

        if(message.contains("sendmymoney")) {
            String[] tokens = message.split(" ");
            double curMoney = Double.parseDouble(tokens[1]);
            broadcastMoney(userObjectMap.get(username).getLobbyId(), username, curMoney);
            return;
        }
        if(message.contains("joinlobby")) {
            String[] tokens = message.split(" ");
            int newLobbyId = Integer.parseInt(tokens[1]);
            userObjectMap.get(username).setLobbyId(newLobbyId);
            broadcast("I have joined lobby " + userObjectMap.get(username).getLobbyId());
            return;
        }
        if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
        {
            String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
            sendMessageToParticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToParticularUser(username, "[DM] " + username + ": " + message);
        }
        else // Message to whole chat
        {
            broadcast(username + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        String message= username + " disconnected";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
    }

    private void sendItemToUser(String username, String itemName, int quantity, double price) {
        try {
            Item returnItem = new Item(itemName, quantity, price);
            String returnString = gson.toJson(returnItem);
            usernameSessionMap.get(username).getAsyncRemote().sendText(returnString);
        } catch (Exception e) {
            logger.info("Exception info: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private void sendMessageToParticularUser(String username, String message)
    {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private static void broadcast(String message)
            throws IOException
    {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void broadcastMoney(int lobbyId, String currentUser, double money) {
        ArrayList<String> userOpponents = new ArrayList<String>();
        List<Map.Entry<String, User>> tempList = userObjectMap.entrySet().stream().filter(x -> x.getValue().getLobbyId() == lobbyId && x.getValue().getUsername() != currentUser).collect(Collectors.toList());
        tempList.forEach(x -> userOpponents.add(x.getValue().getUsername()));
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    if(userOpponents.contains(username))
                        session.getBasicRemote().sendText(currentUser + " has this amount of money: " + String.valueOf(money));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static boolean checkIfValidAccount(String username, String psw) {
        //Call database and check if valid password
        return true;
    }

    private static int numberOfPlayersInLobby(int lobbyId) {
        int count = Math.toIntExact(userObjectMap.entrySet().stream().filter(x -> x.getValue().getLobbyId() == lobbyId).count());
        return count;
    }
}
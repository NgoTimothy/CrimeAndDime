package Endpoints;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 * @author Vamsi Krishna Calpakkam
 *
 */
@ServerEndpoint("/websocket/{username}/{password}")//Add password parameter
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
            @PathParam("username") String username, @PathParam("password") String psw) throws IOException
    {
        if(checkIfValidAccount(username, psw)) {
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

    private static boolean checkIfValidAccount(String username, String psw) {
        //Call database and check if valid password
        return true;
    }
}

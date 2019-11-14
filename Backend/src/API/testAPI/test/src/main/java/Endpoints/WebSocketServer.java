package Endpoints;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 * Sets up WebSocketServer
 * @author Vamsi Krishna Calpakkam Wrote skeleton for code
 * @author Timothy Ngo Implemented game specific methods
 *
 */
@ServerEndpoint("/websocket/{lobbyID}/{username}")
@Component
public class WebSocketServer {

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    private static Map<Integer, Session> lobbyIDSessionMap = new HashMap<>();
    private static Map<Session, Integer> sessionLobbyIDMap = new HashMap<>();
    private static Map<StoreInfo, Session> storeInfoSessionMap = new HashMap<>();
    private static Map<Session, StoreInfo> sessionStoreInfoMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username, @PathParam("lobbyID") Integer lobbyID) throws IOException
    {
        logger.info("Entered into Open");

        StoreInfo newStore = new StoreInfo(username, 10000, 100, false);
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        sessionLobbyIDMap.put(session, lobbyID);
        lobbyIDSessionMap.put(lobbyID, session);
        sessionStoreInfoMap.put(session, newStore);
        storeInfoSessionMap.put(newStore, session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        // Handle new messages
        logger.info("Entered into Message: Got Message:"+message);
        System.out.println(message);
        String username = sessionUsernameMap.get(session);

        if(message.contains("storeInfo"))
            parseStoreInformation(session, message);
        else if (message.contains("sendMyMoney")) {
            String[] tokens = message.split(" ");
            double curMoney = Double.parseDouble(tokens[1]);
            int lobbyID = sessionLobbyIDMap.get(session);
            Session usrSession = usernameSessionMap.get(username);
            broadcastMoney(sessionLobbyIDMap.get(usrSession) , username, curMoney);
            sessionStoreInfoMap.get(session).setNextDay(true);
            if(checkForNextDay(lobbyID)) {
                startNextDay(lobbyID);
            }
            else {
                System.out.println("Did not pass");
            }
            return;
        }
        else {
            Integer lobbyID = sessionLobbyIDMap.get(session);
            System.out.println(username);
            System.out.println(lobbyID);
            broadcast(message, lobbyID);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into Close");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        String message= username + " has left this lobby.";
        broadcast(message, sessionLobbyIDMap.get(session));
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered into Error");
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

    private static void broadcast(String message, Integer lobbyID) {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    if (lobbyID.equals(sessionLobbyIDMap.get(session)))
                    {
                        System.out.println(sessionLobbyIDMap.get(session).toString());
                        session.getBasicRemote().sendText(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void printArr(ArrayList<Item> printableArr) {
        System.out.println(printableArr.size());
        for(int i = 0; i < printableArr.size(); i++) {
            System.out.println(printableArr.get(i).getName() + " " + printableArr.get(i).getQuantity() + " " +
                    printableArr.get(i).getPrice());
        }
    }

    public void parseStoreInformation(Session session, String message) {
        logger.info("Entered into Message: Got Message:" + message);
        Item newItem = new Item();
        StoreInfo curStore = sessionStoreInfoMap.get(session);
        storeInfoSessionMap.remove(curStore);
        sessionStoreInfoMap.remove(session);
        curStore.getList().clear();
        message = message.replace("storeInfo", "");
        message = message.replace("]", "");
        if(message.length() < 2) {
            storeInfoSessionMap.put(curStore, session);
            sessionStoreInfoMap.put(session, curStore);
            return;
        }
        message = message.substring(2);
        String[] tokens = message.split("}");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].length() > 5) { //Do something here
                if (i != 0) {
                    tokens[i] = tokens[i].substring(3);
                }
                tokens[i] = tokens[i].replace("\"{", "");
                tokens[i] = tokens[i].replace("{", "");
                String[] itemFields = tokens[i].split(",");
                for (int j = 0; j < itemFields.length; j++) {
                    if (itemFields[j].contains("name")) {
                        newItem = new Item();
                        itemFields[j] = itemFields[j].substring(7).replace("\"", "");
                        newItem.setName(itemFields[j]);
                    }
                    else if (itemFields[j].contains("quantity")) {
                        itemFields[j] = itemFields[j].substring(11);
                        newItem.setQuantity(Integer.parseInt(itemFields[j]));
                    }
                    else if (itemFields[j].contains("retailCost")) {
                        itemFields[j] = itemFields[j].substring(13);
                        newItem.setPrice(Double.parseDouble(itemFields[j]));
                        curStore.getInventory().addItem(newItem);
                    }
                }
            }
        }
        storeInfoSessionMap.put(curStore, session);
        sessionStoreInfoMap.put(session, curStore);
    }

    public StoreInfo getStoreInfoBySession(Session targetSession) {
        return sessionStoreInfoMap.get(targetSession);
    }

    /**
     * Returns the number of players in a lobby
     * @param lobbyId
     * @return
     */
    private static int numberOfPlayersInLobby(int lobbyId) {
        int count = Math.toIntExact(sessionLobbyIDMap.entrySet().stream().filter(x -> x.getValue() == lobbyId).count());
        return count;
    }

    /**
     * Sends a single users money information to everyone in the same lobby
     * @param lobbyId
     * @param currentUser
     * @param money
     */
    private static void broadcastMoney(int lobbyId, String currentUser, double money) {
        sessionUsernameMap.forEach((session, username) -> {
            synchronized (session) {
                try {
                    int curLobbyId = sessionLobbyIDMap.get(session);
                    if(curLobbyId == lobbyId && username != currentUser)
                        session.getBasicRemote().sendText(currentUser + " has this amount of money: " + String.valueOf(money));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * This method will check if it is a valid account
     * @param username
     * @param psw
     * @return
     */
    private static boolean checkIfValidAccount(String username, String psw) {
        //Call database and check if valid password
        return true;
    }

    private boolean checkForNextDay(int lobbyID) {
        Map<Integer, Session> sameLobbies = lobbyIDSessionMap.entrySet().stream().filter(x -> x.getKey() == lobbyID).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        for(Session s :sameLobbies.values()) {
            StoreInfo tempStore = sessionStoreInfoMap.get(s);
            System.out.println(tempStore.getNextDay() + " Hello");
        }
        return true;
    }

    public void sendCustomers() {};

    public void startNextDay(int lobbyID) {
        System.out.println("Ready for next day");
        Map<Integer, Session> sameLobbies = lobbyIDSessionMap.entrySet().stream().filter(x -> x.getKey() == lobbyID).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        for (Session s : sameLobbies.values()) {
            StoreInfo tempStore = sessionStoreInfoMap.get(s);
            tempStore.setNextDay(false);
        }
        broadcastToLobby(lobbyID, "StartNextDay");
    }

    public void broadcastToLobby(int lobbyID, String msg) {
        Map<Integer, Session> sameLobbies = lobbyIDSessionMap.entrySet().stream().filter(x -> x.getKey() == lobbyID).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        sameLobbies.forEach((lobbyId, session) -> {
            synchronized (session) {
                session.getAsyncRemote().sendText(msg);
            }
        });
    }

}

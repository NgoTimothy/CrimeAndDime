package com.example.demo.websocket;


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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Vamsi Krishna Calpakkam
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
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    
    @OnOpen
    public void onOpen(
    	      Session session, 
    	      @PathParam("username") String username, @PathParam("lobbyID") Integer lobbyID) throws IOException
    {
        logger.info("Entered into Open");
        
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);		
        sessionLobbyIDMap.put(session, lobbyID);
        lobbyIDSessionMap.put(lobbyID, session);
        System.out.println(Integer.toString(lobbyID));
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);
    	Integer lobbyID = sessionLobbyIDMap.get(session);
    	System.out.println(username);
    	System.out.println(lobbyID);
    	
	    	broadcast(message, lobbyID);
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
    
	private void sendMessageToPArticularUser(String username, String message) 
    {	
    	try {
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    private static void broadcast(String message, Integer lobbyID) 
    	      throws IOException 
    {	  
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
}

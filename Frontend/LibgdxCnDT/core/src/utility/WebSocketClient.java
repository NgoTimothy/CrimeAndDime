package utility;

import java.io.IOException;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClient {
	Session userSession = null;
    private MessageHandler messageHandler;
 
    public WebSocketClient(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 
    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }
 
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }
 
    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
            System.out.println(message);
        }
    }
    
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
 
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
    
    public void close() throws IOException
    {
    	this.userSession.close();
    }
 
    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}

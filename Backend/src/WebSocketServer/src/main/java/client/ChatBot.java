/**
 * ChatBot.java
 * http://programmingforliving.com
 */
package client;

import java.net.URI;
 
/**
 * ChatBot
 * @author Jiji_Sasidharan
 */
public class ChatBot {
 
    /**
     * main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        final ChatClientEndpoint clientEndPoint = new ChatClientEndpoint(new URI("ws://localhost:8080/websocket/lobby123/username"));
        clientEndPoint.addMessageHandler(new ChatClientEndpoint.MessageHandler() {
                    public void handleMessage(String message) {
//                        JsonObject jsonObject = Json.createReader(new StringReader(message)).readObject();
//                        String userName = jsonObject.getString("user");
//                        if (!"bot".equals(userName)) {
//                            clientEndPoint.sendMessage(getMessage("Hello " + userName +", How are you?"));
//                            // other dirty bot logic goes here.. :)
//                        }
                    	System.out.println(message);
                    }
                });

        while (true) {
            clientEndPoint.sendMessage("Hi there!");
            Thread.sleep(30000);
        }
    }
}

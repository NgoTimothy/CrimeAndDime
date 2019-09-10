import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<ClientThread> clientThreads;
    private int port;

    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }

    public Server(){
        port = 4444;
    }
    
    private void start()
    {
        clientThreads = new ArrayList<ClientThread>();
        ServerSocket sSocket1 = null;
        try {
            sSocket1 = new ServerSocket(port);
            lookForClients(sSocket1);
        } catch (IOException e){
            System.out.println("Could not listen on port: " + port);
            System.exit(0);
        }
    }

    public List<ClientThread> getClients(){
        return clientThreads;
    }

    private void lookForClients(ServerSocket sSocket1)
    {

        System.out.println(sSocket1.getLocalSocketAddress());
        while(true){
            try{
                Socket socket = sSocket1.accept();
                System.out.println("New User Connected");
                ClientThread client = new ClientThread(this, socket);
                Thread thread = new Thread(client);
                thread.start();
                clientThreads.add(client);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

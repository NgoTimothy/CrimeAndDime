import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
	private Server server1;
    private Socket socket;
    private PrintWriter out;

    public ClientThread(Server server1, Socket socket){
        this.server1 = server1;
        this.socket = socket;
    }

    private PrintWriter getWriter(){
        return out;
    }

    @Override
    public void run() {
        try{
            this.out = new PrintWriter(socket.getOutputStream(), false);
            Scanner scanner = new Scanner(socket.getInputStream());

            while(!socket.isClosed())
            {
                if(scanner.hasNextLine())
                {
                    String input = scanner.nextLine();
                    System.out.println(input);
                    for(ClientThread myClient : server1.getClients())
                    {
                        PrintWriter myClientOut = myClient.getWriter();
                        if(myClientOut != null)
                        {
                            myClientOut.write(input + "\r\n");
                            myClientOut.flush();
                        }
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
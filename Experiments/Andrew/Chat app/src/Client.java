import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private int port;
    private String name;
    private String host;

    public static void main(String[] args)
    {
        String input = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name:");
        while(input == null || input.equals(""))
        {
            input = scanner.nextLine();
            if(input.equals("")){
                System.out.println("Invalid name. Enter your name:");
            }
        }

        Client client = new Client(input);
        client.start(scanner);
    }

    private Client(String name)
    {
    	port = 4444;
        this.name = name;
        host = "localhost";
    }

    private void start(Scanner scanner)
    {
        try{
            Socket socket = new Socket(host, port);
            ServerThread serverThread = new ServerThread(socket, name);
            Thread newThread = new Thread(serverThread);
            newThread.start();
            while(newThread.isAlive())
            {
                if(scanner.hasNextLine())
                    serverThread.addMsg(scanner.nextLine());
            }
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
}
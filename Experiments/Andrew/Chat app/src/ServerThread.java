import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerThread implements Runnable 
{
	private boolean messagesWaiting = false;
    private String name;
    private final ArrayList<String> messages;
    private Socket socket;

    public ServerThread(Socket socket, String name)
    {
        this.socket = socket;
        this.name = name;
        messages = new ArrayList<String>();
    }

    @Override
    public void run(){
        System.out.println("Connected");
        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
            InputStream in = socket.getInputStream();
            Scanner scanner = new Scanner(in);
            while(!socket.isClosed())
            {
                if(in.available() > 0)
                {
                    if(scanner.hasNextLine())
                    {
                    	String nextLine = scanner.nextLine();
                    	Scanner lineScanner = new Scanner(nextLine);
                    	if (!lineScanner.next().equals(name))
                    		System.out.println(nextLine);
                    	lineScanner.close();
                    }
                }
                if(messagesWaiting){
                    String newMessage = "";
                    synchronized(messages)
                    {
                        newMessage = messages.remove(0);
                        messagesWaiting = !messages.isEmpty();
                    }
                    out.println(name + " : " + newMessage);
                    out.flush();
                }
            }
            scanner.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public void addMsg(String message)
    {
        synchronized (messages)
        {
            messagesWaiting = true;
            messages.add(message);
        }
    }
} 
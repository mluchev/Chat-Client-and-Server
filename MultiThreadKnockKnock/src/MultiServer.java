import java.net.*;
import java.io.*;

public class MultiServer {
	
	final static int DEFAULT_PORT = 4455;
	final static int MAX_USERS = 10;
	public static MultiServerThread users[] = null;

    public static void main(String[] args) throws IOException {
    	boolean listening = true;
    	int maxUsers = 0;
    	Socket clientSocket = null;
        ServerSocket serverSocket = null;
        
        
        if(args.length < 2)
        {	
        	maxUsers = MAX_USERS;
        }
        else
        {
        	maxUsers = Integer.parseInt(args[1]);
        }
        
        users = new MultiServerThread[maxUsers];
        
        try {
        	if(args.length < 1)
        	{
        		serverSocket = new ServerSocket(DEFAULT_PORT);
        	}
        } catch (IOException e) {
            System.err.println("Could not listen on this port.");
            System.exit(-1);
        }

        while (listening)
        {
        	for(int i = 0; i <= maxUsers ; i++){
    		    if(users[i]==null)
    			{
    		    	clientSocket = serverSocket.accept();
    		    	users[i] = new MultiServerThread(clientSocket);
    		    	users[i].numInRegistry = i;
    			    users[i].start();
    			    break;
    			}
    		}      	
        }

        serverSocket.close();
    }
}
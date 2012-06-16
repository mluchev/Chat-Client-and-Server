import java.io.*;
import java.net.*;

public class Client implements Runnable {
	
	final static String DEFAULT_HOST = "localhost";
	final static int DEFAULT_PORT = 4455;
	static boolean closed = false;
	static Socket clientSocket = null;
	static PrintWriter out = null;
	static BufferedReader in = null;
	
    public static void main(String[] args) throws IOException {
        try {
        	if(args.length < 2)
        	{
        		clientSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        	}
        	else
        	{
        		clientSocket = new Socket(args[0], Integer.parseInt(args[1]));
        	}
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about this host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to this host.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromUser;

		// Create a thread to read from the server
    	new Thread(new Client()).start();
    	
		while (!closed)
		{
			// this thread just reads from the stdIn and writes to the Server
			fromUser = stdIn.readLine();
			if (fromUser != null) 
			{
		        // System.out.println("Me : " + fromUser);
		        out.println(fromUser);
			}
        }
		
        out.close();
        in.close();
        stdIn.close();
        clientSocket.close();  
    }

	@Override
	public void run() {
		
        String fromServer;
        try {
			while ((fromServer = in.readLine()) != null) {
				if (fromServer.equals("bye"))
			    {
			    	closed = true;
			        System.exit(0);    
			    }
			    System.out.println(fromServer);
			}
		} 
        catch (IOException e) {
        	System.err.println("IOException:  " + e);
		}
	}
}
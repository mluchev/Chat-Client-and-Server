import java.io.*;
import java.net.*;

public class Client {
	
	final static String DEFAULT_HOST = "localhost";
	final static int DEFAULT_PORT = 4455;
	
    public static void main(String[] args) throws IOException {
    	
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
        	if(args.length < 2)
        	{
        		kkSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        	}
        	else
        	{
        		kkSocket = new Socket(args[0], Integer.parseInt(args[1]));
        	}
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about this host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to this host.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        
        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: " + fromServer);
            if (fromServer.equals("Bye."))
                break;
		   
            fromUser = stdIn.readLine();
		    if (fromUser != null) {
	                System.out.println("Me : " + fromUser);
	                out.println(fromUser);
		    }
        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}
import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    public String username = null;
    public int numInRegistry;

    public MultiServerThread(Socket socket) {
		super("MultiServerThread");
		this.socket = socket;
    }

    public void run() {

		try {
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
					    new InputStreamReader(
					    		socket.getInputStream()));
	
		    String inputLine, outputLine;
		    ChatProtocol chatProtocol = new ChatProtocol(this);
		    outputLine = "Please register your username...";
		    out.println(outputLine);
		    
		    while ((inputLine = in.readLine()) != null) 
		    {
		    	outputLine = chatProtocol.processInput(inputLine);
				out.println(outputLine);
				//if (outputLine.equals("Bye"))
				//  break;
		    }
		    out.close();
		    in.close();
		    socket.close();
	
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
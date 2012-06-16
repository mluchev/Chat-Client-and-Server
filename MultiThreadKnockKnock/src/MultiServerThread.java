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
		    PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
					    new InputStreamReader(
					    		this.socket.getInputStream()));
	
		    String inputLine, outputLine;
		    ChatProtocol chatProtocol = new ChatProtocol(this);

		    outputLine = "Please register your username...";
		    out.println(outputLine);
		    
		    while ((inputLine = in.readLine()) != null) 
		    {
		    	outputLine = chatProtocol.processInput(inputLine);
		    	
		    	if (outputLine.equals("bye"))
					  break;
		    	// will be used for list\r\n
		    	if(chatProtocol.receiver == null)
		    	{
		    		out = new PrintWriter(this.socket.getOutputStream(), true);
		    		out.println(outputLine);
		    	}
		    	else
		    	{
		    		// empty string is default value for all users
		    		if(chatProtocol.receiver == "")
		    		{
		    			boolean hasFoundUser = false;
		    			for(int i = 0; i < MultiServer.users.length; i++)
		    			{
		    				if(MultiServer.users[i] != null 
		    						&& MultiServer.users[i].username != null
		    						&& !(MultiServer.users[i].username.equals(this.username)))
		    				{
		    					out = new PrintWriter(MultiServer.users[i].socket.getOutputStream(), true);	
		    					out.println(this.username + ": " + outputLine);
		    					hasFoundUser = true;
		    					
		    				}
		    			}
		    			if(hasFoundUser)
		    			{
		    				out = new PrintWriter(this.socket.getOutputStream(), true);	
	    					out.println("200 ok message sent successfully.");
		    			}
		    			else
		    			{
		    				out = new PrintWriter(this.socket.getOutputStream(), true);	
	    					out.println("100 err server error!");
		    			}
		    			continue;
		    		}
		    		// send to a single user
		    		else
		    		{
		    			boolean hasFoundUser = false;
		    			for(int i = 0; i < MultiServer.users.length; i++)
		    			{
		    				if(MultiServer.users[i] != null 
		    						&& MultiServer.users[i].username != null 
		    						&& MultiServer.users[i].username.equals(chatProtocol.receiver))
		    				{
		    					
		    					out = new PrintWriter(MultiServer.users[i].socket.getOutputStream(), true);	
		    					out.println(this.username + ": " + outputLine);
		    					out = new PrintWriter(this.socket.getOutputStream(), true);	
		    					out.println("200 ok message to " + chatProtocol.receiver + " sent successfully.");
		    					hasFoundUser = true;
		    					break;
		    				}
		    			}
		    			if(!hasFoundUser)
		    			{
			    			out = new PrintWriter(this.socket.getOutputStream(), true);	
	    					out.println("100 err " + chatProtocol.receiver + " does not exist!");
	    					continue;
		    			}
		    			
		    		}
		    	}
		    	
		    }
		    out.close();
		    in.close();
		    this.socket.close();
	
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
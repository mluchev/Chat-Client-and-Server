
public class ChatProtocol {

	private static final int WAITING = 0;
	private static final int WORKING = 1;
	private MultiServerThread thread = null;
    private int state = WAITING;
    
    public ChatProtocol(MultiServerThread thread)
    {
    	this.thread = thread;
    }
    
	public String processInput(String s)
	{
		if(state == WAITING)
		{
			if(s.startsWith("user "))
			{
				String username = s.substring(5);
				for(int i = 0; i < MultiServer.users.length; i++)
				{
					if(MultiServer.users[i] != null &&
							MultiServer.users[i].username != null 
							&& MultiServer.users[i].username.equals(username))
						return "100 err " + username +" already taken!";
				}
				this.thread.username = username;
				state = WORKING;	
				return "200 OK " + username + " successfully registered.";
			}
			else
			{
				return "Try again! Use this format: user <username>";
			}
		}
		else if (state == WORKING)
		{
			
		}
		return "";
	}
}

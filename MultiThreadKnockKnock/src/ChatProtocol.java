
public class ChatProtocol {

	private static final int WAITING_TO_REGISTER = 0;
	private static final int WORKING = 1;
	private MultiServerThread thread = null;
    private int state = WAITING_TO_REGISTER;
    public String receiver = null;
    
    public ChatProtocol(MultiServerThread thread)
    {
    	this.thread = thread;
    }
    
	public String processInput(String s)
	{
		receiver = null;
		
		if(state == WAITING_TO_REGISTER)
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
				MultiServer.users[this.thread.numInRegistry].username = username;
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
			String[] messageParts = s.split(" ");
			String messageType = messageParts[0];
			
			if(messageType.equals("send_to"))
			{
				if(messageParts.length >= 3)
				{
					this.receiver = messageParts[1];
					StringBuilder message = new StringBuilder("");
					for(int i = 2; i < messageParts.length; i++)
					{
						message.append(messageParts[i]);
					}
					return message.toString();
					
				}
				else
				{
					return "Try again! Use this format: send_to <username> <single_line_message>";
				}
				
				
			}
		}
		return "Give me somethong different!";
	}
}

package utility;

public class Player {
	private String username;
	private boolean ready;
	
	public Player(String username)
	{
		this.username = username;
		ready = false;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void readyPlayer()
	{
		ready = true;
	}
	
	public boolean isReady()
	{
		return ready;
	}
}

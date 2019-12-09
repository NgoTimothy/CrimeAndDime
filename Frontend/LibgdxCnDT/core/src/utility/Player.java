package utility;

public class Player {
	private String username;
	private boolean ready;
	
	public Player(String username)
	{
		this.username = username;
		ready = false;
	}
	
	public Player(String username, boolean isReady) {
        this.username = username;
        this.ready = isReady;
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

    public boolean getIsReady() { return ready; }

    public void setIsReady(boolean isReady) { ready = isReady; }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof Player))
            return false;

        Player obj = (Player) o;
        return obj.getUsername() == this.getUsername();
    }
}

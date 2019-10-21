package utility;

public class Lobby {

	private int lobbyID;
	private String lobbyName;
	private int numPlayers;
	
	public Lobby(int lobbyID, String lobbyName, int numPlayers)
	{
		this.lobbyID = lobbyID;
		this.lobbyName = lobbyName;
		this.numPlayers = numPlayers;
	}
	
	public void setLobbyID(int lobbyID)
	{
		this.lobbyID = lobbyID;
	}
	
	public int getLobbyID()
	{
		return lobbyID;
	}
	
	public void setLobbyName(String lobbyName)
	{
		this.lobbyName = lobbyName;
	}
	
	public String getLobbyName()
	{
		return lobbyName;
	}
	
	public void setNumPlayers(int numPlayers)
	{
		this.numPlayers = numPlayers;
	}
	
	public int getNumPlayers()
	{
		return numPlayers;
	}
}

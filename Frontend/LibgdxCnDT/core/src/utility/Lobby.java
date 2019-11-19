package utility;

import java.util.ArrayList;

public class Lobby {

	private int lobbyID;
	private String lobbyName;
	private int numPlayers;
	private ArrayList<String> lobbyUsernames;
	
	public Lobby(int lobbyID, String lobbyName, int numPlayers)
	{
		this.lobbyID = lobbyID;
		this.lobbyName = lobbyName;
		this.numPlayers = numPlayers;
		lobbyUsernames = new ArrayList<String>();
	}
	
	public void setLobbyID(int lobbyID)
	{
		this.lobbyID = lobbyID;
	}
	
	public int getLobbyID() { return lobbyID; }
	
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

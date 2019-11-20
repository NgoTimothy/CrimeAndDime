package utility;

import java.util.ArrayList;

public class Lobby {

	private int lobbyID;
	private String lobbyName;
	private int numPlayers;
	private Player me;
	private ArrayList<Player> players;
	
	public Lobby(int lobbyID, String lobbyName, int numPlayers)
	{
		this.lobbyID = lobbyID;
		this.lobbyName = lobbyName;
		this.numPlayers = numPlayers;
		players = new ArrayList<Player>();
		for(int i = 0; i < numPlayers; i++)
			players.add(new Player("player" + (i + 1)));
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
	
	public void addPlayer(String playerName)
	{
		players.add(new Player(playerName));
	}
	
	public void removePlayer(String playerName)
	{
		numPlayers = numPlayers - 1;
		for (Player p : players)
		{
			if (p.getUsername().equals(playerName))
				players.remove(p);
		}
		
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).setUsername("player" + (i + 1));
		}
	}
	
	public void readyPlayer(String playerName)
	{
		for (Player p : players)
		{
			if (p.getUsername().equals(playerName))
				p.readyPlayer();
		}
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public boolean isLobbyReady()
	{
		if(players.size() == 0)
			return false;
		for(Player p : players)
		{
			if(!p.isReady())
				return false;
		}
		return true;
	}
}

package utility;

import java.util.ArrayList;

public class Lobby {

	private int lobbyID;
	private String lobbyName;
	private int numPlayers;
	private ArrayList<Player> players;
	
	public Lobby(int lobbyID, String lobbyName, int numPlayers)
	{
		this.lobbyID = lobbyID;
		this.lobbyName = lobbyName;
		this.numPlayers = numPlayers;
		players = new ArrayList<Player>();
	}
	
	public Lobby(int lobbyID, String lobbyName)
	{
		this.lobbyID = lobbyID;
		this.lobbyName = lobbyName;
		this.numPlayers = 0;
		players = new ArrayList<Player>();
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
	
	public void addPlayer(Player player)
	{
		players.add(player);
		numPlayers = players.size();
	}
	
	public void addPlayer(String playerName)
	{
		players.add(new Player(playerName));
		numPlayers = players.size();
	}
	
	public void removePlayer(String playerName)
	{
		for (Player p : players)
		{
			if (p.getUsername().equals(playerName))
				players.remove(p);
		}
		numPlayers = players.size();
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
	
	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
		numPlayers = players.size();
	}
	
	public boolean isLobbyReady()
	{
		if(players.size() == 0)
			return false;
		for(Player p : players)
		{
			if(!p.getIsReady())
				return false;
		}
		return true;
	}
	
	
}

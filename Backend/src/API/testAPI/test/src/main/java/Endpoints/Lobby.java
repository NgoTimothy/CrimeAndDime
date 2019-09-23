package Endpoints;

public class Lobby {
    private int lobbyId;
    private String lobbyName;
    private boolean hasPassword;
    private String lobbyPassword;
    private int numberOfPlayers;

    public int getLobbyId() { return lobbyId; }

    public String getLobbyName() { return lobbyName; }

    public boolean getHasPassword() { return hasPassword; }

    public String getLobbyPassword() { return lobbyPassword; }

    public int getNumberOfPlayers() { return  numberOfPlayers; }

    public void setLobbyId(int id) { lobbyId = id; }

    public void setLobbyName(String name) { lobbyName = name; }

    public void setHasPassword(boolean hasPsw) { hasPassword = hasPsw; }

    public void setLobbyPassword(String psw) { lobbyPassword = psw; }

    public void setNumberOfPlayers(int numPlayers) { numberOfPlayers = numPlayers; }

}

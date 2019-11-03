package Endpoints;

/**
 * Lobby class contains key variables from a lobby such as; lobbyId, lobbyName, hasPassword, lobbyPassword, and numberOfPlayers.
 * It is used to store lobby information in an easier way
 */
public class Lobby {
    private int lobbyId;
    private String lobbyName;
    private boolean hasPassword;
    private String lobbyPassword;
    private int numberOfPlayers;

    /**
     *
     * @return Returns lobbyID
     */
    public int getLobbyId() { return lobbyId; }

    /**
     *
     * @return Returns lobbyName
     */
    public String getLobbyName() { return lobbyName; }

    /**
     *
     * @return Returns hasPassword
     */
    public boolean getHasPassword() { return hasPassword; }

    /**
     *
     * @return Returns lobbyPassword
     */
    public String getLobbyPassword() { return lobbyPassword; }

    /**
     *
     * @return Returns numberOfPlayers
     */
    public int getNumberOfPlayers() { return  numberOfPlayers; }

    /**
     * Sets lobbyId variable to int id
     * @param id
     */
    public void setLobbyId(int id) { lobbyId = id; }

    /**
     * Sets lobbyName variable to string name
     * @param name
     */
    public void setLobbyName(String name) { lobbyName = name; }

    /**
     * Sets hasPassword variable to boolean hasPsw
     * @param hasPsw
     */
    public void setHasPassword(boolean hasPsw) { hasPassword = hasPsw; }

    /**
     * Sets lobbyPassword variable to string psw
     * @param psw
     */
    public void setLobbyPassword(String psw) { lobbyPassword = psw; }

    /**
     * Sets numberOfPlayers variable to int numPlayers
     * @param numPlayers
     */
    public void setNumberOfPlayers(int numPlayers) { numberOfPlayers = numPlayers; }

    /**
     * Changes the lobby class into a string
     * @return A String of lobby class
     */
    @Override
    public String toString() {
        return "Lobby [lobbyId=" + lobbyId + ", lobbyName=" + lobbyName + ", numberOfPlayers=" + numberOfPlayers + "]";
    }

}

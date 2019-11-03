package Endpoints;

/**
 * User class contains the key information of a user/player
 */
public class User {
    private String username;
    private int lobbyId;

    /**
     * Creates a user based on a username
     * @param username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     *
     * @return Returns username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return Returns lobbyID
     */
    public int getLobbyId() {
        return lobbyId;
    }

    /**
     * Replace current username with another
     * @param newName
     */
    public void setUsername(String newName) {
        username = newName;
    }

    /**
     * Set lobbyID variable to int newLobbyID
     * @param newLobbyId
     */
    public void setLobbyId(int newLobbyId) {
        lobbyId = newLobbyId;
    }
}

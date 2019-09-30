package Endpoints;

public class User {
    private String username;
    private int lobbyId;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public void setUsername(String newName) {
        username = newName;
    }

    public void setLobbyId(int newLobbyId) {
        lobbyId = newLobbyId;
    }
}

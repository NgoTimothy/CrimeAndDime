package GameClasses;

public class Player {
    private String username;
    private boolean isReady;

    public Player(String username, boolean isReady) {
        this.username = username;
        this.isReady = isReady;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public boolean getIsReady() { return isReady; }

    public void setIsReady(boolean isReady) { this.isReady = isReady; }

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

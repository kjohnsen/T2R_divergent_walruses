package modelclasses;

public class Player {
    private String username;
    private PlayerColor playerColor;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }
}

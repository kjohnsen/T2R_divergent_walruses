package modelclasses;

public class Player {
    private String username;
    private PlayerColor playerColor;

    public Player(String username) {
        this.username = username;
    }
    //the following constructor is used for testing
    public Player(String username, PlayerColor color) {
        this.username = username;
        playerColor = color;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return username.equals(player.getUsername()) &&
                playerColor.equals(player.getPlayerColor());
    }
}

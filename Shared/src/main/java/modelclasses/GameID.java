package modelclasses;

public class GameID {
    private String name;
    private String gameID;

    public GameID(String name, String gameID) {
        this.name = name;
        this.gameID = gameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameID gameID = (GameID) o;
        return this.gameID.equals(gameID.gameID) &&
                this.name.equals(gameID.name);
    }
}

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
}

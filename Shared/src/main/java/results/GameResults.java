package results;

import data.Command;
import modelclasses.GameID;

public class GameResults extends Results {

    GameID gameID;

    public GameResults(GameID gameID) {
        this.gameID = gameID;
    }

    public GameID getGameID() {
        return gameID;
    }

    public void setGameID(GameID gameID) {
        this.gameID = gameID;
    }
}

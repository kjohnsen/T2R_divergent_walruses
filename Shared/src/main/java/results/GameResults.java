package results;

import java.io.Serializable;

import modelclasses.GameName;

public class GameResults extends Results implements Serializable {

    GameName gameName;

    public GameResults(GameName gameName) {
        this.gameName = gameName;
    }

    public GameName getGameName() {
        return gameName;
    }

    public void setGameName(GameName gameName) {
        this.gameName = gameName;
    }
}

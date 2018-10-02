package results;

import modelclasses.GameName;

public class GameResults extends Results {

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

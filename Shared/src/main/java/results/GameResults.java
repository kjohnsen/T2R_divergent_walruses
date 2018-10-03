package results;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameName;

public class GameResults extends Results {

    GameName gameName;

    public GameResults(GameName gameName) {
        this.gameName = gameName;
        this.setClientCommands(new ArrayList<Command>());
    }

    public GameName getGameName() {
        return gameName;
    }

    public void setGameName(GameName gameName) {
        this.gameName = gameName;
    }
}

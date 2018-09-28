package results;

import data.Command;

public class GameResults extends Results {

    String gameID;
    Command[] clientCommands;

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Command[] getClientCommands() {
        return clientCommands;
    }

    public void setClientCommands(Command[] clientCommands) {
        this.clientCommands = clientCommands;
    }
}

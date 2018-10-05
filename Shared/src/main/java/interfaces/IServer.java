package interfaces;

import java.util.ArrayList;
import data.Command;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public interface IServer {
    LoggedInResults loginUser(String username, String password);

    LoggedInResults registerUser(String username, String password);

    GameResults createGame(String name, int numPlayers, String authToken);

    GameResults joinGame(GameName gameName, String authToken);

    GameResults startGame(GameName gameName, String authToken);

    Results chooseColor(PlayerColor color, String authToken);

    ArrayList<Command> getCommands(String clientID, String authToken);

    void setHostIP(String hostIP);

    void setHostPort(String hostPort);
}
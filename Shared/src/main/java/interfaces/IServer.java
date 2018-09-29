package interfaces;

import java.util.ArrayList;
import data.Command;
import modelclasses.GameID;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public interface IServer {
    LoggedInResults loginUser(String username, String password);

    LoggedInResults registerUser(String username, String password);

    GameResults createGame(String name, int numPlayers);

    GameResults joinGame(GameID gameID);

    GameResults startGame(GameID gameID);

    Results chooseColor(PlayerColor color);

    ArrayList<Command> getCommands(String clientID);
}
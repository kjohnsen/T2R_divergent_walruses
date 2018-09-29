package interfaces;

import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.Results;

public interface iClient {
    void loginUser(String username, String password);
    void registerUser(String username, String password);
    void joinGame(Player player, GameID gameID);
    void createGame(GameInfo gameInfo);
    void startGame(GameInfo gameInfo);
    void claimColor(Player player, PlayerColor playerColor);
}

package interfaces;

import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;
import results.Results;

public interface iClient {
    void loginUser(User user, String authToken);
    void registerUser(User user, String authToken);
    void joinGame(Player player, GameID gameID);
    void createGame(GameInfo gameInfo);
    void startGame(GameInfo gameInfo);
    void claimColor(Player player, PlayerColor playerColor);
}

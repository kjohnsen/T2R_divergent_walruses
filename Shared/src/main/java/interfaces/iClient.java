package interfaces;

import modelclasses.GameName;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;

public interface iClient {
    void loginUser(User user, String authToken);
    void registerUser(User user, String authToken);
    void joinGame(Player player, GameName gameName);
    void createGame(GameInfo gameInfo);
    void startGame(GameName gameName);
    void claimColor(String username, PlayerColor playerColor);
}

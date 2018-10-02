package server;

import data.CommandManager;
import interfaces.iClient;
import modelclasses.User;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameName;
import modelclasses.GameInfo;

public class ClientProxy implements iClient {

    @Override
    public void loginUser(User user, String authToken) {
        CommandManager.getInstance().addClient(authToken);
    }

    @Override
    public void registerUser(User user, String authToken) {
        CommandManager.getInstance().addClient(authToken);
    }

    @Override
    public void joinGame(Player player, GameName gameName) {

    }

    @Override
    public void createGame(GameInfo gameInfo) {

    }

    @Override
    public void startGame(GameName gameName) {

    }

    @Override
    public void claimColor(String username, PlayerColor playerColor) {

    }
}

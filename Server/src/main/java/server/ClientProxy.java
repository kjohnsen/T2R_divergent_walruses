package server;

import data.CommandManager;
import interfaces.iClient;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameID;
import modelclasses.GameInfo;

public class ClientProxy implements iClient {

    @Override
    public void loginUser(String username, String password) {
        CommandManager.getInstance().addClient(username);
    }

    @Override
    public void registerUser(String username, String password) {
        CommandManager.getInstance().addClient(username);
    }

    @Override
    public void joinGame(Player player, GameID gameID) {

    }

    @Override
    public void createGame(GameInfo gameInfo) {

    }

    @Override
    public void startGame(GameInfo gameInfo) {

    }

    @Override
    public void claimColor(Player player, PlayerColor playerColor) {

    }
}

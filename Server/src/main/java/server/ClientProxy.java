package server;

import data.CommandManager;
import interfaces.iClient;
import modelclasses.User;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.GameID;
import modelclasses.GameInfo;

public class ClientProxy implements iClient {

    @Override
    public void loginUser(User user, String authToken) {

    }

    @Override
    public void registerUser(User user, String authToken) {

    }

    @Override
    public void joinGame(Player player, GameID gameID) {

    }

    @Override
    public void createGame(GameInfo gameInfo) {

    }

    @Override
    public void startGame(GameID gameID) {

    }

    @Override
    public void claimColor(String username, PlayerColor playerColor) {

    }
}

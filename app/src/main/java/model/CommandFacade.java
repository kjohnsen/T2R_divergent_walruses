package model;

import clientserver.ServerProxy;
import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.User;
import results.Results;

public class CommandFacade implements iClient {

    private static final CommandFacade ourInstance = new CommandFacade();

    public static CommandFacade getInstance() {
        return ourInstance;
    }

    private CommandFacade() {
    }


    @Override
    public void loginUser(User user, String authToken) {
        ClientModel.getInstance().setCurrentUser(user);
        ServerProxy.getInstance().setAuthToken(authToken);
    }

    @Override
    public void registerUser(User user, String authToken) {
        ClientModel.getInstance().setCurrentUser(user);
        ServerProxy.getInstance().setAuthToken(authToken);
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

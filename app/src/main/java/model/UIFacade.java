package model;

import clientserver.ServerPoller;
import clientserver.ServerProxy;
import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.LoggedInResults;
import results.Results;

public class UIFacade implements iClient {

    @Override
    public void loginUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().loginUser(username, password);
        if(loggedInResults != null) {
            //execute the command
        } else {
            //show an error through the model?
        }
    }

    @Override
    public void registerUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().registerUser(username, password);
        if(loggedInResults != null) {
            //execute the command
        } else {
            //show an error through the model?
        }
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

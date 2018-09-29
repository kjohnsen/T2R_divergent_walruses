package model;

import clientserver.ServerPoller;
import clientserver.ServerProxy;
import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public class UIFacade implements iClient {

    @Override
    public void loginUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().loginUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            //execute command
        } else {
            //throw an error and have the presenter catch it and display it in a toast?
            if(loggedInResults == null) {
                //results never got server, make message
            } else {
                //user or server error, get message from results
            }
        }
    }

    @Override
    public void registerUser(String username, String password) {
        LoggedInResults loggedInResults = ServerProxy.getInstance().registerUser(username, password);
        if(loggedInResults != null && loggedInResults.getSuccess()) {
            //execute command
        } else {
            //throw an error and have the presenter catch it and display it in a toast?
            if(loggedInResults == null) {
                //results never got server, make message
            } else {
                //user or server error, get message from results
            }
        }
    }

    @Override
    public void joinGame(Player player, GameID gameID) {

    }

    @Override
    public void createGame(GameInfo gameInfo) {
        //GameInfo needs methods to get attributes
        //GameResults gameResults = ServerProxy.getInstance().createGame()

    }

    @Override
    public void startGame(GameInfo gameInfo) {

    }

    @Override
    public void claimColor(Player player, PlayerColor playerColor) {

    }
}

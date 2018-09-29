package model;

import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.Results;

public class CommandFacade implements iClient {

    @Override
    public void loginUser(String username, String password) {

    }

    @Override
    public void registerUser(String username, String password) {

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

package main.java.model;

import main.java.interfaces.iClient;
import results.Results;

public class UIFacade implements iClient {
    @Override
    public Results joinGame(Player player, GameID gameID) {
        return null;
    }

    @Override
    public Results createGame(GameInfo gameInfo) {
        return null;
    }

    @Override
    public Results startGame(GameID gameID) {
        return null;
    }

    @Override
    public Results loginUser(String authToken) {
        return null;
    }

    @Override
    public Results registerUser(String authToken) {
        return null;
    }

    @Override
    public Results claimColor(Player player, PlayerColor pc) {
        return null;
    }
}

package model;

import interfaces.iClient;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.Results;

public class CommandFacade implements iClient {
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

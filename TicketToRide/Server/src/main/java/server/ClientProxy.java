package server;

import interfaces.iClient;
import results.Results;
import results.GameResults;
import results.LoggedInResults;
import model.Player;
import model.PlayerColor;
import model.GameID;
import model.GameInfo;

public class ClientProxy implements iClient {

    public ClientProxy(){}

    public GameResults joinGame(Player player, GameID gameID) {
        return null;
    }

    public GameResults createGame(GameInfo gameInfo) {
        return null;
    }

    public GameResults startGame(GameID gameID) {
        return null;
    }

    public LoggedInResults loginUser(String authToken) {
        return null;
    }

    public LoggedInResults registerUser(String authToken) {
        return null;
    }

    public Results claimColor(Player player, PlayerColor color) {
        return null;
    }

}

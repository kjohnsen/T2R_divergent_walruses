package server;

import java.util.ArrayList;

import interfaces.IServer;
import results.LoggedInResults;
import results.GameResults;
import results.Results;
import data.Command;
import modelclasses.GameID;
import modelclasses.PlayerColor;

// this will implement iServer, once that is a thing
public class ServerFacade implements IServer {

    private String _requesterAuthToken;

    public ServerFacade(String requesterAuthToken) {
        _requesterAuthToken = requesterAuthToken;
    }

    public String get_requesterAuthToken() {
        return _requesterAuthToken;
    }

    public void set_requesterAuthToken(String _requesterAuthToken) {
        this._requesterAuthToken = _requesterAuthToken;
    }

    public LoggedInResults loginUser(String username, String password) {
        return null;
    }

    public LoggedInResults registerUser(String username, String password) {
        return null;
    }

    public GameResults createGame(String name, int numPlayers) {
        return null;
    }

    public GameResults joinGame(GameID gameID) {
        return null;
    }

    public GameResults startGame(GameID gameID) {
        return null;
    }

    public Results chooseColor(PlayerColor color) {
        return null;
    }

    public ArrayList<Command> getCommands(String clientID) {
        return null;
    }

}

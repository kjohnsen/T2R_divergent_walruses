package main.java.interfaces;

import main.java.model.GameID;
import main.java.model.GameInfo;
import main.java.model.Player;
import main.java.model.PlayerColor;
import results.Results;

public interface iClient {
    public Results joinGame(Player player, GameID gameID);

    public Results createGame(GameInfo gameInfo);

    public Results startGame(GameID gameID);

    public Results loginUser(String authToken);

    public Results registerUser(String authToken);

    public Results claimColor(Player player, PlayerColor pc);
}

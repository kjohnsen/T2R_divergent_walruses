package interfaces;


import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import results.Results;

public interface iClient {
    public Results joinGame(Player player, GameID gameID);

    public Results createGame(GameInfo gameInfo);

    public Results startGame(GameID gameID);

    public Results loginUser(String authToken);

    public Results registerUser(String authToken);

    public Results claimColor(Player player, PlayerColor pc);
}

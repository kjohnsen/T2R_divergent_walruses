package persistence;

import modelclasses.GameInfo;
import modelclasses.GameName;

public interface IGameInfoDAO {
    public Result createGameInfo(GameInfo gameInfo);
    public GameInfo readGameInfo(GameName gameName);
    public Result updateGameInfo(GameInfo gameInfo);
    public Result deleteGameInfo(GameInfo gameInfo);
}

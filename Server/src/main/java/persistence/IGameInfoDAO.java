package persistence;

import java.util.List;

import modelclasses.GameInfo;
import modelclasses.GameName;

public interface IGameInfoDAO {
    public Result createGameInfo(GameInfo gameInfo);
    public GameInfo readGameInfo(GameName gameName);
    List<GameInfo> readAllGameInfos();
    public Result updateGameInfo(GameInfo gameInfo);
    public Result deleteGameInfo(GameInfo gameInfo);
}

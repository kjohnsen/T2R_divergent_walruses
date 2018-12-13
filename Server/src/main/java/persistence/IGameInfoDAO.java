package persistence;

import java.util.ArrayList;

import modelclasses.GameInfo;
import modelclasses.GameName;

public interface IGameInfoDAO {
    Result createGameInfo(GameInfo gameInfo);
    GameInfo readGameInfo(GameName gameName);
    ArrayList<GameInfo> readAllGameInfos();
    Result updateGameInfo(GameInfo gameInfo);
    Result deleteGameInfo(GameInfo gameInfo);
}

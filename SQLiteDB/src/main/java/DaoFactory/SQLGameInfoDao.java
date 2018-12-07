package DaoFactory;

import persistence.IGameInfoDAO;
import persistence.Result;

import modelclasses.GameInfo;
import modelclasses.GameName;

public class SQLGameInfoDao implements IGameInfoDAO {

    @Override
    public Result createGameInfo(GameInfo gameInfo) {
        return null;
    }

    @Override
    public GameInfo readGameInfo(GameName gameName) {
        return null;
    }

    @Override
    public Result updateGameInfo(GameInfo gameInfo) {
        return null;
    }

    @Override
    public Result deleteGameInfo(GameInfo gameInfo) {
        return null;
    }

}

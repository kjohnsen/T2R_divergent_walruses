package DaoFactory;

import persistence.IGameInfoDAO;
import persistence.Result;

import modelclasses.GameInfo;
import modelclasses.GameName;

public class SQLGameInfoDao implements IGameInfoDAO {

    @Override
    public Result create(GameInfo gameInfo) {
        return null;
    }

    @Override
    public GameInfo read(GameName gameName) {
        return null;
    }

    @Override
    public Result update(GameInfo gameInfo) {
        return null;
    }

    @Override
    public Result delete(GameInfo gameInfo) {
        return null;
    }

}

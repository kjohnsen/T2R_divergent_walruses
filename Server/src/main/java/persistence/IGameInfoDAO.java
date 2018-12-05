package persistence;

import modelclasses.GameInfo;
import modelclasses.GameName;

public interface IGameInfoDAO {
    public Result create(GameInfo gameInfo);
    public GameInfo read(GameName gameName);
    public Result update(GameInfo gameInfo);
    public Result delete(GameInfo gameInfo);
}

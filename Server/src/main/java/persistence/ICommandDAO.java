package persistence;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameName;

public interface ICommandDAO {
    public Result create(GameName gameName, Command command);
    public ArrayList<Command> read(GameName gameName);
    public Result delete(ArrayList<Command> commands);
}

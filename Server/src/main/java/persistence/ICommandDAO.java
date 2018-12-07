package persistence;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameName;

public interface ICommandDAO {
    public Result createCommand(Command command, GameName gameName);
    public Command readCommand(String commandID);
    public Result deleteCommand(GameName gameName);
}
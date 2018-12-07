package persistence;

import java.util.ArrayList;
import java.util.List;

import data.Command;
import modelclasses.GameName;

public interface ICommandDAO {
    public Result createCommand(Command command, GameName gameName);
    public Command readCommand(String commandID, GameName gameName);
    List<Command> readAllCommands();
    public Result deleteCommand(GameName gameName);
}
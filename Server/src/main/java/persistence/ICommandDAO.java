package persistence;

import java.util.ArrayList;

import data.Command;
import modelclasses.GameName;

public interface ICommandDAO {
    Result createCommand(Command command, GameName gameName);
    ArrayList<Command> readCommands(GameName gameName);
    ArrayList<Command> readAllCommands();
    Result deleteCommand(GameName gameName);
}
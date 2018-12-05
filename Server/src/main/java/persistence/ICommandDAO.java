package persistence;

import java.util.ArrayList;

import data.Command;

public interface ICommandDAO {
    public Result create(Command command);
    public Command read(String commandID);
    public Result delete(ArrayList<Command> commands);
}

package server;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import data.Command;

public class CommandManager {

    private static CommandManager instance = null;

    // These strings are usernames
    private Map<String, ArrayList<Command>> clientCommands = new HashMap<>();

    private CommandManager(){}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public ArrayList<Command> getCommands(String username) {
        ArrayList<Command> returnCommands = new ArrayList<>();
        ArrayList<Command> commandsToAdd = clientCommands.get(username);
        if (commandsToAdd != null) {
            returnCommands.addAll(commandsToAdd);
            //clear commands
            clientCommands.get(username).removeAll(returnCommands);
            return returnCommands;
        } else {
            return null;
        }

    }

    // gets the array list of commands with the given client username and adds it.
    public void addCommand(String username, Command command) {
        ArrayList<Command> commands = clientCommands.get(username);
        if (commands == null) {
            commands = new ArrayList<>();
        }
        commands.add(command);
    }

    public void addClient(String username) {
        ArrayList<Command> commands = new ArrayList<>();
        clientCommands.put(username, commands);
    }

}

package data;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class CommandManager {

    private static CommandManager instance = null;

    // These strings are authTokens
    private Map<String, ArrayList<Command>> clientCommands = new HashMap<>();

    private CommandManager(){}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public ArrayList<Command> getCommands(String authToken) {
        ArrayList<Command> returnCommands = new ArrayList<>();
        ArrayList<Command> commandsToAdd = clientCommands.get(authToken);
        if (commandsToAdd != null) {
            returnCommands.addAll(commandsToAdd);
            //clear commands
            clientCommands.get(authToken).removeAll(returnCommands);
            return returnCommands;
        } else {
            return null;
        }

    }

    //gets the array list of commands with the given client ID and adds it.
    public void addCommand(String authToken, Command command) {
        ArrayList<Command> commands = clientCommands.get(authToken);
        if (commands == null) {
            commands = new ArrayList<>();
        }
        commands.add(command);
    }

    public void addClient(String authToken) {
        ArrayList<Command> commands = new ArrayList<>();
        clientCommands.put(authToken, commands);
    }

}

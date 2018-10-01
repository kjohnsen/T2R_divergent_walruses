package data;

import java.util.Map;
import java.util.ArrayList;

public class CommandManager {

    private static CommandManager instance = null;

    // These strings are authTokens
    private Map<String, ArrayList<Command>> clientCommands;

    private CommandManager(){}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public ArrayList<Command> getCommands(String clientID) {
        return clientCommands.get(clientID);
    }

    public void addCommand(String clientID, Command command) {
        ArrayList<Command> commands = clientCommands.get(clientID);
        commands.add(command);
    }

    public void addClient(String clientID) {
        ArrayList<Command> commands = new ArrayList<>();
        clientCommands.put(clientID, commands);
    }

}

package data;

import java.util.Map;
import java.util.ArrayList;

public class CommandManager {

    private Map<String, ArrayList<Command>> _clientCommands;
    private ArrayList<String> _clientIDs;

    public CommandManager(Map<String, ArrayList<Command>> clientCommands, ArrayList<String> clientIDs) {
        _clientCommands = clientCommands;
        _clientIDs = clientIDs;
    }

    public ArrayList<Command> getCommands(String clientID) {
        return null;
    }

    public void addCommand(String clientID, Command command) {

    }

}

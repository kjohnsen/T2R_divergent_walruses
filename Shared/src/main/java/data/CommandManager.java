package data;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class CommandManager {

    private static CommandManager instance = null;

    private static HashMap<String, ArrayList<Command>> _clientCommands;
    private static ArrayList<String> _clientIDs;


    public static CommandManager getInstance(){
        if(instance == null) {
            instance = new CommandManager();
            _clientCommands = new HashMap();
            _clientIDs = new ArrayList();

        }
        return instance;
    }

    private CommandManager(){}

    public static HashMap<String, ArrayList<Command>> get_clientCommands() {
        return _clientCommands;
    }

    public static ArrayList<String> get_clientIDs() {
        return _clientIDs;
    }

    public ArrayList<Command> getCommands(String clientID) {
        return null;
    }

    //gets the array list of commands with the given client ID and adds it.
    public void addCommand(String clientID, Command command) {
        get_clientCommands().get(clientID).add(command);
    }

}

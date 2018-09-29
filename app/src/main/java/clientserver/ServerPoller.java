package clientserver;

import java.util.ArrayList;

import data.Command;

public class ServerPoller {
    private static final ServerPoller ourInstance = new ServerPoller();

    private ServerPoller() {}

    public static ServerPoller getInstance() {
        return ourInstance;
    }

    public ArrayList<Command> getCommands() {
        return null;
    }
}
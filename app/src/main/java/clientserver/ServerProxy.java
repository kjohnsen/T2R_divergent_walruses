package clientserver;

import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import modelclasses.GameName;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public class ServerProxy implements IServer {
    private static final ServerProxy ourInstance = new ServerProxy();

    private ServerProxy() {}

    public static ServerProxy getInstance() {
        return ourInstance;
    }

    private String authToken;

    @Override
    public LoggedInResults loginUser(String username, String password) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command(IServer.class.getName(), methodName, parameterTypes, parameters);
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public LoggedInResults registerUser(String username, String password) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command(IServer.class.getName(), methodName, parameterTypes, parameters);
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults createGame(String name, int numPlayers, String authToken) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"String", "int"};
        Object[] parameters = {name, numPlayers};
        Command command = new Command(IServer.class.getName(), methodName, parameterTypes, parameters);
        return (GameResults) ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults joinGame(GameName gameName, String authToken) {
        return null;
    }

    @Override
    public GameResults startGame(GameName gameName, String authToken) {
        return null;
    }

    @Override
    public Results chooseColor(PlayerColor color, String authToken) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"PlayerColor"};
        Object[] parameters = {color};
        Command command = new Command(IServer.class.getName(), methodName, parameterTypes, parameters);
        return ClientCommunicator.getInstance().send(command);
    }

    @Override
    public ArrayList<Command> getCommands(String clientID, String authToken) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"String"};
        Object[] parameters = {clientID};
        Command command = new Command(IServer.class.getName(), methodName, parameterTypes, parameters);
        Results results = ClientCommunicator.getInstance().send(command);
        //Results needs to have the list of commands in it
        return null;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
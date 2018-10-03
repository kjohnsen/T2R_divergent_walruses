package clientserver;

import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import model.ClientModel;
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


    @Override
    public LoggedInResults loginUser(String username, String password) {
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command("ServerFacade", "loginUser", parameterTypes, parameters);
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public LoggedInResults registerUser(String username, String password) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command("ServerFacade", "registerUser", parameterTypes, parameters);
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults createGame(String name, int numPlayers, String authToken) {
        String[] parameterTypes = {"String", "int", "String"};
        Object[] parameters = {name, numPlayers, authToken};
        Command command = new Command("ServerFacade", "createGame", parameterTypes, parameters);
        return (GameResults) ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults joinGame(GameName gameName, String authToken) {
        String[] parameterTypes = {"GameName", "String"};
        Object[] parameters = {gameName, authToken};
        Command command = new Command("ServerFacade", "joinGame", parameterTypes, parameters);
        return (GameResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults startGame(GameName gameName, String authToken) {
        String[] parameterTypes = {"GameName", "String"};
        Object[] parameters = {gameName, authToken};
        Command command = new Command("ServerFacade", "startGame", parameterTypes, parameters);
        return (GameResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public Results chooseColor(PlayerColor color, String authToken) {
        String[] parameterTypes = {"PlayerColor", "String"};
        Object[] parameters = {color, authToken};
        Command command = new Command("ServerFacade", "chooseColor", parameterTypes, parameters);
        return ClientCommunicator.getInstance().send(command);
    }

    @Override
    public ArrayList<Command> getCommands(String clientID, String authToken) {
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {clientID, authToken};
        Command command = new Command("ServerFacade", "getCommands", parameterTypes, parameters);
        Results results = ClientCommunicator.getInstance().send(command);
        return results.getClientCommands();
    }
}
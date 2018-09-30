package clientserver;

import java.util.ArrayList;

import data.Command;
import interfaces.IServer;
import modelclasses.GameID;
import modelclasses.PlayerColor;
import results.GameResults;
import results.LoggedInResults;
import results.Results;

public class ServerProxy implements IServer {
    private static final ServerProxy ourInstance = new ServerProxy();
    private ClientCommunicator clientCommunicator = ClientCommunicator.getInstance();

    private ServerProxy() {}

    public static ServerProxy getInstance() {
        return ourInstance;
    }

    public void setClientCommunicator(ClientCommunicator clientCommunicator) {
        this.clientCommunicator = clientCommunicator;
    }

    @Override
    public LoggedInResults loginUser(String username, String password) {
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command("ServerFacade", "loginUser", parameterTypes, parameters);
        return (LoggedInResults)clientCommunicator.send(command);
    }

    @Override
    public LoggedInResults registerUser(String username, String password) {
        String[] parameterTypes = {"String", "String"};
        Object[] parameters = {username, password};
        Command command = new Command("ServerFacade", "registerUser", parameterTypes, parameters);
        return (LoggedInResults)clientCommunicator.send(command);
    }

    @Override
    public GameResults createGame(String name, int numPlayers) {
        String[] parameterTypes = {"String", "int"};
        Object[] parameters = {name, numPlayers};
        Command command = new Command("ServerFacade", "createGame", parameterTypes, parameters);
        return (GameResults) clientCommunicator.send(command);
    }

    @Override
    public GameResults joinGame(GameID gameID) {
        return null;
    }

    @Override
    public GameResults startGame(GameID gameID) {
        return null;
    }

    @Override
    public Results chooseColor(PlayerColor color) {
        String[] parameterTypes = {"PlayerColor"};
        Object[] parameters = {color};
        Command command = new Command("ServerFacade", "chooseColor", parameterTypes, parameters);
        return clientCommunicator.send(command);
    }

    @Override
    public ArrayList<Command> getCommands(String clientID) {
        String[] parameterTypes = {"String"};
        Object[] parameters = {clientID};
        Command command = new Command("ServerFacade", "getCommands", parameterTypes, parameters);
        Results results = clientCommunicator.send(command);
        return results.getClientCommands();
    }
}
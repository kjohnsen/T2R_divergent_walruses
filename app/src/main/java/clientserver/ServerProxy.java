package clientserver;

import java.util.ArrayList;
import java.util.Arrays;

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
        //Send the command...
        Command command = new Command("ServerFacade", "loginUser", Arrays.asList(new Object[] {username, password}));
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public LoggedInResults registerUser(String username, String password) {
        //Send the command...
        Command command = new Command("ServerFacade", "registerUser", Arrays.asList(new Object[] {username, password}));
        return (LoggedInResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults createGame(String name, int numPlayers, String authToken) {
        //Send the command...
        Command command = new Command("ServerFacade", "createGame", Arrays.asList(new Object[] {numPlayers, authToken}));
        return (GameResults) ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults joinGame(GameName gameName, String authToken) {
        //Send the command...
        Command command = new Command("ServerFacade", "joinGame", Arrays.asList(new Object[] {gameName, authToken}));
        return (GameResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public GameResults startGame(GameName gameName, String authToken) {
        //Send the command...
        Command command = new Command("ServerFacade", "startGame", Arrays.asList(new Object[] {gameName, authToken}));
        return (GameResults)ClientCommunicator.getInstance().send(command);
    }

    @Override
    public Results chooseColor(PlayerColor color, GameName gameName, String authToken) {
        //Send the command...
        Command command = new Command("ServerFacade", "chooseColor", Arrays.asList(new Object[] {color, gameName, authToken}));
        return ClientCommunicator.getInstance().send(command);
    }

    @Override
    public Results getCommands(String authToken) {
        //Send the command...
        Command command = new Command("ServerFacade", "getCommands", Arrays.asList(new Object[] {authToken}));
        Results results = ClientCommunicator.getInstance().send(command);
        return results;
    }

    @Override
    public void setHostIP(String hostIP) {
        ClientCommunicator.getInstance().setServerHost(hostIP);
    }

    @Override
    public void setHostPort(String hostPort) {
        ClientCommunicator.getInstance().setServerPort(hostPort);
    }
}
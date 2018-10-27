package model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.Command;
import interfaces.IServer;
import modelclasses.ChatMessage;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import results.Results;

public class MockServerProxy implements IServer {

    @Override
    public Results selectDestinationCards(ArrayList<DestinationCard> tickets, GameName gameName, String authToken) {
        //implement this if you want to
        return null;
    }
    @Override
    public Results loginUser(String username, String password) {
        if(username.equals("success")) {
            Results loggedInResults = new Results();
            loggedInResults.setSuccess(true);
            loggedInResults.setClientCommands(new ArrayList<Command>());
            return loggedInResults;
        } else if(username.equals("fail")) {
            Results loggedInResults = new Results();
            loggedInResults.setSuccess(false);
            loggedInResults.setErrorMessage("Password does not match");
            return loggedInResults;
        }

        return null;
    }

    @Override
    public Results registerUser(String username, String password) {
        if(username.equals("success")) {
            Results loggedInResults = new Results();
            loggedInResults.setSuccess(true);
            loggedInResults.setClientCommands(new ArrayList<Command>());
            return loggedInResults;
        } else if(username.equals("fail")) {
            Results loggedInResults = new Results();
            loggedInResults.setSuccess(false);
            loggedInResults.setErrorMessage("User already exists");
            return loggedInResults;
        }

        return null;
    }

    @Override
    public Results createGame(String name, Integer numPlayers, String authToken) {
        if(name.equals("success")) {
            GameName gameName = new GameName("success");
            Results gameResults = new Results();
            gameResults.setSuccess(true);

            GameInfo gameInfo = new GameInfo(gameName, new ArrayList<Player>(), numPlayers);
            ArrayList<Command> commands = new ArrayList<>();
            commands.add(new Command("model.CommandFacade", "createGame",
                            Arrays.asList(new Object[] {gameInfo})));
            gameResults.setClientCommands(commands);
            return gameResults;
        } else if(name.equals("fail")) {
            Results gameResults = new Results();
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game already exists");
            return gameResults;
        }

        return null;
    }

    @Override
    public Results joinGame(GameName gameName, String authToken) {
        if(gameName.getName().equals("success")) {
            Results gameResults = new Results();
            gameResults.setSuccess(true);

            Player player = new Player("billy");

            ArrayList<Command> commands = new ArrayList<>();
            commands.add(new Command("model.CommandFacade", "joinGame",
                    Arrays.asList(new Object[] {player, gameName})));
            gameResults.setClientCommands(commands);
            return gameResults;
        } else if(gameName.getName().equals("fail")) {
            Results gameResults = new Results();
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game is full");
            return gameResults;
        }

        return null;
    }

    @Override
    public Results startGame(GameName gameName, String authToken) {
        if(gameName.getName().equals("success")) {
            Results gameResults = new Results();
            gameResults.setSuccess(true);
            gameResults.setClientCommands(new ArrayList<Command>());
            return gameResults;
        } else if(gameName.getName().equals("fail")) {
            Results gameResults = new Results();
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Game cannot start");
            return gameResults;
        }

        return null;
    }

    @Override
    public Results chooseColor(PlayerColor color, GameName gameName, String authToken) {
        if(authToken.equals("success")) {
            Results gameResults = new Results();
            gameResults.setSuccess(true);
            return gameResults;
        } else if(authToken.equals("fail")) {
            Results gameResults = new Results();
            gameResults.setSuccess(false);
            gameResults.setErrorMessage("Color already taken");
            return gameResults;
        }

        return null;
    }

    @Override
    public Results selectTrainCard(int index, GameName name, String authtoken) { return null; }

    @Override
    public Results drawTrainCard(GameName name, String authtoken) { return null; }

    @Override
    public Results drawDestinationCards(GameName name, String authtoken) { return null; }

    @Override
    public Results getCommands(String authToken) {
        return null;
    }

    @Override
    public void setHostIP(String hostIP) { }

    @Override
    public void setHostPort(String hostPort) { }

    @Override
    public Results sendChatMessage(ChatMessage message, GameName gameName) {
        return null;
    }
}

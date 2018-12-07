package server;


import java.util.ArrayList;

import data.Command;
import model.ServerModel;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.User;
import persistence.ICommandDAO;
import persistence.IGameInfoDAO;
import persistence.IUserDAO;
import persistence.Result;

public class DAOProxy implements ICommandDAO, IGameInfoDAO, IUserDAO {

    @Override
    public Result createCommand(Command command, GameName gameName) {

        //if the delta hits the max, then update the gameInfo
        if(ServerModel.getInstance().getDelta(gameName) >= ServerModel.getInstance().getDeltaMax()){

            //update game info and delete commands
            updateGameInfo(ServerModel.getInstance().getGameInfo(gameName));
            deleteCommand(gameName);

            //set delta to 0. hopefully the map can determine that the gamenames are the same.
            ServerModel.getInstance().initializeDelta(gameName);
        }

        //increment the delta
        ServerModel.getInstance().deltaIncrementer(gameName);
        return ServerModel.getInstance().getiPersistencePluginFactory().getCommandDAO().createCommand(command, gameName);
    }

    @Override
    public Command readCommand(String commandID, GameName gameName) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getCommandDAO().readCommand(commandID, gameName);
    }

    @Override
    public Result deleteCommand(GameName gameName) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getCommandDAO().deleteCommand(gameName);
    }

    @Override
    public Result createGameInfo(GameInfo gameInfo) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getGameInfoDAO().createGameInfo(gameInfo);
    }

    @Override
    public GameInfo readGameInfo(GameName gameName) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getGameInfoDAO().readGameInfo(gameName);
    }

    @Override
    public Result updateGameInfo(GameInfo gameInfo) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getGameInfoDAO().updateGameInfo(gameInfo);
    }

    @Override
    public Result deleteGameInfo(GameInfo gameInfo) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getGameInfoDAO().deleteGameInfo(gameInfo);
    }

    @Override
    public Result createUser(User user) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getUserDAO().createUser(user);
    }

    @Override
    public User readUser(String username) {
        return ServerModel.getInstance().getiPersistencePluginFactory().getUserDAO().readUser(username);
    }
}

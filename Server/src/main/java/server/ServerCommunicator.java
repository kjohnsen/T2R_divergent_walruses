package server;
import java.io.*;
import java.net.*;
import java.util.List;

import com.sun.net.httpserver.HttpServer;

import data.Command;
import handler.ExcmdHandler;
import model.ServerModel;
import modelclasses.GameInfo;
import modelclasses.User;
import persistence.ICommandDAO;
import persistence.IGameInfoDAO;
import persistence.IPersistencePluginFactory;
import persistence.IUserDAO;
import persistence.PluginManager;

/**
 * Created by Parker on 2/10/18.
 */


public class ServerCommunicator {

    // The maximum number of waiting incoming connections to queue.
    // While this value is necessary, for our purposes it is unimportant.
    // Take CS 460 for a deeper understanding of what it means.
    private static final int MAX_WAITING_CONNECTIONS = 12;

    // Java provides an HttpServer class that can be used to embed
    // an HTTP server in any Java program.
    // Using the HttpServer class, you can easily make a Java
    // program that can receive incoming HTTP requests, and respond
    // with appropriate HTTP responses.
    // HttpServer is the class that actually implements the HTTP network
    // protocol (be glad you don't have to).
    // The "server" field contains the HttpServer instance for this program,
    // which is initialized in the "run" method below.
    private HttpServer server;


    private void run(String portNumber)
    {
        System.out.print("Initializing HTTP Server\n");

        try
        {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        System.out.println("Creating contexts");
        server.createContext("/excmd", new ExcmdHandler());

        // Log message indicating that the HttpServer is about the start accepting
        // incoming client connections.
        System.out.println("Starting server");

        server.start();

        // Log message indicating that the server has successfully started.
        System.out.println(String.format("Server started on port %s", portNumber));
    }


    private static void reloadPersistentData(IPersistencePluginFactory persistencePluginFactory) {
        persistencePluginFactory.startTransaction();

        // load all users into model
        IUserDAO userDAO = persistencePluginFactory.getUserDAO();
        for (User user : userDAO.readAllUsers()) {
            ServerModel.getInstance().getUsers().put(user.getUsername(), user);
        }
        // load all games into model
        IGameInfoDAO gameInfoDAO = persistencePluginFactory.getGameInfoDAO();
        for (GameInfo game : gameInfoDAO.readAllGameInfos()) {
            ServerModel.getInstance().getGames().put(game.getGameName(), game);
        }
        // run all commands on games
        ICommandDAO commandDAO = persistencePluginFactory.getCommandDAO();
        for (Command command : commandDAO.readAllCommands()) {
            command.execute();
        }

        //after executing commands... delete them from the database and reinitialize delta
        for (GameInfo game : gameInfoDAO.readAllGameInfos()) {
            ServerModel.getInstance().getDaoProxy().deleteCommand(game.getGameName());
            ServerModel.getInstance().initializeDelta(game.getGameName());
        }


        persistencePluginFactory.endTransaction();
    }


    // "main" method for the server program
    // "args" should contain one command-line argument, which is the port number
    // on which the server should accept incoming client connections.
    public static void main(String[] args) {
        String portNumber = args[0];
        String persistenceType = args[1];
        int commandsBetweenCheckpoints = Integer.parseInt(args[2]);

        ServerModel.getInstance().setDeltaMax(commandsBetweenCheckpoints);

        PluginManager pluginManager = ServerModel.getInstance().getPluginManager();
        pluginManager.setiPersistencePluginFactory(persistenceType);
        IPersistencePluginFactory pluginFactory = pluginManager.getiPersistencePluginFactory();

        reloadPersistentData(pluginFactory);

        new ServerCommunicator().run(portNumber);
    }
}

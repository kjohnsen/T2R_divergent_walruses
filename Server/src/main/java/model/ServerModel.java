package model;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import modelclasses.ChatMessage;

import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.User;
import persistence.IPersistencePluginFactory;
import persistence.PluginManager;
import server.DAOProxy;

/**
 * Represents the model on the server side.
 * All game info, chat info, and user info exist here
 * and methods for updating this information
 */
public class ServerModel {

    /**
     * Singleton so that there exists one copy of the model
     */
    private static ServerModel instance = null;

    /**
     * Manages data persistence plugins.
     */
    PluginManager pluginManager = new PluginManager();

    /**
     * connection to the DAO stuff
     */
    private DAOProxy daoProxy = new DAOProxy();

    /**
     * delta counter counts until it hits deltaMax.
     * When it hits deltaMax, it deletes the commands
     * and updates the GameInfo blob in the database.
     */
    private Map<GameName, Integer> gameName_delta = new HashMap<>();

    /**
     * deltaMax comes from the commandline when initializing the server.
     * It is the number of commands that are added to the data base
     * before the list of commands is deleted and the gameinfo blob
     * is updated.
     */
    private Integer deltaMax = 0;

    /**
     * This is points to the iPersistencePluginFactory on the PluginManager class
     */
    IPersistencePluginFactory iPersistencePluginFactory = null;

    /**
     * Map of authTokens to usernames (owners of the auth tokens)
     */
    private Map<String, String> authTokens = new HashMap<>(); // maps authTokens to usernames

    /**
     * maps usernames to actual Model User objects
     */
    private Map<String, User> users = new HashMap<>(); // maps usernames to users

    /**
     * Maps Gamename object to the GameInfo (contains all information about a specific game)
     */
    private Map<GameName, GameInfo> games = new HashMap<>();

    /**
     * Maps the GameName object to a list of all the Chatmessage Objects in that game.
     */
    private Map<GameName,List<ChatMessage>> chatMessages = new HashMap<>();

    private int lastPlayerIndex = -1;

    private boolean bootingUp = false;

    /**
     * private empty constructor.
     * @pre None
     * @post new authTokens.size() = 0
     * @post new users.size() = 0
     * @post new games.size() = 0
     * @post new chatMessages.size() = 0
     * @post new ServerModel() != null
     */
    private ServerModel() { }

    /**
     * @return single instance of the servermodel
     * @pre None
     * @post ServerModel instance != null
     */
    public static ServerModel getInstance(){
        if(instance == null) {
            instance = new ServerModel();
        }
        return instance;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public IPersistencePluginFactory getiPersistencePluginFactory() {
        return iPersistencePluginFactory;
    }

    public void setiPersistencePluginFactory(IPersistencePluginFactory iPersistencePluginFactory) {
        this.iPersistencePluginFactory = iPersistencePluginFactory;
    }

    public int getLastPlayerIndex() {
        return lastPlayerIndex;
    }

    public void setLastPlayerIndex(int lastPlayerIndex) {
        this.lastPlayerIndex = lastPlayerIndex;
    }


    /**
     * @pre None
     * @post Return value contains all chatmessages in all games
     * @return returns entire map of all chatmessages in every game
     */
    public Map<GameName, List<ChatMessage>> getChatMessages() {
        return chatMessages;
    }

    /**
     * Checks if the user exists in the server model
     * @pre username not null
     * @post Return boolean contains true if user exists
     * @param username String of specified user
     * @return Boolean true if user exists
     */
    public Boolean checkUserExists(String username) {
        return getUsers().containsKey(username);
    }

    /**
     * Checks if the password for the specified username is correct
     * @param username string username
     * @param password string password
     *
     * @pre username is not null
     * @pre password is not null
     * @post Return boolean contains true if password is correct
     *
     * @return returns true if password is correct
     */
    public Boolean checkPassword(String username, String password) {
        return getUsers().get(username).getPassword().equals(password);
    }

    /**
     * Gets the gameinfo for a specified gamename object
     *
     * @pre gamename is not null
     * @post return gameInfo contains specified gamename
     *
     * @param gameName GameName object
     * @return GameInfo object returned
     */
    public GameInfo getGameInfo(GameName gameName) {
        return games.get(gameName);
    }

    /**
     * Gets the user of the specified username
     *
     * @pre string username is not null
     * @post return user contains username provided
     *
     * @param username String username
     * @return User if the username is in the map
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Gets a list of all the gameinfo objects
     *
     * @pre None
     * @post return list contains all gameinfo objects in server model
     *
     * @return ArrayList of Gameinfo objects
     */
    public ArrayList<GameInfo> getGameList() {
        Object[] gameList = games.values().toArray();
        ArrayList<GameInfo> gameListToReturn = new ArrayList<>();
        for (Object gameObj : gameList) {
            GameInfo game = (GameInfo) gameObj;
            gameListToReturn.add(game);
        }
        return gameListToReturn;
    }

    // ********** getters and setters ***********


    public DAOProxy getDaoProxy() {
        return daoProxy;
    }

    public Map<GameName, Integer> getGameName_delta() {
        return gameName_delta;
    }

    /**
     * increments the delta of the corresponding gamename
     * @param gameName
     */
    public void deltaIncrementer(GameName gameName) {
        gameName_delta.put(gameName, gameName_delta.get(gameName) + 1);
    }

    /**
     * initialize the delta for the corresponding game
     * @param gameName
     */
    public void initializeDelta(GameName gameName) {
        gameName_delta.put(gameName, 0);
    }

    /**
     * gets the delta of the corresponding gamename.
     * @param gameName
     * @return
     */
    public Integer getDelta(GameName gameName) {
        return gameName_delta.get(gameName);
    }

    public Integer getDeltaMax() {
        return deltaMax;
    }

    public void setDeltaMax(Integer deltaMax) {
        this.deltaMax = deltaMax;
    }

    /**
     * Getters and Setters self explanatory
     * @pre None
     * @post return map maps authtokens to users
     * @return
     */
    public Map<String, String> getAuthTokens() {
        return authTokens;
    }

    /**
     * @pre input parameter authtokens is not null
     * @post authtokens is not null
     * @param authTokens
     */
    public void setAuthTokens(Map<String, String> authTokens) {
        this.authTokens = authTokens;
    }

    /**
     * @pre None
     * @post return map maps authtokens to users
     * @return
     */
    public Map<String, User> getUsers() {
        return users;
    }

    /**
     * @pre input parameter map is not null
     * @post users is not null
     * @param users
     */
    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    /**
     * @pre None
     * @post return map maps authtokens to users
     * @return
     */
    public Map<GameName, GameInfo> getGames() {
        return games;
    }

    /**
     * @pre input parameter games is not null
     * @post return map maps authtokens to users
     * @param games
     */
    public void setGames(Map<GameName, GameInfo> games) {
        this.games = games;
    }

    public boolean userIsAlreadyInGame(String username) {
        boolean inGame = false;
        for(GameInfo gameInfo: games.values()) {
            if(gameInfo.getPlayer(username) != null) {
                inGame = true;
                break;
            }
        }

        return inGame;
    }

    public GameName getGameNameFromGameInfo(GameInfo gameInfo){
        for(GameName gameName : getGames().keySet()){
            if(getGames().get(gameName).equals(gameInfo))
                return gameName;
        }
        return null;
    }

    public boolean isBootingUp() {
        return bootingUp;
    }

    public void setBootingUp(boolean bootingUp) {
        this.bootingUp = bootingUp;
    }

    //********************************************
}

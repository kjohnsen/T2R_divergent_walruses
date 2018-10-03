package model;

import java.util.Map;
import modelclasses.GameInfo;
import modelclasses.GameName;
import modelclasses.User;

public class ServerModel {

    private static ServerModel instance = null;

    private Map<String, String> authTokens; // maps authTokens to usernames
    private Map<String, User> users;
    private Map<GameName, GameInfo> games;

    private ServerModel() { }

    public static ServerModel getInstance(){
        if(instance == null) {
            instance = new ServerModel();
        }
        return instance;
    }

    public Boolean checkUserExists(String username) {
        return getUsers().containsKey(username);
    }

    public Boolean checkPassword(String username, String password) {
        return getUsers().get(username).equals(password);
    }

    public GameInfo getGameInfo(GameName gameName) {
        return games.get(gameName);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    // ********** getters and setters ***********
    public Map<String, String> getAuthTokens() {
        return authTokens;
    }

    public void setAuthTokens(Map<String, String> authTokens) {
        this.authTokens = authTokens;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<GameName, GameInfo> getGames() {
        return games;
    }

    public void setGames(Map<GameName, GameInfo> games) {
        this.games = games;
    }
    //********************************************
}

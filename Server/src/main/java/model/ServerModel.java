package model;

import java.util.Map;
import modelclasses.GameInfo;
import modelclasses.GameName;


public class ServerModel {

    private static ServerModel instance = null;

    private Map<String, String> authTokens;
    // maps usernames to passwords
    private Map<String, String> users;
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

    // ********** getters and setters ***********
    public Map<String, String> getAuthTokens() {
        return authTokens;
    }

    public void setAuthTokens(Map<String, String> authTokens) {
        this.authTokens = authTokens;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
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

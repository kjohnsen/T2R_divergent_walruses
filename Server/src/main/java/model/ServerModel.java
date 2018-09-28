package model;

import java.util.Map;
import modelclasses.GameInfo;
import modelclasses.GameID;


public class ServerModel {

    private static ServerModel instance = null;

    private Map<String, String> _authTokens;
    //does this map users to passwords?
    private Map<String, String> _users;
    private Map<GameID, GameInfo> _games;

    public ServerModel() { }

    public static ServerModel getInstance(){
        if(instance == null) {
            instance = new ServerModel();
        }
        return instance;
    }

    public Boolean checkUserExists(String username) {
        return get_users().containsKey(username);
    }

    public Boolean checkPassword(String username, String password) {
        return get_users().get(username).equals(password);
    }

    // ********** getters and setters ***********
    public Map<String, String> get_authTokens() {
        return _authTokens;
    }

    public void set_authTokens(Map<String, String> _authTokens) {
        this._authTokens = _authTokens;
    }

    public Map<String, String> get_users() {
        return _users;
    }

    public void set_users(Map<String, String> _users) {
        this._users = _users;
    }

    public Map<GameID, GameInfo> get_games() {
        return _games;
    }

    public void set_games(Map<GameID, GameInfo> _games) {
        this._games = _games;
    }
    //********************************************
}

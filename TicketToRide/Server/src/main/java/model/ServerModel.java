package model;

import java.util.Map;

public class ServerModel {

    private Map<String, String> _authTokens;
    private Map<String, String> _users;
    private Map<GameID, GameInfo> _games;

    public ServerModel(Map<String, String> authTokens, Map<String, String> users, Map<GameID, GameInfo> games) {
        _authTokens = authTokens;
        _users = users;
        _games = games;
    }

}

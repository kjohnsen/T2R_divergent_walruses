package results;

import java.util.ArrayList;
import modelclasses.GameInfo;

public class LoggedInResults extends Results {

    private String authToken;
    private ArrayList<GameInfo> games = new ArrayList<>();

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ArrayList<GameInfo> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameInfo> games) {
        this.games = games;
    }
}
package modelclasses;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<GameID> gameIDlist;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

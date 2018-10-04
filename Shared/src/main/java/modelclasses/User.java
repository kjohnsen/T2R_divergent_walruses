package modelclasses;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<GameName> gameNameList;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void addGame(GameName gameName) {
        gameNameList.add(gameName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username.equals(user.username) && this.password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

package modelclasses;

import java.util.ArrayList;

public class User {
    private String username;
    private ArrayList<GameName> gameNameList;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}

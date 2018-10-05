package modelclasses;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<GameName> gameNameList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<GameName> getGameNameList() {
        return gameNameList;
    }

    public void setGameNameList(ArrayList<GameName> gameNameList) {
        this.gameNameList = gameNameList;
    }

    public void addGame(GameName gameName) {
        gameNameList.add(gameName);
    }

    public boolean gameInList(GameName gameName) {
        for (GameName game : gameNameList) {
            if (gameName.equals(game)) {
                return true;
            }
        }
        return false;
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

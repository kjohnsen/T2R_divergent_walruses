package persistence;

import java.util.List;

import modelclasses.User;

public interface IUserDAO {
    public Result createUser(User user);
    List<User> readAllUsers();
    public User readUser(String username);
}
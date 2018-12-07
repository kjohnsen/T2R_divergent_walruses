package persistence;

import modelclasses.User;

public interface IUserDAO {
    public Result createUser(User user);
    public User readUser(String username);
}
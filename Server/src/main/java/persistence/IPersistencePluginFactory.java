package persistence;

public interface IPersistencePluginFactory {

    //No IHost needed
    public void openConnection();
    public void closeConnection();
    public void clearDB();
    public void startTransaction();
    public void endTransaction();
    public IUserDAO getUserDAO();
    public ICommandDAO getCommandDAO();
    public IGameInfoDAO getGameInfoDAO();

}

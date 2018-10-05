package presenter;

public interface ILoginPresenter {
    void login();
    void register();
    void loginUsernameChanged(String username);
    void loginPasswordChanged(String password);
    void registerUsernameChanged(String username);
    void registerPasswordChanged(String password);
    void registerConfirmChanged(String confirm);
    void hostIPChanged(String hostIP);
    void hostPortChanged(String hostPort);
}

package activity;

public interface ILoginActivity {
    void setRegisterEnabled(boolean enabled);
    void setLoginEnabled(boolean enabled);
    void displayErrorMessage(String message);
    void goToGameList();
}

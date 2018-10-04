package presenter;

import activity.ILoginActivity;

public class LoginMock implements ILoginActivity {

    private boolean login = false;
    private boolean register = false;

    public void reset() {
        login = false;
        register = false;
    }

    public boolean loginEnabled() {
        return login;
    }

    public boolean registerEnabled() {
        return register;
    }

    @Override
    public void setLoginEnabled(boolean enabled) {
        login = enabled;
    }

    @Override
    public void setRegisterEnabled(boolean enabled) {
        register = enabled;
    }

    @Override
    public void displayErrorMessage(String message) {
        //just here for mocking purposes
    }

    @Override
    public void goToGameList() {
        //just here for mocking purposes
    }
}

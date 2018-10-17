package presenter;

import activity.ILoginView;

public class LoginMock implements ILoginView {

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
    public void goToGameList() {
        //just here for mocking purposes
    }
}

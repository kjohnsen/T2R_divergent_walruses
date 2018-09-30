package presenter;

import activity.ILoginActivity;

public class LoginMock implements ILoginActivity {

    @Override
    public void setLoginEnabled(boolean enabled) {
        //just here for mocking purposes
    }

    @Override
    public void setRegisterEnabled(boolean enabled) {
        //just here for mocking purposes
    }
}

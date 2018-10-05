package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoginPresenter {

    private static LoginPresenter loginPresenter;
    private static LoginMock mock;

    @BeforeClass
    public static void setup() {
        mock = new LoginMock();
        loginPresenter = new LoginPresenter(mock);
    }
    @Before
    public void reset() {
        mock.reset();
    }
    @Test
    public void validLogin() {
        loginPresenter.loginUsernameChanged("Steve");
        loginPresenter.loginPasswordChanged("Hi");
        assertTrue(mock.isLogin());
    }
    @Test
    public void validRegister() {
        loginPresenter.registerUsernameChanged("Fred");
        loginPresenter.registerPasswordChanged("Whatwhat");
        loginPresenter.registerConfirmChanged("Whatwhat");
        assertTrue(mock.isRegister());
    }
    @Test
    public void registerConfirmOff() {
        loginPresenter.registerUsernameChanged("Fruit");
        loginPresenter.registerPasswordChanged("Orange");
        loginPresenter.registerConfirmChanged("Pineapple");
        assertTrue(!mock.isRegister());
        loginPresenter.registerConfirmChanged("Orange");
        assertTrue(mock.isRegister());
    }
}

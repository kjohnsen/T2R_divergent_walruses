package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoginPresenter {

    private static LoginPresenter loginPresenter;
<<<<<<< HEAD
    private static LoginMock loginMock;

    @BeforeClass
    public static void setup() {
        loginMock = new LoginMock();
        loginPresenter = new LoginPresenter(loginMock);
    }
    @Before
    public void reset() {
        loginMock.reset();
    }
    @Test
    public void validLoginTest() {
        loginPresenter.loginUsernameChanged("Hammy");
        loginPresenter.loginPasswordChanged("CAFFEINE");
        assertTrue(loginMock.loginEnabled());
        assertTrue(!loginMock.registerEnabled());
    }
    @Test
    public void validRegister() {
        loginPresenter.registerUsernameChanged("RJ");
        loginPresenter.registerPasswordChanged("Doritos");
        loginPresenter.registerConfirmChanged("Doritos");
        assertTrue(loginMock.registerEnabled());
        assertTrue(!loginMock.loginEnabled());
    }
    @Test
    public void registerConfirmUnmatching() {
        loginPresenter.registerUsernameChanged("Verne");
        loginPresenter.registerPasswordChanged("Bark");
        loginPresenter.registerConfirmChanged("Sticks");
        assertTrue(!loginMock.registerEnabled());
    }
    @Test
    public void loginEmptyPassword() {
        loginPresenter.loginPasswordChanged("");
        loginPresenter.loginUsernameChanged("Orange");
        assertTrue(!loginMock.loginEnabled());
    }
    @Test
    public void registerEmptyPassword() {
        loginPresenter.registerUsernameChanged("Orange");
        loginPresenter.registerPasswordChanged("");
        loginPresenter.registerConfirmChanged("");
        assertTrue(!loginMock.registerEnabled());
=======
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
>>>>>>> master
    }
}

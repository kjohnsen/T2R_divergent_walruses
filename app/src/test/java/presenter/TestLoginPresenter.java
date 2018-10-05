package presenter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestLoginPresenter {

    private static LoginPresenter loginPresenter;
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
        loginPresenter.registerConfirmChanged("Bark");
        assertTrue(loginMock.registerEnabled());
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
    }
}

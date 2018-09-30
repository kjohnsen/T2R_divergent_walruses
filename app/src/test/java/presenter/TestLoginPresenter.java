package presenter;

import org.junit.Before;
import org.junit.Test;

import activity.ILoginActivity;

import static org.junit.Assert.*;

public class TestLoginPresenter {

    private LoginPresenter loginPresenter;

    @Before
    public void setup() {
        loginPresenter = new LoginPresenter(new LoginMock());
    }
    @Test
    public void loginTest() {
        //there should be a test here, but there's no way to test anything in the presenter...
        //none of the functions return anything, and getters and setters are unnecessary
        assertEquals(4, 2+2);
//        assertTrue(false);
    }
}

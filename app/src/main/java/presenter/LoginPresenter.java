package presenter;

import activity.ILoginActivity;
import model.UIFacade;

public class LoginPresenter implements ILoginPresenter {
    private String loginUsername = null;
    private String loginPassword = null;
    private String registerUsername = null;
    private String registerPassword = null;
    private String registerConfirm = null;
    private ILoginActivity activity;

    public LoginPresenter(ILoginActivity activity) { this.activity = activity; }

    @Override
    public void login() {
        UIFacade.getInstance().loginUser(loginUsername, loginPassword);
    }

    @Override
    public void register() {
        UIFacade.getInstance().registerUser(registerUsername, registerPassword);
    }

    @Override
    public void loginUsernameChanged(String username) {
        loginUsername = username;
        checkLogin();
    }

    @Override
    public void loginPasswordChanged(String password) {
        loginPassword = password;
        checkLogin();
    }

    @Override
    public void registerUsernameChanged(String username) {
        registerUsername = username;
        checkRegister();
    }

    @Override
    public void registerPasswordChanged(String password) {
        registerPassword = password;
        checkRegister();
    }

    @Override
    public void registerConfirmChanged(String confirm) {
        registerConfirm = confirm;
        checkRegister();
    }

    private void checkLogin() {
        boolean allow = (loginUsername != null && loginPassword != null);
        activity.setLoginEnabled(allow);
    }

    private void checkRegister() {
        boolean allow = (registerUsername != null && registerPassword != null
                && registerPassword.equals(registerConfirm));
        activity.setRegisterEnabled(allow);
    }
}

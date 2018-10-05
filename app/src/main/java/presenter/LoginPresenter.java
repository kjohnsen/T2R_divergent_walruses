package presenter;

import android.content.Intent;

import java.util.Observable;
import java.util.Observer;

import activity.IGameListActivity;
import activity.ILoginActivity;
import model.ClientModel;
import model.UIFacade;

public class LoginPresenter implements ILoginPresenter, Observer {
    private String loginUsername = "";
    private String loginPassword = "";
    private String registerUsername = "";
    private String registerPassword = "";
    private String registerConfirm = "";
    private ILoginActivity activity;

    public LoginPresenter(ILoginActivity activity) {
        this.activity = activity;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void login() {
        String message = UIFacade.getInstance().loginUser(loginUsername, loginPassword);
        if (message != null) {
            activity.displayErrorMessage(message);
        }
    }

    @Override
    public void register() {
        String message = UIFacade.getInstance().registerUser(registerUsername, registerPassword);
        if (message != null) {
            activity.displayErrorMessage(message);
        }
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
        boolean allow = (!loginUsername.isEmpty() && !loginPassword.isEmpty());
        activity.setLoginEnabled(allow);
    }

    private void checkRegister() {
        boolean allow = (!registerUsername.isEmpty() && !registerPassword.isEmpty()
                && registerPassword.equals(registerConfirm));
        activity.setRegisterEnabled(allow);
    }

    @Override
    public void hostIPChanged(String hostIP) {
        UIFacade.getInstance().setHostIP(hostIP);
    }

    @Override
    public void hostPortChanged(String hostPort) {
        UIFacade.getInstance().setHostPort(hostPort);
    }

    public void update(Observable observable, Object o) {
        observable.deleteObserver(this);
        activity.goToGameList();
    }
}

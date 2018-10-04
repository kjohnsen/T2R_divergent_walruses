package activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import presenter.ILoginPresenter;
import presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    EditText loginUsername;
    EditText loginPassword;
    Button loginButton;
    EditText registerUsername;
    EditText registerPassword;
    EditText registerConfirm;
    Button registerButton;
    ILoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        loginUsername = findViewById(R.id.loginUsername);
        loginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.loginUsernameChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginPassword = findViewById(R.id.loginPassword);
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.loginPasswordChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        registerUsername = findViewById(R.id.registerUsername);
        registerUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.registerUsernameChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        registerPassword = findViewById(R.id.registerPassword);
        registerPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.registerPasswordChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        registerConfirm = findViewById(R.id.confirm);
        registerConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.registerConfirmChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.register();
            }
        });
    }

    @Override
    public void setLoginEnabled(boolean enabled) {
        if (loginButton != null) {
            loginButton.setEnabled(enabled);
        }
    }

    @Override
    public void setRegisterEnabled(boolean enabled) {
        if (registerButton != null) {
            registerButton.setEnabled(enabled);
        }
    }

    @Override
    public void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToGameList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}

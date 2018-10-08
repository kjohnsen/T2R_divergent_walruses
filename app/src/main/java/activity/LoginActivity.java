package activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.content.SharedPreferences;
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

    EditText hostIP;
    EditText hostPort;
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
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String storedUsername = sharedPref.getString("storedUsername","");
        String storedPassword = sharedPref.getString("storedPassword","");
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
                Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString("storedUsername", loginUsername.getText().toString());
                editor.putString("storedPassword", loginPassword.getText().toString());
                editor.apply();
                LoginTask l = new LoginTask();
                l.execute();
            }
        });
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                RegisterTask r = new RegisterTask();
                r.execute();
        }});
        if (!storedUsername.equals("") && !storedPassword.equals("")) {
            loginUsername.setText(storedUsername);
            presenter.loginUsernameChanged(storedUsername);
            loginPassword.setText(storedPassword);
            presenter.loginPasswordChanged(storedPassword);
        }
        hostIP = findViewById(R.id.hostIP);
        hostIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.hostIPChanged(charSequence.toString());
                presenter.hostIPChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hostIP.setText("10.0.2.2");
        hostPort = findViewById(R.id.hostPort);
        hostPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.hostPortChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        hostPort.setText("5000");
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

    public class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... s) {
            return presenter.register();
        }
        @Override
        protected void onPostExecute(String message) {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    public class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... s) {
            return presenter.login();
        }
        @Override
        protected void onPostExecute(String message) {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void goToGameList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}

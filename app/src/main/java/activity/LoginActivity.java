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

public class LoginActivity extends AppCompatActivity implements ILoginView {

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
        String prefLoginUsername = sharedPref.getString("loginUsername","");
        String prefLoginPassword = sharedPref.getString("loginPassword","");
        String prefRegisterUsername = sharedPref.getString("registerUsername","");
        String prefRegisterPassword = sharedPref.getString("registerPassword","");
        String prefRegisterConfirm = sharedPref.getString("registerConfirm","");
        String prefIP = sharedPref.getString("IP","");
        String prefPort = sharedPref.getString("port","");
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString("loginUsername", loginUsername.getText().toString());
                editor.putString("loginPassword", loginPassword.getText().toString());
                editor.putString("IP", hostIP.getText().toString());
                editor.putString("port", hostPort.getText().toString());
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
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.putString("registerUsername", registerUsername.getText().toString());
                editor.putString("registerPassword", registerPassword.getText().toString());
                editor.putString("registerConfirm", registerConfirm.getText().toString());
                editor.putString("IP", hostIP.getText().toString());
                editor.putString("port", hostPort.getText().toString());
                editor.apply();
                RegisterTask r = new RegisterTask();
                r.execute();
            }});
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
        if (!prefLoginUsername.equals("")) {
            loginUsername.setText(prefLoginUsername);
        }
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
        if (!prefLoginPassword.equals("")) {
            loginPassword.setText(prefLoginPassword);
        }
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
        if (!prefRegisterUsername.equals("")) {
            registerUsername.setText(prefRegisterUsername);
        }
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
        if (!prefRegisterPassword.equals("")) {
            registerPassword.setText(prefRegisterPassword);
        }
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
        if (!prefRegisterConfirm.equals("")) {
            registerConfirm.setText(prefRegisterConfirm);
        }
        hostIP = findViewById(R.id.hostIP);
        hostIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.hostIPChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (!prefIP.equals("")) {
            hostIP.setText(prefIP);
        }
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
        if (!prefPort.equals("")) {
            hostPort.setText(prefPort);
        }
    }

    @Override
    public void onBackPressed() {}

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
            if (message != null) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... s) {
            return presenter.login();
        }
        @Override
        protected void onPostExecute(String message) {
            if (message != null) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void goToGameList() {
        Intent intent = new Intent(this, GameListActivity.class);
        startActivity(intent);
    }
}

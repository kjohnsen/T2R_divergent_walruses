package com.example.emilyhales.tickettoride.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.emilyhales.tickettoride.R;

public class LoginActivity extends AppCompatActivity implements ILoginActivity {

    EditText loginUsername;
    EditText loginPassword;
    Button loginButton;
    EditText registerUsername;
    EditText registerPassword;
    EditText registerConfirm;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerUsername = findViewById(R.id.registerUsername);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirm = findViewById(R.id.confirm);
        registerButton = findViewById(R.id.registerButton);
    }
}

package com.example.emilyhales.tickettoride.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emilyhales.tickettoride.R;

public class GameLobbyActivity extends AppCompatActivity implements IGameLobbyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
    }
}

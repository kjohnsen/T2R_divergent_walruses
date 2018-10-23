package activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import adapter.GameListAdapter;
import clientserver.ServerPoller;
import modelclasses.GameInfo;
import modelclasses.Player;
import presenter.GameListPresenter;
import presenter.IGameListPresenter;

public class GameListActivity extends AppCompatActivity implements IGameListView {

    private RecyclerView gameList;
    private GameListAdapter gameListAdapter;
    private EditText gameName;
    private Spinner numPlayers;
    private Button createGameButton;
    private IGameListPresenter presenter;
    private boolean doneLoading = false;

    @Override
    public void joinGame(String name) {
        JoinGameTask j = new JoinGameTask();
        j.execute(name);
    }

    @Override
    public void populateGameList(final List<GameInfo> games) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (doneLoading) {
                    gameList = findViewById(R.id.gameList);
                    RecyclerView.LayoutManager gameListManager = new LinearLayoutManager(GameListActivity.this);
                    gameList.setLayoutManager(gameListManager);
                    gameListAdapter = new GameListAdapter(games, GameListActivity.this, GameListActivity.this);
                    gameList.setAdapter(gameListAdapter);
                }
            }
        });
    }

    @Override
    public void setCreateGameEnabled(final boolean enabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createGameButton.setEnabled(enabled);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        //start the ServerPoller
        Intent intent = new Intent(this, ServerPoller.class);
        getApplicationContext().startService(intent);
        presenter = new GameListPresenter(this);
        gameName = findViewById(R.id.gameName);
        gameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.createGameNameChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        numPlayers = findViewById(R.id.numPlayers);
        //set up player number spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.num_players_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPlayers.setAdapter(adapter);
        createGameButton = findViewById(R.id.createGame);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGameTask c = new CreateGameTask();
                c.execute(Integer.parseInt(numPlayers.getSelectedItem().toString()));
            }
        });
        gameList = findViewById(R.id.gameList);
        RecyclerView.LayoutManager gameListManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(gameListManager);
        //initialize the view with current info
        doneLoading = true;
        presenter.getGameListInfo();
    }

    public class CreateGameTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... s) {
            return presenter.createGame(s[0]);
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(GameListActivity.this, param, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class JoinGameTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... s) {
            return presenter.joinGame(s[0]);
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(GameListActivity.this, param, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void goToGameLobby() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameListActivity.this, GameLobbyActivity.class);
                startActivity(intent);
            }
        });
    }
}

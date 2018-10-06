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

import clientserver.ServerPoller;
import modelclasses.GameInfo;
import modelclasses.Player;
import presenter.GameListPresenter;
import presenter.IGameListPresenter;

public class GameListActivity extends AppCompatActivity implements IGameListActivity {

    private RecyclerView gameList;
    private EditText gameName;
    private Spinner numPlayers;
    private Button createGameButton;
    private IGameListPresenter presenter;

    @Override
    public void populateGameList(ArrayList<GameInfo> games) {
        GameListAdapter gameListAdapter = new GameListAdapter(games);
        gameList.setAdapter(gameListAdapter);
    }

    @Override
    public void setCreateGameEnabled(boolean enabled) {
        createGameButton.setEnabled(enabled);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        ServerPoller.getInstance().startService(null);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.num_players_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPlayers.setAdapter(adapter);
        createGameButton = findViewById(R.id.createGame);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGameTask c = new CreateGameTask();
                c.doInBackground((Integer)numPlayers.getSelectedItem());
            }
        });
        gameList = findViewById(R.id.gameList);
        RecyclerView.LayoutManager gameListManager = new LinearLayoutManager(this);
        gameList.setLayoutManager(gameListManager);
    }

    public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameHolder> {
        private ArrayList<GameInfo> games;
        GameListAdapter(ArrayList<GameInfo> games) {
            this.games = games;
        }
        @Override
        @NonNull
        public GameListAdapter.GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GameListActivity.this);
            return new GameHolder(inflater.inflate(R.layout.item_game_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull GameListAdapter.GameHolder holder, int position) {
            String gameName = games.get(position).getGameName().getName();
            StringBuilder players = new StringBuilder();
            ArrayList<Player> playerList = games.get(position).getPlayers();
            for (int i = 0; i < playerList.size(); i++) {
                players.append(playerList.get(i).getUsername());
                if (i < playerList.size() - 1) {
                    players.append(", ");
                }
            }
            int spotsLeft = games.get(position).getNumPlayers() - playerList.size();
            holder.bind(gameName, players.toString(), Integer.toString(spotsLeft));
        }

        @Override
        public int getItemCount() {
            return games.size();
        }
        class GameHolder extends RecyclerView.ViewHolder {
            private TextView gameName;
            private TextView gamePlayers;
            private TextView numSpots;
            GameHolder(View view) {
                super(view);
                gameName = findViewById(R.id.itemName);
                gamePlayers = findViewById(R.id.itemPlayers);
                numSpots = findViewById(R.id.itemNum);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JoinGameTask j = new JoinGameTask();
                        j.execute(gameName.getText().toString());
                    }
                });
            }
            void bind(String name, String players, String spotsLeft) {
                gameName.setText(name);
                gamePlayers.setText(players);
                numSpots.setText(spotsLeft);
            }
        }
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
        Intent intent = new Intent(this, GameLobbyActivity.class);
        startActivity(intent);
    }
}

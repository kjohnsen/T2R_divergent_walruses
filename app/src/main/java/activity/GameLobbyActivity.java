package activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;

import modelclasses.GameName;
import modelclasses.Player;
import presenter.GameLobbyPresenter;
import presenter.IGameLobbyPresenter;

public class GameLobbyActivity extends AppCompatActivity implements IGameLobbyActivity {

    private RecyclerView playerList;
    private Spinner colorSpinner;
    private Button startGameButton;
    private IGameLobbyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        presenter = new GameLobbyPresenter(this);
        colorSpinner = findViewById(R.id.colorSpinner);
        colorSpinner.setSelection(0);
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    ChooseColorTask c = new ChooseColorTask();
                    c.execute(adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        startGameButton = findViewById(R.id.startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame();
            }
        });
        playerList = findViewById(R.id.playerList);
        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);
        //initialize the view with current game info
        presenter.getGameLobbyInfo();
    }

    public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerHolder> {
        private ArrayList<Player> players;
        PlayerListAdapter(ArrayList<Player> players) {
            this.players = players;
        }
        @Override
        @NonNull
        public PlayerListAdapter.PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GameLobbyActivity.this);
            return new PlayerHolder(inflater.inflate(R.layout.item_player_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull PlayerListAdapter.PlayerHolder holder, int position) {
            String playerName = players.get(position).getUsername();
            String playerColor = players.get(position).getPlayerColor().name();
            holder.bind(playerName, playerColor);
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
        class PlayerHolder extends RecyclerView.ViewHolder {
            private TextView playerName;
            private TextView playerColor;
            PlayerHolder(View view) {
                super(view);
                playerName = view.findViewById(R.id.itemName);
                playerColor = view.findViewById(R.id.itemColor);
            }
            void bind(String name, String color) {
                playerName.setText(name);
                playerColor.setText(color);
            }
        }
    }

    @Override
    public void updateAvailableColors(final ArrayList<String> colors) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(GameLobbyActivity.this,
                        android.R.layout.simple_spinner_item, colors);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                colorSpinner.setAdapter(adapter);
            }
        });
    }

    @Override
    public void updatePlayerList(final ArrayList<Player> players) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PlayerListAdapter playerListAdapter = new PlayerListAdapter(players);
                playerList.setAdapter(playerListAdapter);
            }
        });
    }

    @Override
    public void setStartGameEnabled(final boolean enabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startGameButton.setEnabled(enabled);
            }
        });
    }

    public class ChooseColorTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... s) {
            return presenter.chooseColor(s[0]);
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(GameLobbyActivity.this, param, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class StartGameTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... s) {
            return presenter.startGame();
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(GameLobbyActivity.this, param, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void startGame() {
        //currently doesn't do anything-- I'll update it when we start phase 2
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GameLobbyActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}

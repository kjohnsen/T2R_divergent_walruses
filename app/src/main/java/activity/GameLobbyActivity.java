package activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;

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
        startGameButton = findViewById(R.id.startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startGame();
            }
        });
        playerList = findViewById(R.id.gameList);
        RecyclerView.LayoutManager playerListManager = new LinearLayoutManager(this);
        playerList.setLayoutManager(playerListManager);
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
                playerName = findViewById(R.id.itemName);
                playerColor = findViewById(R.id.itemColor);
            }
            void bind(String name, String color) {
                playerName.setText(name);
                playerColor.setText(color);
            }
        }
    }

    @Override
    public void updateAvailableColors(ArrayList<String> colors) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
    }

    @Override
    public void updatePlayerList(ArrayList<Player> players) {
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(players);
        playerList.setAdapter(playerListAdapter);
    }

    @Override
    public void setStartGameEnabled(boolean enabled) {
        startGameButton.setEnabled(enabled);
    }
}

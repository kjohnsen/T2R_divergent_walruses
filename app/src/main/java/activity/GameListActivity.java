package activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;

import model.ClientModel;
import model.UIFacade;
import modelclasses.GameID;
import modelclasses.GameInfo;
import modelclasses.Player;
import presenter.GameListPresenter;
import presenter.IGameListPresenter;

public class GameListActivity extends AppCompatActivity implements IGameListActivity {

    private RecyclerView gameList;
    private GameListAdapter gameListAdapter;
    private RecyclerView.LayoutManager gameListManager;
    private EditText gameName;
    private Spinner numPlayers;
    private Button createGameButton;
    private IGameListPresenter presenter;

    @Override
    public void populateGameList() {

    }

    @Override
    public void setCreateGameEnabled() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        presenter = new GameListPresenter(this);
        gameName = findViewById(R.id.gameName);
        numPlayers = findViewById(R.id.numPlayers);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.num_players_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPlayers.setAdapter(adapter);
        createGameButton = findViewById(R.id.createGame);
        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createGame(gameName.getText().toString(),Integer.parseInt(numPlayers.getSelectedItem().toString()));
            }
        });
    }

    public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameHolder> {
        private GameInfo games[];
        public GameListAdapter(GameInfo r[]) {
            games = r;
        }
        @Override
        @NonNull
        public GameListAdapter.GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GameListActivity.this);
            return new GameHolder(inflater.inflate(R.layout.item_game_list, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull GameListAdapter.GameHolder holder, int position) {
            String gameName = games[position].getName();
            StringBuilder players = new StringBuilder();
            ArrayList<Player> playerList = games[position].getPlayers();
            for (int i = 0; i < playerList.size(); i++) {
                players.append(playerList.get(i).getUsername());
                if (i < playerList.size() - 1) {
                    players.append(", ");
                }
            }
            int spotsLeft = games[position].getTotalPlayers() - playerList.size();
            holder.bind(gameName, players.toString(), Integer.toString(spotsLeft));
        }

        @Override
        public int getItemCount() {
            return games.length;
        }
        class GameHolder extends RecyclerView.ViewHolder {
            private TextView gameName;
            private TextView gamePlayers;
            private TextView numSpots;
            private GameID gameID;
            GameHolder(View view) {
                super(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String username = ClientModel.getInstance().getCurrentUser().getUsername();
                        UIFacade.getInstance().joinGame(username, gameID);
                    }
                });
                gameName = findViewById(R.id.itemName);
                gamePlayers = findViewById(R.id.itemPlayers);
                numSpots = findViewById(R.id.itemNum);
            }
            void bind(String name, String players, String spotsLeft) {
                gameName.setText(name);
                gamePlayers.setText(players);
                numSpots.setText(spotsLeft);
            }
        }
    }
}

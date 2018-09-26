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
        private main.java.model.GameInfo games[];
        private int lastSelectedPosition = -1;
        public GameListAdapter(main.java.model.GameInfo r[]) {
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
            String option = games[position].toString();
            holder.bind(option, position);
        }

        @Override
        public int getItemCount() {
            return games.length;
        }
        class GameHolder extends RecyclerView.ViewHolder {
            private String option;
            private TextView eTextView;
            GameHolder(View view) {
                super(view);
                eTextView = view.findViewById(R.id.itemName);
            }
            void bind(String o, int position) {
                option = o;
                eTextView.setText(option);
            }
        }
    }
}

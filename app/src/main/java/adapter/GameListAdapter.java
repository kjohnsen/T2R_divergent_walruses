package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import activity.IGameListView;
import modelclasses.GameInfo;
import modelclasses.Player;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameHolder> {
    private List<GameInfo> games;
    private Context context;
    private IGameListView gameListView;
    public GameListAdapter(List<GameInfo> games, Context context, IGameListView view) {
        this.games = games;
        this.context = context;
        gameListView = view;
    }
    @Override
    @NonNull
    public GameListAdapter.GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new GameHolder(inflater.inflate(R.layout.item_game_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull GameListAdapter.GameHolder holder, int position) {
        String gameName = games.get(position).getGameName().getName();
        StringBuilder players = new StringBuilder();
        List<Player> playerList = games.get(position).getPlayers();
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
            gameName = view.findViewById(R.id.itemName);
            gamePlayers = view.findViewById(R.id.itemPlayers);
            numSpots = view.findViewById(R.id.itemNum);
            //if someone taps the game, it should go to GameLobby
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameListView.joinGame(gameName.getText().toString());
                }
            });
        }
        void bind(String name, String players, String spotsLeft) {
            if (gameName != null) {
                gameName.setText(name);
                gamePlayers.setText(players);
                numSpots.setText(spotsLeft);
            }
        }
    }
}

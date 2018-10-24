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

import activity.IGameLobbyView;
import modelclasses.Player;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerHolder> {
    private List<Player> players;
    private Context context;
    private IGameLobbyView gameLobbyView;
    public PlayerListAdapter(List<Player> players, Context context, IGameLobbyView view) {
        this.players = players;
        this.context = context;
        gameLobbyView = view;
    }
    @Override
    @NonNull
    public PlayerListAdapter.PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
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

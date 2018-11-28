package activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import modelclasses.PlayerSummary;
import presenter.IGameEndPresenter;
import presenter.GameEndPresenter;

public class GameEndView extends AppCompatActivity implements IGameEndView {

    private IGameEndPresenter presenter;
    private RecyclerView endGameInfoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GameEndPresenter(this);
        setContentView(R.layout.activity_game_end_view);
        endGameInfoRecyclerView = findViewById(R.id.endGameInfoRecyclerView);
        endGameInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        EndGameAdapter endGameAdapter = new EndGameAdapter();
        endGameAdapter.setPlayerEndInfo(presenter.getPlayerSummaryInfo());
        endGameInfoRecyclerView.setAdapter(endGameAdapter);
    }

    public IGameEndPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IGameEndPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void updateGameEndInfo() {}

    private class EndGameAdapter extends RecyclerView.Adapter<EndGameAdapter.EndGameInfoViewHolder> {

        private List<PlayerSummary> playerEndInfo;

        @NonNull
        @Override
        public EndGameInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View endGameView = layoutInflater.inflate(R.layout.item_end_game_list, parent, false);
            EndGameInfoViewHolder endGameInfoViewHolder = new EndGameInfoViewHolder(endGameView);
            return endGameInfoViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull EndGameInfoViewHolder holder, int position) {
            PlayerSummary summary = playerEndInfo.get(position);
            holder.setPlayerSummaryInfo(summary);
        }

        @Override
        public int getItemCount() {
            return playerEndInfo.size();
        }

        public void setPlayerEndInfo(List<PlayerSummary> playerEndInfo) {
            this.playerEndInfo = playerEndInfo;
        }

        public class EndGameInfoViewHolder extends RecyclerView.ViewHolder {

            private TextView playerNameTextView;
            private TextView totalPointsTextView;
            private TextView winnerTextView;
            private TextView pointsFromRoutesTextView;
            private TextView pointsLostFromDestCardsTextView;
            private TextView pointsGainedFromDestCardsTextView;
            private TextView mostRoutesPointsTextView;

            public EndGameInfoViewHolder(View itemView) {
                super(itemView);
                playerNameTextView = itemView.findViewById(R.id.nameTextView);
                totalPointsTextView = itemView.findViewById(R.id.totalPointsView);
                winnerTextView = itemView.findViewById(R.id.winnerView);
                pointsFromRoutesTextView = itemView.findViewById(R.id.routePointsView);
                pointsLostFromDestCardsTextView = itemView.findViewById(R.id.lostDestPointsView);
                pointsGainedFromDestCardsTextView = itemView.findViewById(R.id.gaineDestPointsView);
                mostRoutesPointsTextView = itemView.findViewById(R.id.mostClaimedRoutesView);
            }

            public void setPlayerSummaryInfo(PlayerSummary summary) {
                playerNameTextView.setText(summary.getPlayerName());
                totalPointsTextView.setText(Integer.toString(summary.getTotalPoints()));
                pointsFromRoutesTextView.setText(Integer.toString(summary.getPointsFromRoutes()));
                pointsLostFromDestCardsTextView.setText(Integer.toString(summary.getPointsLostFromDestinations()));
                pointsGainedFromDestCardsTextView.setText(Integer.toString(summary.getPointsGainedFromDestinations()));
                mostRoutesPointsTextView.setText(Integer.toString(summary.getMostClaimedRoutesPoints()));
                if(!summary.isWinner()) {
                    winnerTextView.setText("");
                }
            }
        }
    }
}

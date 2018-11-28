package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import adapter.PlayerInfoListViewAdapter;
import model.ClientModel;
import modelclasses.Atlas;
import modelclasses.DestinationCard;
import modelclasses.Player;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;
import presenter.GameInfoPresenter;
import presenter.IGameListPresenter;

public class GameInfoFragment extends Fragment implements IGameInfoView {

    View v;
    TextView currentPlayer;
    ListView listView;
    TextView destDeck;
    TextView trainDeck;
    GameInfoPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_game_info, container, false);
        presenter = new GameInfoPresenter(this);

        currentPlayer = v.findViewById(R.id.current_player);
        listView = v.findViewById(R.id.list_view_player_info);
        destDeck = v.findViewById(R.id.destinationDeck);
        trainDeck = v.findViewById(R.id.trainDeck);

        presenter.initialUpdate();

        return v;
    }


    @Override
    public void updateCurrentPlayer(final String player) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String currentPlayerText = "Player Turn: " + player;
                currentPlayer.setText(currentPlayerText);
            }
        });
    }


    @Override
    public void updateTrainDeckInfo(final int trainDeckSize) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String trainString = "Train cards left: " + trainDeckSize;
                trainDeck.setText(trainString);
            }
        });
    }

    @Override
    public void updateDestDeckInfo(final int destDeckSize) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String destString = "Destination cards left: " + destDeckSize;
                destDeck.setText(destString);
            }
        });
    }

    @Override
    public void updatePlayerInfo(final List<Player> players) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PlayerInfoListViewAdapter listViewAdapter = new PlayerInfoListViewAdapter(getContext(), players);
                listView.setAdapter(listViewAdapter);
            }
        });
    }
}

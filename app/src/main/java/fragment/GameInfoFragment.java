package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import adapter.PlayerInfoListViewAdapter;
import model.ClientModel;
import modelclasses.Player;
import presenter.GameInfoPresenter;
import presenter.IGameListPresenter;

public class GameInfoFragment extends Fragment implements IGameInfoView {

    View v;
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

        listView = v.findViewById(R.id.list_view_player_info);
        destDeck = v.findViewById(R.id.destinationDeck);
        trainDeck = v.findViewById(R.id.trainDeck);

        presenter.initialUpdate();

        return v;
    }

    @Override
    public void updateDecksInfo(final int destDeckSize, final int trainDeckSize) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String destString = "Destination cards left: " + destDeckSize;
                String trainString = "Train cards left: " + trainDeckSize;
                destDeck.setText(destString);
                trainDeck.setText(trainString);
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

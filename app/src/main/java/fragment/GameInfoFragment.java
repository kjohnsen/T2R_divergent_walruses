package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import adapter.PlayerInfoListViewAdapter;
import model.ClientModel;
import modelclasses.Player;
import presenter.IGameListPresenter;

public class GameInfoFragment extends Fragment implements IGameInfoView {

    View v;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_game_info, container, false);
        listView = v.findViewById(R.id.list_view_player_info);

        return v;
    }

    @Override
    public void updatePlayerInfo(List<Player> players) {
        PlayerInfoListViewAdapter listViewAdapter = new PlayerInfoListViewAdapter(this.getContext(), players);
        listView.setAdapter(listViewAdapter);
    }
}

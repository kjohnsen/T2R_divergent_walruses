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
    private int buttonIndex = 0;
    private String[] toastMessages = {"Updating opponent train cards",
                                      "Updating opponent train car pieces",
                                      "Updating opponent destination cards",
                                      "Updating player scores"};



    Button testButton;

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

        testButton = v.findViewById(R.id.testButton);
        testButton.setText(String.valueOf("Test Model Button"));
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonIndex < toastMessages.length) {
                    Toast.makeText(getActivity(), toastMessages[buttonIndex], Toast.LENGTH_LONG).show();

                    switch (buttonIndex) {
                        case 0:
                            ClientModel.getInstance().getCurrentGame().getPlayers().get(1).addTrainCardToHand(new TrainCard(TrainCardColor.WHITE));
                            ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
                            break;
                        case 1:
                            ClientModel.getInstance().getCurrentGame().getPlayers().get(1).setNumberOfTrains(12);
                            ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
                            break;
                        case 2:
                            ClientModel.getInstance().getCurrentGame().getPlayers().get(1).addDestCardToHand(Atlas.getDestinations()[0]);
                            ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
                            break;
                        case 3:
                            ClientModel.getInstance().getCurrentGame().getPlayers().get(0).addPoints(35);
                            ClientModel.getInstance().getCurrentGame().getPlayers().get(1).addPoints(23);
                            ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame().getPlayers());
                        case 4:
                            ClientModel.getInstance().getCurrentGame().changeTurn();
                            ClientModel.getInstance().notifyObservers(ClientModel.getInstance().getCurrentGame());
                    }

                    buttonIndex += 1;
                    if(buttonIndex == 4)
                        buttonIndex = 0;
                }
            }
        });

        presenter.initialUpdate();

        return v;
    }


    @Override
    public void updateCurrentPlayer(final Player player) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentPlayer.setText(player.getUsername());
            }
        });
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

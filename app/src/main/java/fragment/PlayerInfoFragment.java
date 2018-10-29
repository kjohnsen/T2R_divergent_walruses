package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.DestinationCardListViewAdapter;
import adapter.PlayerInfoListViewAdapter;
import model.ClientModel;
import modelclasses.DestinationCard;
import modelclasses.Player;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;
import presenter.PlayerInfoPresenter;

public class PlayerInfoFragment extends Fragment implements IPlayerInfoView {

    TextView cardRed;
    TextView cardOrange;
    TextView cardYellow;
    TextView cardGreen;
    TextView cardBlue;
    TextView cardPurple;
    TextView cardWhite;
    TextView cardBlack;
    TextView cardWild;

    Map<TrainCardColor, TextView> cards_textView;

    private ListView destinationCardList;
    PlayerInfoPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_info, container, false);

        presenter = new PlayerInfoPresenter(this);

        destinationCardList = v.findViewById(R.id.destinationCardList);

        cardRed = v.findViewById(R.id.cardRed);
        cardOrange = v.findViewById(R.id.cardOrange);
        cardYellow = v.findViewById(R.id.cardYellow);
        cardGreen = v.findViewById(R.id.cardGreen);
        cardBlue = v.findViewById(R.id.cardBlue);
        cardPurple = v.findViewById(R.id.cardPurple);
        cardWhite = v.findViewById(R.id.cardWhite);
        cardBlack = v.findViewById(R.id.cardBlack);
        cardWild = v.findViewById(R.id.cardWild);

        //set colors of card views
        cardRed.setBackgroundColor(getResources().getColor(R.color.trainRed));
        cardOrange.setBackgroundColor(getResources().getColor(R.color.trainOrange));
        cardYellow.setBackgroundColor(getResources().getColor(R.color.trainYellow));
        cardGreen.setBackgroundColor(getResources().getColor(R.color.trainGreen));
        cardBlue.setBackgroundColor(getResources().getColor(R.color.trainBlue));
        cardPurple.setBackgroundColor(getResources().getColor(R.color.trainPurple));
        cardWhite.setBackgroundColor(getResources().getColor(R.color.trainWhite));
        cardBlack.setBackgroundColor(getResources().getColor(R.color.trainBlack));
        cardWild.setBackgroundColor(getResources().getColor(R.color.trainPink));

        cards_textView = new HashMap<>();
        cards_textView.put(TrainCardColor.RED, cardRed);
        cards_textView.put(TrainCardColor.ORANGE, cardOrange);
        cards_textView.put(TrainCardColor.YELLOW, cardYellow);
        cards_textView.put(TrainCardColor.GREEN, cardGreen);
        cards_textView.put(TrainCardColor.BLUE, cardBlue);
        cards_textView.put(TrainCardColor.PURPLE, cardPurple);
        cards_textView.put(TrainCardColor.WHITE, cardWhite);
        cards_textView.put(TrainCardColor.BLACK, cardBlack);
        cards_textView.put(TrainCardColor.WILD, cardWild);

        //set on click listeners for all cards
        for(final TrainCardColor trainCardColor : cards_textView.keySet()){
            cards_textView.get(trainCardColor).setTextSize(40);

            if(trainCardColor == TrainCardColor.BLACK)
                cards_textView.get(trainCardColor).setTextColor(Color.WHITE);

            cards_textView.get(trainCardColor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cards_textView.get(trainCardColor).bringToFront();
                }
            });
        }

        presenter.initialUpdate();

        return v;
    }

    //update texts for each card
    @Override
    public void updateTrainCards(Map<TrainCardColor, Integer> cards_amount) {
        for(TrainCardColor trainCardColor: cards_textView.keySet()){
            cards_textView.get(trainCardColor).setText(String.valueOf(cards_amount.get(trainCardColor)));
        }
    }

    @Override
    public void updateDestinationTickets(List<DestinationCard> destinationCards) {
        //this should update the list view.
        DestinationCardListViewAdapter listViewAdapter = new DestinationCardListViewAdapter(this.getContext(), destinationCards);
        destinationCardList.setAdapter(listViewAdapter);
    }
}

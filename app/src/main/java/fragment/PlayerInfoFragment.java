package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelclasses.DestinationCard;
import modelclasses.TrainCard;
import modelclasses.TrainCardColor;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_info, container, false);

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
        cardRed.setBackgroundColor(0xFF0000);
        cardOrange.setBackgroundColor(0xFFA500);
        cardYellow.setBackgroundColor(0xFFFF00);
        cardGreen.setBackgroundColor(0x00FF00);
        cardBlue.setBackgroundColor(0x0000FF);
        cardPurple.setBackgroundColor(0x800080);
        cardWhite.setBackgroundColor(0xFFFFFF);
        cardBlack.setBackgroundColor(0x000000);
        cardWild.setBackgroundColor(0xD3D3D3);

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
            cards_textView.get(trainCardColor).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cards_textView.get(trainCardColor).bringToFront();
                }
            });
        }

        return v;
    }

    //update texts for each card
    @Override
    public void updateTrainCards(Map<TrainCardColor, Integer> cards_amount) {
        cardRed.setText(cards_amount.get(TrainCardColor.RED));
        for(TrainCardColor trainCardColor: cards_textView.keySet())
            cards_textView.get(trainCardColor).setText(cards_amount.get(trainCardColor));
    }

    @Override
    public void updateDestinationTickets(List<DestinationCard> destinationCards) {

    }
}

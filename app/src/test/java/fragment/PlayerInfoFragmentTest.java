package fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.ClientModel;
import model.CommandFacade;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.TrainCardColor;
import modelclasses.User;

import static org.junit.Assert.assertEquals;

public class PlayerInfoFragmentTest implements IPlayerInfoView {

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


    @Before
    public void setup() {

        cards_textView = new LinkedHashMap<>();
        cards_textView.put(TrainCardColor.RED, cardRed);
        cards_textView.put(TrainCardColor.ORANGE, cardOrange);
        cards_textView.put(TrainCardColor.YELLOW, cardYellow);
        cards_textView.put(TrainCardColor.GREEN, cardGreen);
        cards_textView.put(TrainCardColor.BLUE, cardBlue);
        cards_textView.put(TrainCardColor.PURPLE, cardPurple);
        cards_textView.put(TrainCardColor.WHITE, cardWhite);
        cards_textView.put(TrainCardColor.BLACK, cardBlack);
        cards_textView.put(TrainCardColor.WILD, cardWild);

    }

    @After
    public void tearDown() {

    }

    //not going to initialize all the textviews... because i'm not passing in a view.
    //just want to see how this treemap sorts!
    @Test
    public void bringTrainCardsFront() {

        for(final TrainCardColor trainCardColor : cards_textView.keySet()) {

            ArrayList<TrainCardColor> keys = new ArrayList<TrainCardColor>(cards_textView.keySet());

            //loop from back
            for (int i = keys.size() - 1; i >= 0; i--) {
                if (keys.get(i) == trainCardColor) {
                    break;
                }
                //cards_textView.get(keys.get(i)).bringToFront();
            }

            //loop from front
            for (int i = 0; i < keys.size(); i++) {
                if (keys.get(i) == trainCardColor) {
                    break;
                } else {
                    //cards_textView.get(keys.get(i)).bringToFront();
                }
            }

            //set clicked to front
            //cards_textView.get(trainCardColor).bringToFront();
        }


    }

    @Override
    public void updateTrainCards(Map<TrainCardColor, Integer> cards_amount) {

    }

    @Override
    public void updateDestinationTickets(List<DestinationCard> destinationCards) {

    }
}

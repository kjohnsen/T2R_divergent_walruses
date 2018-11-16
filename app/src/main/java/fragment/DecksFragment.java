package fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import modelclasses.TrainCard;
import presenter.decks.DecksPresenter;
import presenter.decks.IDecksPresenter;

public class DecksFragment extends Fragment implements IDecksView{

    View cardZero;
    View cardOne;
    View cardTwo;
    View cardThree;
    View cardFour;
    View trainDeck;
    View destinationDeck;
    IDecksPresenter presenter;
    Button demoButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_decks, container, false);
        presenter = new DecksPresenter(this);
        if (presenter.isGameStart()) {
            presenter.drawDestinationCards();
        }
        cardZero = v.findViewById(R.id.cardZero);
        cardZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have selected a card", Toast.LENGTH_SHORT).show();
                SelectCardTask s = new SelectCardTask();
                s.execute(0);
            }
        });
        cardOne = v.findViewById(R.id.cardOne);
        cardOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have selected a card", Toast.LENGTH_SHORT).show();
                SelectCardTask s = new SelectCardTask();
                s.execute(1);
            }
        });
        cardTwo = v.findViewById(R.id.cardTwo);
        cardTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have selected a card", Toast.LENGTH_SHORT).show();
                SelectCardTask s = new SelectCardTask();
                s.execute(2);
            }
        });
        cardThree = v.findViewById(R.id.cardThree);
        cardThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have selected a card", Toast.LENGTH_SHORT).show();
                SelectCardTask s = new SelectCardTask();
                s.execute(3);
            }
        });
        cardFour = v.findViewById(R.id.cardFour);
        cardFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have selected a card", Toast.LENGTH_SHORT).show();
                SelectCardTask s = new SelectCardTask();
                s.execute(4);
            }
        });
        trainDeck = v.findViewById(R.id.trainDeck);
        trainDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You have drawn a card", Toast.LENGTH_SHORT).show();
                DrawCardTask s = new DrawCardTask();
                s.execute();
            }
        });
        destinationDeck = v.findViewById(R.id.destinationDeck);
        destinationDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.drawDestinationCards();
            }
        });
        presenter.getFaceupCards();

        return v;
    }

    @Override
    public void replaceTrainCards(final List<TrainCard> cards) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cards.size() == 5){
                    setColor(cards.get(0), cardZero);
                    setColor(cards.get(1), cardOne);
                    setColor(cards.get(2), cardTwo);
                    setColor(cards.get(3), cardThree);
                    setColor(cards.get(4), cardFour);
                }
            }
        });
    }

    private void setColor(TrainCard card, View view) {
        switch (card.getColor()) {
            case WILD: view.setBackgroundColor(getResources().getColor(R.color.trainPink)); break;
            case WHITE: view.setBackgroundColor(getResources().getColor(R.color.trainWhite)); break;
            case BLACK: view.setBackgroundColor(getResources().getColor(R.color.trainBlack)); break;
            case RED: view.setBackgroundColor(getResources().getColor(R.color.trainRed)); break;
            case ORANGE: view.setBackgroundColor(getResources().getColor(R.color.trainOrange)); break;
            case YELLOW: view.setBackgroundColor(getResources().getColor(R.color.trainYellow)); break;
            case GREEN: view.setBackgroundColor(getResources().getColor(R.color.trainGreen)); break;
            case BLUE: view.setBackgroundColor(getResources().getColor(R.color.trainBlue)); break;
            case PURPLE: view.setBackgroundColor(getResources().getColor(R.color.trainPurple)); break;
        }
    }

    @Override
    public void drawDestinationCards() {
//        ChooseDestinationsFragment cd = new ChooseDestinationsFragment();
//        cd.show(DecksFragment.this.getActivity().getSupportFragmentManager(), "example");
    }

    public class SelectCardTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... s) {
            return presenter.selectTrainCard(s[0]);
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(DecksFragment.this.getActivity(), param, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class DrawCardTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground(Integer... s) {
            return presenter.drawTrainCard();
        }
        @Override
        protected void onPostExecute(String param) {
            if (param != null) {
                Toast.makeText(DecksFragment.this.getActivity(), param, Toast.LENGTH_LONG).show();
            }
        }
    }
}

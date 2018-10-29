package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.List;

import modelclasses.DestinationCard;
import modelclasses.Player;


/**
 * Created by Parker on 4/15/18.
 */

public class DestinationCardListViewAdapter extends BaseAdapter {

    /**
     * instance of context to be used in class
     */
    private Context mContext;
    /**
     * events to be populated in list view
     */
    private List<DestinationCard> destinationCards;

    public DestinationCardListViewAdapter(Context context, List<DestinationCard> cards) {
        mContext = context;
        destinationCards = cards;
    }

    @Override
    public int getCount() {
        return destinationCards.size();
    }

    @Override
    public Object getItem(int i) {
        return destinationCards.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.destination_card, null);

        TextView cityOrigin = (TextView) view.findViewById(R.id.city_origin);
        String originString = "Origin: " + destinationCards.get(i).getCities()[0].getName();
        cityOrigin.setText(originString);

        TextView cityDestination = (TextView) view.findViewById(R.id.city_destination);
        String destinationString = "Destination: " + destinationCards.get(i).getCities()[1].getName();
        cityDestination.setText(destinationString);

        TextView pointsText = (TextView) view.findViewById(R.id.points);
        String pointsString = "Points: " + destinationCards.get(i).getPoints();
        pointsText.setText(pointsString);

        return view;
    }
}

package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import modelclasses.DestinationCard;
import modelclasses.Player;
import util.PlayerColorConverter;


/**
 * Created by Parker on 4/15/18.
 */

public class PlayerInfoListViewAdapter extends BaseAdapter {

    /**
     * instance of context to be used in class
     */
    private Context mContext;
    /**
     * events to be populated in list view
     */
    private List<Player> players;

    public PlayerInfoListViewAdapter(Context context, List<Player> _players) {
        mContext = context;
        players = _players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int i) {
        return players.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (viewGroup != null && viewGroup.getChildAt(i) != null) {
            viewGroup.getChildAt(i).setBackgroundColor(
                    PlayerColorConverter.convertPlayerColor(players.get(i).getPlayerColor(), mContext));
        }

        LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.player_info, null);

        //set image view to Person
        //ImageView imageView = (ImageView)view.findViewById(R.id.image_avatar);
        //imageView.setImageResource(R.drawable.map_marker);

        TextView playerName = view.findViewById(R.id.player_name);
        playerName.setText(players.get(i).getUsername());

        //TODO: getPoints() is in player right now... somehow have to calculate points still.
        TextView points = view.findViewById(R.id.points);
        String pointsString = "Points: " + players.get(i).getPoints();
        points.setText(pointsString);

        TextView trainNumber = view.findViewById(R.id.train_number);
        String trainNumberString = "Trains Left: " + players.get(i).getNumberOfTrains();
        trainNumber.setText(trainNumberString);

        TextView trainCardNumber = view.findViewById(R.id.trainCard_number);
        if (players.get(i).getTrainCards() != null) {
            String trainCardString = "Train Cards: " + players.get(i).getTrainCards().size();
            trainCardNumber.setText(trainCardString);
        }

        TextView destinationCardNumber = view.findViewById(R.id.destinationCard_number);
        if (players.get(i).getDestinationCards() != null) {
            String destinationCardString = "Destination Cards: " + players.get(i).getDestinationCards().size();
            destinationCardNumber.setText(destinationCardString);
        }

        return view;
    }
}

package activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.emilyhales.tickettoride.R;

import java.util.ArrayList;
import java.util.Arrays;

import fragment.ChatFragment;
import fragment.DecksFragment;
import fragment.GameInfoFragment;
import fragment.PlayerInfoFragment;
import model.ClientModel;
import modelclasses.Atlas;
import modelclasses.DestinationCard;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerColor;
import modelclasses.TrainCard;
import modelclasses.User;

public class GameActivityTest extends GameActivity {

    ViewPager pager;
    MyPagerAdapter adapter;
    TabLayout tabs;


    public void setupTest(){
        //although this violates MVP... it's going to make it easy to test stuff
        ArrayList<GameInfo> gameInfos = new ArrayList<>();

        GameInfo gameInfo = GameInfo.makeRandomGameInfo();
        gameInfos.add(gameInfo);
        ClientModel.getInstance().setGameList(gameInfos);

        ClientModel.getInstance().setCurrentGame(gameInfo);
        ClientModel.getInstance().setCurrentUser(new User("asdf0", "asdf0"));

        //setting random trains and tickets so player info can see it
        Player asdf0 = gameInfo.getPlayer("asdf0");
        asdf0.setPlayerColor(PlayerColor.GREEN);
        ClientModel.getInstance().setPlayerTrainCards(asdf0.getTrainCards(), "asdf0");
        ClientModel.getInstance().setPlayerTickets(gameInfo.getPlayer("asdf0").getDestinationCards(), "asdf0");
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setupTest();

        super.onCreate(savedInstanceState);
    }
}

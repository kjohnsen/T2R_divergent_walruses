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

import fragment.ChatFragment;
import fragment.DecksFragment;
import fragment.GameInfoFragment;
import fragment.PlayerInfoFragment;
import model.ClientModel;
import modelclasses.GameInfo;
import modelclasses.Player;

public class GameActivityTest extends GameActivity {

    ViewPager pager;
    MyPagerAdapter adapter;
    TabLayout tabs;


    public void setupTest(){
        //although this violates MVP... it's going to make it easy to test stuff
        ArrayList<GameInfo> gameInfos = new ArrayList<>();
        //name, players, number of players
        ArrayList<Player> players = new ArrayList<>();

        GameInfo gameInfo = GameInfo.makeRandomGameInfo();
        gameInfos.add(gameInfo);
        ClientModel.getInstance().setGameList(gameInfos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setupTest();

        super.onCreate(savedInstanceState);
    }
}

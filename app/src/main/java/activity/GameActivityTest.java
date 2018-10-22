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

public class GameActivityTest extends AppCompatActivity {

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
        setContentView(R.layout.activity_game);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabLayout);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new PlayerInfoFragment();
                case 1: return new GameInfoFragment();
                case 2: return new DecksFragment();
                case 3: return new ChatFragment();
                default: return null;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Player Info";
                case 1: return "Game Info";
                case 2: return "Decks";
                case 3: return "Chat";
                default: return null;
            }
        }
    }
}

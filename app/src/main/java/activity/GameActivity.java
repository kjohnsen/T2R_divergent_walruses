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

import fragment.ChatFragment;
import fragment.DecksFragment;
import fragment.GameInfoFragment;
import fragment.MapFragment;
import fragment.PlayerInfoFragment;

public class GameActivity extends AppCompatActivity {

    ViewPager pager;
    MyPagerAdapter adapter;
    TabLayout tabs;
    Fragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mapFragment = new MapFragment();
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.mapContainer, mapFragment)
                .commit();
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabLayout);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onBackPressed() {}

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
                case 0: return new DecksFragment();
                case 1: return new GameInfoFragment();
                case 2: return new PlayerInfoFragment();
                case 3: return new ChatFragment();
                default: return null;
            }
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Decks";
                case 1: return "Game Info";
                case 2: return "Player Info";
                case 3: return "Chat";
                default: return null;
            }
        }
    }
}

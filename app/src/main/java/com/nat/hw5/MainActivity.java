package com.nat.hw5;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsFragmentPagerAdapter fragmentPagerAdapter;

    private  NewsPageFragment newsLastFragment;
    private static NewsPageFragment newsFavouritesFragment;

    private ArrayList<RecyclerViewItem> newsList = new ArrayList<RecyclerViewItem>();
    private ArrayList<RecyclerViewItem> favouritesNewsList = new ArrayList<RecyclerViewItem>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentPagerAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());

        newsLastFragment = NewsPageFragment.newInstance(NewsPageFragment.LAST, false);
        newsFavouritesFragment = NewsPageFragment.newInstance(NewsPageFragment.FAV, true);
        fragmentPagerAdapter.addFragment("", newsLastFragment);
        fragmentPagerAdapter.addFragment("", newsFavouritesFragment);

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_access_time);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);

    }
}


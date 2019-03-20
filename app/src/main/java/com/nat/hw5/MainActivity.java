package com.nat.hw5;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NewsFragmentPagerAdapter fragmentPagerAdapter;
    protected static NewsViewModel newsViewModel;


    private  NewsPageFragment newsLastFragment;
    private static NewsPageFragment newsFavouritesFragment;

    private ArrayList<RecyclerViewItem> newsList = new ArrayList<RecyclerViewItem>();
    private ArrayList<RecyclerViewItem> favouritesNewsList = new ArrayList<RecyclerViewItem>();


    private Observer<List<NewsItem>> observer = new Observer<List<NewsItem>>() {
        @Override
        public void onChanged(List<NewsItem> newsItems) {
            newsList = Utils.prepareData(newsItems);
            newsLastFragment.getAdapter().refreshData(newsList);
        }
    };

    private Observer<List<NewsItem>> favouriteObserver = new Observer<List<NewsItem>>() {
        @Override
        public void onChanged(@Nullable List<NewsItem> newsItems) {
            favouritesNewsList = Utils.prepareData(newsItems);
            newsFavouritesFragment.getAdapter().refreshData(favouritesNewsList);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentPagerAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager());

        newsLastFragment = NewsPageFragment.newInstance(newsList, false);
        newsFavouritesFragment = NewsPageFragment.newInstance(favouritesNewsList, true);
        fragmentPagerAdapter.addFragment("", newsLastFragment);
        fragmentPagerAdapter.addFragment("", newsFavouritesFragment);

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_access_time);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_star);

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        newsViewModel.getAllNews().observe(this, observer);
        newsViewModel.getAllFavouritesNews().observe(this, favouriteObserver);

    }
}


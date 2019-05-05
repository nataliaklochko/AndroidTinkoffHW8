package com.nat.hw8.ui;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;

    private static NewsPageFragment newsLastFragment;
    private static NewsPageFragment newsFavouritesFragment;

    private ArrayList<String> tabTitles = new ArrayList<String>();
    private ArrayList<NewsPageFragment> newsFragments = new ArrayList<NewsPageFragment>();

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        newsLastFragment = NewsPageFragment.newInstance(true, false);
        newsFavouritesFragment = NewsPageFragment.newInstance(false, true);
        addFragment("", newsLastFragment);
        addFragment("", newsFavouritesFragment);
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        return newsFragments.get(position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    private void addFragment(String title, NewsPageFragment fragment) {
        tabTitles.add(title);
        newsFragments.add(fragment);
    }

}
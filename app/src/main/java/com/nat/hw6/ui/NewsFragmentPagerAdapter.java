package com.nat.hw6.ui;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private ArrayList<String> tabTitles = new ArrayList<String>();
    private ArrayList<NewsPageFragment> news = new ArrayList<NewsPageFragment>();

    public NewsFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        return news.get(position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return System.currentTimeMillis();
    }

    public void addFragment(String title, NewsPageFragment fragment) {
        tabTitles.add(title);
        news.add(fragment);
    }

}
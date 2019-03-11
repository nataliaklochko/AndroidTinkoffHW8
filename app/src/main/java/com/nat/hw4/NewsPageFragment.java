package com.nat.hw4;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class NewsPageFragment extends Fragment {
    public static final String NEWS_ARG = "ITEMS_ARG";

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<ListItem> news;

    public static NewsPageFragment newInstance(ArrayList<ListItem> listItems) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(NEWS_ARG, listItems);
        NewsPageFragment fragment = new NewsPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NewsAdapter getAdapter() {
        return this.newsAdapter;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = getArguments().getParcelableArrayList(NEWS_ARG);
        }
        setRetainInstance(true);
    }


    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.addItemDecoration(
                new NewsItemDecoration(getResources().getDrawable(R.drawable.divider), 32, 48)
        );

        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getContext(), news);
        recyclerView.setAdapter(newsAdapter);
        return view;
    }

}

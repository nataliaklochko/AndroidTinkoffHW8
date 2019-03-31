package com.nat.hw5.recycler_view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nat.hw5.R;

import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    final LinearLayout newsItem;
    final TextView textTitle;
    final TextView textDesc;

    NewsViewHolder(View newsView) {
        super(newsView);
        newsItem = (LinearLayout) newsView.findViewById(R.id.news_item);
        textTitle = newsView.findViewById(R.id.title);
        textDesc = newsView.findViewById(R.id.description);
    }


}
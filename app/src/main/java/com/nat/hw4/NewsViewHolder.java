package com.nat.hw4;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    final LinearLayout newsItem;
    final TextView textTitle;
    final TextView textDesc;

    NewsViewHolder(View newsView) {
        super(newsView);
        newsItem = (LinearLayout) newsView.findViewById(R.id.news);
        textTitle = newsView.findViewById(R.id.title);
        textDesc = newsView.findViewById(R.id.description);
    }


}
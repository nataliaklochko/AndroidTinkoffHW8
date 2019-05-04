package com.nat.hw8.ui.recycler_view;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nat.hw8.R;

import androidx.recyclerview.widget.RecyclerView;


public class DateViewHolder extends RecyclerView.ViewHolder {

    final LinearLayout dateItem;
    final TextView textDate;

    DateViewHolder(View dateView) {
        super(dateView);
        dateItem = (LinearLayout) dateView.findViewById(R.id.date_linear_layout);
        textDate = dateView.findViewById(R.id.date_text);
    }

}
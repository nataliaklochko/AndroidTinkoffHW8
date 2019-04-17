package com.nat.hw6.ui.recycler_view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;


public class NewsItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;
    private int dividerMargin;
    private int itemOffset;

    public NewsItemDecoration(Drawable divider, int dividerMargin, int itemOffset) {
        this.divider = divider;
        this.dividerMargin = dividerMargin;
        this.itemOffset = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

            outRect.top = itemOffset;
            outRect.bottom = itemOffset;
            outRect.right = itemOffset;
            outRect.left = itemOffset;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = dividerMargin;
        int right = parent.getWidth() - dividerMargin;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount ; i++) {

            if (i != childCount - 1) {

                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin + itemOffset;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

}
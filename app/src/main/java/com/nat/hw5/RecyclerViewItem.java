package com.nat.hw5;

import android.os.Parcelable;

public abstract class RecyclerViewItem implements Parcelable {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_NEWS = 1;

    public abstract int getType();
}

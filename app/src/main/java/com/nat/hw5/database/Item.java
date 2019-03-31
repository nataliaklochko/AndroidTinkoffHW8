package com.nat.hw5.database;

import android.os.Parcelable;

public abstract class Item implements Parcelable {

    public static final int TYPE_DATE = 0;
    public static final int TYPE_NEWS = 1;

    public abstract int getType();
}

package com.nat.hw4;

import android.os.Parcelable;

public abstract class ListItem implements Parcelable {

        public static final int TYPE_DATE = 0;
        public static final int TYPE_NEWS = 1;

        abstract public int getType();
}

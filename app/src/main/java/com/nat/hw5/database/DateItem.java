package com.nat.hw5.database;


import android.os.Parcel;
import android.os.Parcelable;

public class DateItem extends Item {

    private String date;

    public DateItem(String date) {
        this.date = date;
    }

    public DateItem(Parcel in) {
        String[] data = new String[1];
        in.readStringArray(data);
        this.date = data[0];
    }

    public String getDate() {
        return date;
    }

    public int getType() {
        return TYPE_DATE;
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { date });
    }

    public static final Parcelable.Creator<DateItem> CREATOR = new Parcelable.Creator<DateItem>() {

        @Override
        public DateItem createFromParcel(Parcel source) {
            return new DateItem(source);
        }

        @Override
        public DateItem[] newArray(int size) {
            return new DateItem[size];
        }
    };
}

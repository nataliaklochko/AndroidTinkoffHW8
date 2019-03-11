package com.nat.hw4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItem extends ListItem implements Parcelable {

    private String title;
    private String desc;
    private String date;
    private String content;
    private String last;

    public NewsItem(String title, String desc, String date, String content, String last) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.content = content;
        this.last = last;
    }

    public NewsItem(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.title = data[0];
        this.desc = data[1];
        this.date = data[2];
        this.content = data[3];
        this.last = data[4];
    }

    public int getType() {
        return TYPE_NEWS;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getDate() {
        return this.date;
    }

    public String getContent() {
        return this.content;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        NewsItem news = (NewsItem) obj;
        return (
            this.title.equals(news.title) &&
            this.content.equals(news.content) &&
            this.date.equals(news.date) &&
            this.desc.equals(news.desc)
        );
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s: %s(%s)", title, desc, date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { title, desc, date, content, last });
    }

    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {

        @Override
        public NewsItem createFromParcel(Parcel source) {
            return new NewsItem(source);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}

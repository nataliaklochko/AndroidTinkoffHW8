package com.nat.hw8.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
     tableName = "news_table",
     indices = { @Index("id") }
)
public class NewsItem extends Item {

    @PrimaryKey
    @NonNull
    private String id;

    private String description;
    private String content;
    private String date;

    public NewsItem(String id, String description, String content, String date) {
        this.id = id;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NewsItem(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.id = data[0];
        this.description = data[1];
        this.date = data[2];
        this.content = data[3];
    }


    @Override
    public int getType() {
        return TYPE_NEWS;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        NewsItem news = (NewsItem) obj;
        return this.id.equals(news.id);
    }


    @NonNull
    @Override
    public String toString() {
        return String.format("№%d: %s(%s)", id, description, date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{ id, description, date, content });
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

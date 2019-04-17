package com.nat.hw6.database;


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

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String content;
    private String date;

    public NewsItem(String title, String description, String content, String date) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NewsItem(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.id = Integer.valueOf(data[0]);
        this.title = data[1];
        this.description = data[2];
        this.date = data[3];
        this.content = data[4];
    }


    @Override
    public int getType() {
        return TYPE_NEWS;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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



    @Override
    public boolean equals(@Nullable Object obj) {
        NewsItem news = (NewsItem) obj;
        return (
                this.title.equals(news.title) &&
                this.content.equals(news.content) &&
                this.date.equals(news.date) &&
                this.description.equals(news.description)
        );
    }


    @NonNull
    @Override
    public String toString() {
        return String.format("№%d %s: %s(%s)", id, title, description, date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { String.valueOf(id), title, description, date, content });
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

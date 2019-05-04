package com.nat.hw8.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(
    tableName = "favourites_news_table",
    indices = { @Index("news_id") }
)
public class FavouritesNews extends Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "news_id")
    private String newsId;

    public FavouritesNews(String newsId) {
        this.newsId = newsId;
    }


    public FavouritesNews(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        this.id = Integer.valueOf(data[0]);
        this.newsId = data[1];
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }

    public int getId() {
        return id;
    }

    public String getNewsId() {
        return newsId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { String.valueOf(id), newsId });
    }

    public static final Parcelable.Creator<FavouritesNews> CREATOR = new Parcelable.Creator<FavouritesNews>() {

        @Override
        public FavouritesNews createFromParcel(Parcel source) {
            return new FavouritesNews(source);
        }

        @Override
        public FavouritesNews[] newArray(int size) {
            return new FavouritesNews[size];
        }
    };
}

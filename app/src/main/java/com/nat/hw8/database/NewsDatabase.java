package com.nat.hw8.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = { NewsItem.class, FavouritesNews.class }, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase instance;
    private static final String DB_NAME = "news_database.db";
    public abstract NewsDao newsDao();

    public abstract FavouritesNewsDao favouritesNewsDao();


    public static NewsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NewsDatabase.class) {
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NewsDatabase.class,
                        DB_NAME
                )
                        .build();
            }
        }
        return instance;
    }

}

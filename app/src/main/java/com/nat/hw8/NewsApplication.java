package com.nat.hw8;

import android.app.Application;

import com.nat.hw8.database.NewsDatabase;

public class NewsApplication extends Application {

    private NewsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = NewsDatabase.getInstance(this);
    }

    public NewsDatabase getNewsDatabase() {
        return database;
    }

}

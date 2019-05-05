package com.nat.hw8.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface NewsDao {

    @Insert(onConflict = REPLACE)
    void insert(NewsItem newsItem);

    @Query("DELETE FROM news_table WHERE id=:idToDelete")
    void delete(String idToDelete);

    @Query("SELECT * FROM news_table WHERE id=:idToSelect")
    Maybe<NewsItem> select(String idToSelect);

    @Query("SELECT * FROM news_table ORDER BY date DESC")
    Flowable<List<NewsItem>> getAllNews();

    @Query("SELECT * FROM news_table WHERE id IN (SELECT news_id FROM favourites_news_table)")
    Flowable<List<NewsItem>> getAllFavouritesNews();

}

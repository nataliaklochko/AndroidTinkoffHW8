package com.nat.hw5;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface NewsDao {

    @Insert(onConflict = REPLACE)
    void insert(NewsItem newsItem);

    @Delete
    void delete(NewsItem newsItem);

    @Query("DELETE FROM news_table WHERE id=:idToDelete")
    void delete(int idToDelete);

    @Query("SELECT * FROM news_table ORDER BY date DESC")
    LiveData<List<NewsItem>> getAllNews();

    @Query("SELECT * FROM news_table WHERE id IN (SELECT news_id FROM favourites_news_table)")
    LiveData<List<NewsItem>> getAllFavouritesNews();

}

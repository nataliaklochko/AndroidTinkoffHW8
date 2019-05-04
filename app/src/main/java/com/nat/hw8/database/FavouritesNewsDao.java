package com.nat.hw8.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Maybe;


import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavouritesNewsDao {

    @Insert(onConflict = REPLACE)
    void insert(FavouritesNews news);

    @Delete
    void delete(FavouritesNews news);

    @Query("DELETE FROM favourites_news_table WHERE news_id=:newsIdToDelete")
    void delete(String newsIdToDelete);

    @Query("SELECT * FROM favourites_news_table WHERE news_id=:idToSelect")
    Maybe<FavouritesNews> select(String idToSelect);

}

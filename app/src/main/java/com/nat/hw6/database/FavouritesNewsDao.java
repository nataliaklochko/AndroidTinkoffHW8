package com.nat.hw6.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Maybe;
import io.reactivex.Single;


import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavouritesNewsDao {

    @Insert(onConflict = REPLACE)
    void insert(FavouritesNews news);

    @Delete
    void delete(FavouritesNews news);

    @Query("DELETE FROM favourites_news_table WHERE news_id=:newsIdToDelete")
    void delete(int newsIdToDelete);

    @Query("SELECT * FROM favourites_news_table WHERE news_id=:idToSelect")
    Maybe<FavouritesNews> select(int idToSelect);

}

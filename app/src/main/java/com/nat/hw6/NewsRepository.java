package com.nat.hw6;

import android.app.Application;

import com.nat.hw6.database.FavouritesNews;
import com.nat.hw6.database.FavouritesNewsDao;
import com.nat.hw6.database.NewsDao;
import com.nat.hw6.database.NewsDatabase;
import com.nat.hw6.database.NewsItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;


public class NewsRepository {

    private NewsDao newsDao;
    private FavouritesNewsDao favouritesNewsDao;

    public NewsRepository(Application application) {
        NewsDatabase database = NewsDatabase.getInstance(application);
        newsDao = database.newsDao();
        favouritesNewsDao = database.favouritesNewsDao();
    }

    public Flowable<List<NewsItem>> getAllNews() {
        return newsDao.getAllNews();
    }

    public Flowable<List<NewsItem>> getAllFavouritesNews() {
        return newsDao.getAllFavouritesNews();
    }

    public void insertFavourite(FavouritesNews favouritesNews) {
        favouritesNewsDao.insert(favouritesNews);
    }

    public void deleteFavourite(int newsIdToDelete) {
        favouritesNewsDao.delete(newsIdToDelete);
    }

    public Maybe<FavouritesNews> getFavouriteNews(int idToSelect) {
        return favouritesNewsDao.select(idToSelect);
    }

}

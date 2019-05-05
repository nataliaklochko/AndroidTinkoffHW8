package com.nat.hw8.database;

import com.nat.hw8.NewsApplication;

import android.app.Application;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.Maybe;


public class NewsRepository {

    private NewsDao newsDao;
    private FavouritesNewsDao favouritesNewsDao;

    public NewsRepository(Application application) {
        NewsDatabase database = ((NewsApplication) application).getNewsDatabase();
        newsDao = database.newsDao();
        favouritesNewsDao = database.favouritesNewsDao();
    }

    public Flowable<List<NewsItem>> getAllNews() {
        return newsDao.getAllNews();
    }

    public Flowable<List<NewsItem>> getAllFavouritesNews() {
        return newsDao.getAllFavouritesNews();
    }

    public void deleteNewsItem(String idToDelete) {
        newsDao.delete(idToDelete);
    }

    public void insertNewsItem(NewsItem newsItem) {
        newsDao.insert(newsItem);
    }

    public void insertFavourite(FavouritesNews favouritesNews) {
        favouritesNewsDao.insert(favouritesNews);
    }

    public void deleteFavourite(String newsIdToDelete) {
        favouritesNewsDao.delete(newsIdToDelete);
    }

    public Maybe<FavouritesNews> getFavouriteNews(String idToSelect) {
        return favouritesNewsDao.select(idToSelect);
    }

    public Maybe<NewsItem> getNewsItem(String idToSelect) {
        return newsDao.select(idToSelect);
    }

}

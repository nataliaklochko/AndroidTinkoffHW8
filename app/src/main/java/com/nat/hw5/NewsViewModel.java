package com.nat.hw5;

import android.app.Application;

import com.nat.hw5.database.FavouritesNews;
import com.nat.hw5.database.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private LiveData<List<NewsItem>> allNews;
    private LiveData<List<NewsItem>> allFavouritesNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);

        repository = new NewsRepository(application);
        allNews = repository.getAllNews();
        allFavouritesNews = repository.getAllFavouritesNews();

    }

    public void insertFavourites(FavouritesNews favouritesNews) {
        repository.insertFavourite(favouritesNews);
    }

    public FavouritesNews getFavouriteNews(int idToSelect) {
        return repository.getFavouriteNews(idToSelect);
    }

    public void deleteFavourite(int newsIdToDelete) {
        repository.deleteFavourite(newsIdToDelete);
    }

    public LiveData<List<NewsItem>> getAllNews() {
        return allNews;
    }

    public LiveData<List<NewsItem>> getAllFavouritesNews () {
        return allFavouritesNews;
    }

}

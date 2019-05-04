package com.nat.hw8;

import android.app.Application;

import com.nat.hw8.database.FavouritesNews;
import com.nat.hw8.database.Item;
import com.nat.hw8.database.NewsItem;
import com.nat.hw8.database.NewsRepository;
import com.nat.hw8.retrofit.NewsRetrofit;
import com.nat.hw8.retrofit.NewsService;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class NewsViewModel extends AndroidViewModel {
    private NewsRepository repository;
    private NewsService newsService;
    private LiveData<ArrayList<Item>> allNews;
    private LiveData<ArrayList<Item>> allFavouritesNews;
    private CompositeDisposable compositeDisposable;


    public NewsViewModel(@NonNull Application application) {
        super(application);

        compositeDisposable = new CompositeDisposable();
        newsService = NewsRetrofit.getInstance().getNewsService();
        repository = new NewsRepository(application);

        loadNews();

        allFavouritesNews = LiveDataReactiveStreams.fromPublisher(
                repository.getAllFavouritesNews()
                        .map(newsItemList -> Utils.prepareData(newsItemList))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );
    }


    public void loadNews() {
        if (Utils.isNetworkAvailable(getApplication().getApplicationContext())) {
            allNews = LiveDataReactiveStreams.fromPublisher(
                    newsService.getNews()
                            .flatMapIterable(payload -> payload.getPayload())
                            .map(itemList -> Utils.retrofitToRoomNewsModel(itemList))
                            .toList()
                            .map(newsItemList -> Utils.prepareData(newsItemList))
                            .toFlowable()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );
        } else {
            allNews = LiveDataReactiveStreams.fromPublisher(
                    repository.getAllNews()
                            .map(newsItemList -> Utils.prepareData(newsItemList))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );
        }

        allFavouritesNews = LiveDataReactiveStreams.fromPublisher(
                repository.getAllFavouritesNews()
                        .map(newsItemList -> Utils.prepareData(newsItemList))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );
    }

    public void insertFavourites(FavouritesNews favouritesNews) {
        compositeDisposable.add(
            Single.just(favouritesNews)
                .doOnSuccess(value -> repository.insertFavourite(value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void insertNewsItem(NewsItem newsItem) {
        compositeDisposable.add(
            Single.just(newsItem)
                .doOnSuccess(value -> repository.insertNewsItem(newsItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public LiveData<Boolean> isFavorite(String id) {
        return LiveDataReactiveStreams.fromPublisher(
                repository.getFavouriteNews(id)
                        .isEmpty()
                        .toFlowable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );
    }

    public LiveData<Boolean> isOld(String id) {
        return LiveDataReactiveStreams.fromPublisher(
                repository.getNewsItem(id)
                        .isEmpty()
                        .toFlowable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );
    }

    public LiveData<NewsItem> getOldNewsItem(NewsItem newsItem) {
        return LiveDataReactiveStreams.fromPublisher(
                repository.getNewsItem(newsItem.getId())
                        .toFlowable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
        );
    }

    public LiveData<NewsItem> getNewsItem(NewsItem newsItem) {
        return LiveDataReactiveStreams.fromPublisher(
                    newsService
                            .getNewsItem(newsItem.getId())
                            .map(payload -> Utils.updateNewsItemContent(newsItem, payload))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
            );
    }


    public void deleteFavourite(String newsIdToDelete) {
        compositeDisposable.add(
            Single.just(newsIdToDelete)
                .doOnSuccess(value -> repository.deleteFavourite(value))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public LiveData<ArrayList<Item>> getAllNews() {
        return allNews;
    }

    public LiveData<ArrayList<Item>> getAllFavouritesNews () {
        return allFavouritesNews;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}

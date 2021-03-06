package com.nat.hw8;

import android.app.Application;

import com.nat.hw8.database.FavouritesNews;
import com.nat.hw8.database.Item;
import com.nat.hw8.database.NewsItem;
import com.nat.hw8.database.NewsRepository;
import com.nat.hw8.retrofit.NewsRetrofit;
import com.nat.hw8.retrofit.NewsService;
import com.nat.hw8.retrofit.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class NewsViewModel extends AndroidViewModel {

    public final static int NEWS_SIZE = 100;

    private NewsRepository repository;
    private NewsService newsService;
    private LiveData<ArrayList<Item>> allNews;
    private LiveData<ArrayList<Item>> allFavouritesNews;
    private CompositeDisposable compositeDisposable;


    public NewsViewModel(@NonNull Application application) {
        super(application);

        compositeDisposable = new CompositeDisposable();
        newsService = NewsRetrofit.getInstance(application).getNewsService();
        repository = new NewsRepository(application);

        if (allNews == null) {
            loadNews();
            clearDbNews();
        }
    }

    public void loadNews() {
        if (Utils.isNetworkAvailable(getApplication().getApplicationContext())) {
            allNews = LiveDataReactiveStreams.fromPublisher(
                    newsService.getNews()
                            .flattenAsFlowable(ResponsePayload::getPayload)
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

    private void clearDbNews() {
        compositeDisposable.add(
                repository
                    .getAllNews()
                    .map(newsItemList -> adjustDbSize(newsItemList))
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        );
    }

    private Boolean adjustDbSize(List<NewsItem> newsItemList) {
        int newsListSize = newsItemList.size();
        if (newsListSize > NEWS_SIZE) {
            repository.deleteNewsItems(NEWS_SIZE);
            return true;
        }
        return false;
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
                            .toFlowable()
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

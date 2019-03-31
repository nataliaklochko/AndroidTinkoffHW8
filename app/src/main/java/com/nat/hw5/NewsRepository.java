package com.nat.hw5;

import android.app.Application;
import android.os.AsyncTask;

import com.nat.hw5.database.FavouritesNews;
import com.nat.hw5.database.FavouritesNewsDao;
import com.nat.hw5.database.NewsDao;
import com.nat.hw5.database.NewsDatabase;
import com.nat.hw5.database.NewsItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;



public class NewsRepository {

    private NewsDao newsDao;
    private FavouritesNewsDao favouritesNewsDao;

    private LiveData<List<NewsItem>> allNews;
    private LiveData<List<NewsItem>> allFavouritesNews;

    public NewsRepository(Application application) {
        NewsDatabase database = NewsDatabase.getInstance(application);

        newsDao = database.newsDao();
        favouritesNewsDao = database.favouritesNewsDao();

        try {
            allNews = new GetAllNewsAsyncTask(newsDao).execute().get();
            allFavouritesNews = new GetAllFavouritesNewsAsyncTask(newsDao).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }
    }

    public void insertFavourite(FavouritesNews favouritesNews) {
        new InsertFavouritesAsyncTask(favouritesNewsDao).execute(favouritesNews);
    }

    public void deleteFavourite(int newsIdToDelete) {
        new DeleteFavouritesAsyncTask(favouritesNewsDao).execute(newsIdToDelete);
    }

    public FavouritesNews getFavouriteNews(int idToSelect) {
        try {
            return new SelectFavouriteAsyncTask(favouritesNewsDao).execute(new Integer(idToSelect)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

    }

    public LiveData<List<NewsItem>> getAllNews() {
        return allNews;
    }

    public LiveData<List<NewsItem>> getAllFavouritesNews() {
        return allFavouritesNews;
    }



    private static class InsertFavouritesAsyncTask extends AsyncTask<FavouritesNews, Void, Void> {
        private FavouritesNewsDao favouritesNewsDao;

        private InsertFavouritesAsyncTask(FavouritesNewsDao favouritesNewsDao) {
            this.favouritesNewsDao = favouritesNewsDao;
        }

        @Override
        protected Void doInBackground(FavouritesNews... favouritesNews) {
            favouritesNewsDao.insert(favouritesNews[0]);
            return null;
        }
    }


    private static class SelectFavouriteAsyncTask extends AsyncTask<Integer, Void, FavouritesNews> {
        private FavouritesNewsDao favouritesNewsDao;

        private SelectFavouriteAsyncTask(FavouritesNewsDao favouritesNewsDao) {
            this.favouritesNewsDao = favouritesNewsDao;
        }

        @Override
        protected FavouritesNews doInBackground(Integer... position) {
            int idToSelect = position[0].intValue();
            FavouritesNews favouritesNews = favouritesNewsDao.select(idToSelect);
            return favouritesNews;
        }
    }

    private static class DeleteFavouritesAsyncTask extends AsyncTask<Integer, Void, Void> {
        private FavouritesNewsDao favouritesNewsDao;

        private DeleteFavouritesAsyncTask(FavouritesNewsDao favouritesNewsDao) {
            this.favouritesNewsDao = favouritesNewsDao;
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            favouritesNewsDao.delete(ints[0]);
            return null;
        }
    }


    private static class GetAllNewsAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {
        private NewsDao newsDao;

        private GetAllNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... voids) {
            LiveData<List<NewsItem>> news = newsDao.getAllNews();
            return news;
        }
    }


    private static class GetAllFavouritesNewsAsyncTask extends AsyncTask<Void, Void, LiveData<List<NewsItem>>> {
        private NewsDao newsDao;

        private GetAllFavouritesNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected LiveData<List<NewsItem>> doInBackground(Void... voids) {
            LiveData<List<NewsItem>> favouritesNews = newsDao.getAllFavouritesNews();
            return favouritesNews;
        }
    }
}

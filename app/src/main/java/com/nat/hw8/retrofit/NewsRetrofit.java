package com.nat.hw8.retrofit;

import android.content.Context;

import com.nat.hw8.R;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRetrofit {

    private static Retrofit retrofit;
    private static NewsRetrofit newsRetrofit;
    private NewsService newsService;

    private NewsRetrofit(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newsService = retrofit.create(NewsService.class);
    }


    public static NewsRetrofit getInstance(Context context) {
        if (newsRetrofit == null) {
            newsRetrofit = new NewsRetrofit(context);
        }
        return newsRetrofit;
    }

    public NewsService getNewsService() {
        return newsService;
    }


}

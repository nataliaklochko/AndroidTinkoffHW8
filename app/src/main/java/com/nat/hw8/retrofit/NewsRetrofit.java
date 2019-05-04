package com.nat.hw8.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRetrofit {

    private static Retrofit retrofit;
    private static NewsRetrofit newsRetrofit;
    private NewsService newsService;

    private NewsRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tinkoff.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        newsService = retrofit.create(NewsService.class);
    }


    public static NewsRetrofit getInstance() {
        if (newsRetrofit == null) {
            newsRetrofit = new NewsRetrofit();
        }
        return newsRetrofit;
    }

    public NewsService getNewsService() {
        return newsService;
    }


}

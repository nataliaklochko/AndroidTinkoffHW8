package com.nat.hw8.retrofit;


import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface NewsService {

    @GET("/v1/news")
    Single<ResponsePayload<List<NewsListRetrofit>>> getNews();

    @GET("/v1/news_content")
    Single<ResponsePayload<NewsItemRetrofit>> getNewsItem(@Query("id") String id);

}

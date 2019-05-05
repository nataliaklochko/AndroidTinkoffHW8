package com.nat.hw8.retrofit;

import com.google.gson.annotations.SerializedName;

public class NewsItemRetrofit {

    @SerializedName("content")
    private String content;

    public String getContent() {
        return this.content;
    }
}

package com.nat.hw8.retrofit;


import com.google.gson.annotations.SerializedName;

public class NewsListRetrofit {

    @SerializedName("id")
    private String id;

    @SerializedName("text")
    private String text;

    @SerializedName("publicationDate")
    private PublicationDate publicationDate;

    public String getId(){
        return this.id;
    }

    public String getText(){
        return this.text;
    }

    public PublicationDate getPublicationMsDate(){
        return this.publicationDate;
    }

}

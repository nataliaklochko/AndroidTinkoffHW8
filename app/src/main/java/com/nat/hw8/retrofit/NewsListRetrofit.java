package com.nat.hw8.retrofit;


public class NewsListRetrofit {

    private String id;

    private String text;

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

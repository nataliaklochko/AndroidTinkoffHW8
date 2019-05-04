package com.nat.hw8.retrofit;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponsePayload<T> implements Serializable {

    @SerializedName("payload")
    private T payload;

    public T getPayload() {
        return this.payload;
    }

}

package com.ebs.hydrokleen.networkutils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String BASE_URL = "https://hydrokleen.com.bd/v1/api/" ;
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitAPI getRetrofitApi(){
        return retrofit.create(RetrofitAPI.class);
    }

}

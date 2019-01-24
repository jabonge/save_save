package com.example.myapplication.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

   public static Retrofit getClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.78.213.146:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}

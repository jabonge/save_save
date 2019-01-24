package com.example.myapplication.Remote;

import com.example.myapplication.Model.EletcModel;
import com.example.myapplication.Model.MainModel;
import com.example.myapplication.Model.WaterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/main")
    Call<MainModel> getmaindata();

    @GET("/api/electricity?")
    Call<EletcModel> getelectricdata(@Query("searchDate") String searchDate);
    @GET("/api/water?")
    Call<WaterModel> getwaterdata(@Query("searchDate") String searchDate);

    @FormUrlEncoded
    @POST("/api/electricity")
    Call<ResponseBody> electgoal(@Field("goal") Integer goal);

    @FormUrlEncoded
    @POST("/api/water")
    Call<ResponseBody> watergoal(@Field("goal") Integer goal);

    @DELETE("/api/usage")
    Call<ResponseBody> reset();

}

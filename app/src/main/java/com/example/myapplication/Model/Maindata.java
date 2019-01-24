package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Maindata {

    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("waterDday")
    @Expose
    public Integer waterDday;
    @SerializedName("electDday")
    @Expose
    public Integer electDday;
    @SerializedName("electGoalPrice")
    @Expose
    public Integer electGoalPrice;
    @SerializedName("waterGoalPrice")
    @Expose
    public Integer waterGoalPrice;
    @SerializedName("waterPrice")
    @Expose
    public Integer waterPrice;
    @SerializedName("electPrice")
    @Expose
    public Integer electPrice;
    @SerializedName("savePrice")
    @Expose
    public Integer savePrice;

}
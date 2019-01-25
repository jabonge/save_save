package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WaterData {

    @SerializedName("waterDday")
    @Expose
    public Integer waterDday;
    @SerializedName("monthUsage")
    @Expose
    public Integer monthUsage;
    @SerializedName("monthUsagePrice")
    @Expose
    public Integer monthUsagePrice;
    @SerializedName("saveAmount")
    @Expose
    public Integer saveAmount;
    @SerializedName("savePrice")
    @Expose
    public Integer savePrice;
    @SerializedName("predictionAmount")
    @Expose
    public Integer predictionAmount;
    @SerializedName("predictionPrice")
    @Expose
    public Integer predictionPrice;
    @SerializedName("waterGoal")
    @Expose
    public Integer waterGoal;
    @SerializedName("waterGoalPrice")
    @Expose
    public Integer waterGoalPrice;
    @SerializedName("stateGoal")
    @Expose
    public Integer stateGoal;

}
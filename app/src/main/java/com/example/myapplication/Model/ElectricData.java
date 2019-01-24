package com.example.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ElectricData  {

    @SerializedName("electDday")
    @Expose
    public Integer electDday;
    @SerializedName("monthUsage")
    @Expose
    public Integer monthUsage;
    @SerializedName("monthUsagePrice")
    @Expose
    public Integer monthUsagePrice;
    @SerializedName("stepElectricity")
    @Expose
    public Integer stepElectricity;
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
    @SerializedName("electGoal")
    @Expose
    public Integer electGoal;
    @SerializedName("electGoalPrice")
    @Expose
    public Integer electGoalPrice;
    @SerializedName("statusGoal")
    @Expose
    public Integer statusGoal;

}

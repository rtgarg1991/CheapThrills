package com.nyu.prashant.cheapthrills.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("deal")
    @Expose
    private MainDeal deal;

    public MainDeal getDeal() {
        return deal;
    }

    public void setDeal(MainDeal deal) {
        this.deal = deal;
    }

}
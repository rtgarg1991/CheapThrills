
package com.nyu.prashant.cheapthrills.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("deal")
    @Expose
    private Deal_ deal;

    public Deal_ getDeal() {
        return deal;
    }

    public void setDeal(Deal_ deal) {
        this.deal = deal;
    }

}

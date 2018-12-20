
package com.nyu.prashant.cheapthrills.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponseModel {

    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals = null;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

}

package com.nyu.prashant.cheapthrills.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedDeal {
    @SerializedName("user_id")
    @Expose
    String userId;
    @SerializedName("category_slug")
    @Expose
    String categorySlug;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategorySlug() {
        return categorySlug;
    }

    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }
}


package com.nyu.prashant.cheapthrills.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("query")
    @Expose
    private Object query;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("radius")
    @Expose
    private Integer radius;
    @SerializedName("online")
    @Expose
    private Object online;
    @SerializedName("category_slugs")
    @Expose
    private Object categorySlugs;
    @SerializedName("provider_slugs")
    @Expose
    private Object providerSlugs;
    @SerializedName("updated_after")
    @Expose
    private Object updatedAfter;
    @SerializedName("order")
    @Expose
    private Object order;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Object getOnline() {
        return online;
    }

    public void setOnline(Object online) {
        this.online = online;
    }

    public Object getCategorySlugs() {
        return categorySlugs;
    }

    public void setCategorySlugs(Object categorySlugs) {
        this.categorySlugs = categorySlugs;
    }

    public Object getProviderSlugs() {
        return providerSlugs;
    }

    public void setProviderSlugs(Object providerSlugs) {
        this.providerSlugs = providerSlugs;
    }

    public Object getUpdatedAfter() {
        return updatedAfter;
    }

    public void setUpdatedAfter(Object updatedAfter) {
        this.updatedAfter = updatedAfter;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

}

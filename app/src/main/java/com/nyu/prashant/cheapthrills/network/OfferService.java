package com.nyu.prashant.cheapthrills.network;

import com.nyu.prashant.cheapthrills.model.Deal;
import com.nyu.prashant.cheapthrills.model.DealList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rohit on 20/12/18.
 */

public interface OfferService {
    @GET("v2/deals")
    Call<DealList> getDeals(@Query(value = "api_key") String apiKey,
                            @Query(value = "location") String location,
                            @Query(value = "radius") int radius,
                            @Query(value = "categories") String categories);

    @GET("v2/deals/{dealId}")
    Call<Deal> getDeal(@Path(value = "dealId") Long dealId, @Query(value = "api_key") String apiKey);
}

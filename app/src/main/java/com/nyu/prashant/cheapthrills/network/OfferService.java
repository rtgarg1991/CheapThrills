package com.nyu.prashant.cheapthrills.network;

import com.nyu.prashant.cheapthrills.model.Deal;
import com.nyu.prashant.cheapthrills.model.DealList;
import com.nyu.prashant.cheapthrills.model.SelectedDeal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rohit on 20/12/18.
 */

public interface OfferService {
    @GET("deal/search")
    Call<DealList> getDeals(@Query(value = "latitude") Double latitude, @Query(value = "longitude") Double longitude);

    @GET("deal/{dealId}")
    Call<Deal> getDeal(@Path(value = "dealId") Long dealId);

    @POST("dynamo")
    Call<ResponseBody> postDeal(@Body SelectedDeal selectedDeal);
}

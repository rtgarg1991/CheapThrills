package com.nyu.prashant.cheapthrills;

import android.app.Application;

import com.google.gson.Gson;
import com.nyu.prashant.cheapthrills.network.OfferService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohit on 20/12/18.
 */

public class MainApplication extends Application {
    private static MainApplication instance;
    private OfferService service;
    private Gson gson;


    public static final String API_KEY = "IBWXKxPd";

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.discountapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OfferService.class);
    }

    public OfferService getService() {
        return service;
    }

}

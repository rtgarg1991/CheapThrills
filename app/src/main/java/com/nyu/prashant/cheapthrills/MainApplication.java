package com.nyu.prashant.cheapthrills;

import android.app.Application;

import com.google.gson.Gson;
import com.nyu.prashant.cheapthrills.network.OfferService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohit on 20/12/18.
 */

public class MainApplication extends Application {
    private static MainApplication instance;
    private OfferService service;
    private Gson gson;


    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        gson = new Gson();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://u0i1l6urvh.execute-api.us-east-1.amazonaws.com/Deal/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OfferService.class);
    }

    public OfferService getService() {
        return service;
    }

}

package com.nyu.prashant.cheapthrills;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;
import com.google.gson.Gson;
import com.nyu.prashant.cheapthrills.model.DealInterface;
import com.nyu.prashant.cheapthrills.network.OfferService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.ContactsContract.CommonDataKinds.StructuredPostal.REGION;

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

    /** This is the entry point of the application*/
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

    public void callAPIGateway(String params) {

        CognitoCachingCredentialsProvider credentialsProvider =
                new CognitoCachingCredentialsProvider(
                        MainApplication.this.getApplicationContext(),
                        CognitoSettings.IDENTITY_POOL_ID,
                        CognitoSettings.COGNITO_REGION);

        // Create a LambdaInvokerFactory, to be used to instantiate the Lambda proxy
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                                        MainApplication.this.getApplicationContext(),
                                        CognitoSettings.COGNITO_REGION,
                                        credentialsProvider);

        final DealInterface myInterface = factory.build(DealInterface.class);

        // The Lambda function invocation results in a network call
        // Make sure it is not called from the main thread
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.getDeals(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("New Async Call", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    return;
                }
                Log.d("New_Call", result);
            }
        }.execute(params);
    }

    public OfferService getService() {
        return service;
    }
}

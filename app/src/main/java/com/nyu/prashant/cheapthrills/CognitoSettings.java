package com.nyu.prashant.cheapthrills;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    private static final String USER_POOL_ID = "us-east-1_gUVEEBG4f";
    private static final String CLIENT_ID = "104u7nob7tocshj5ihhqt685rt";
    private static final String CLIENT_SECRET = "82kvq994q6geqe5g05b29qjh1fiufl5g5ljiq6pm1g32v897md";
    private static final String IDENTITY_POOL_ID = "eu-west-1:74e4c7ad-5f4d-4a94-a02d-703978da6ddd";
    private static final Regions COGNITO_REGION =  Regions.US_EAST_1;
    private Context context;

    public CognitoSettings(Context context) {
        this.context = context;
    }

    public String getUserPoolId() {
        return USER_POOL_ID;
    }

    public String getClientId() {
        return CLIENT_ID;
    }

    public String getClientSecret() {
        return CLIENT_SECRET;
    }

    public Regions getCognitoRegion() {
        return COGNITO_REGION;
    }

    /*the entry point for all interactions with your user pool from your application*/
    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, USER_POOL_ID, CLIENT_ID, CLIENT_SECRET, COGNITO_REGION);
    }

    public CognitoCachingCredentialsProvider getCredentialsProvider() {
        return new CognitoCachingCredentialsProvider(
                context.getApplicationContext(),
                IDENTITY_POOL_ID, // Identity pool ID
                COGNITO_REGION// Region;
        );
    }

}

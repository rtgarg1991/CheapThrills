package com.nyu.prashant.cheapthrills;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    public static final String USER_POOL_ID = "";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String IDENTITY_POOL_ID = "";
    public static final Regions COGNITO_REGION =  Regions.US_EAST_1;
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

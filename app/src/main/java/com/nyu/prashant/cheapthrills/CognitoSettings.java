package com.nyu.prashant.cheapthrills;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    private static final String userPoolId = "";
    private static final String clientId = "";
    private static final String clientSecret = "";
    private static final Regions cognitoRegion =  Regions.US_EAST_1;

    private String identityPoolId = "";

    private Context context;


    public CognitoSettings(Context context) {
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }

    /*the entry point for all interactions with your user pool from your application*/
    public CognitoUserPool getUserPool() {
        return new CognitoUserPool(context, userPoolId, clientId
                , clientSecret, cognitoRegion);
    }

    public CognitoCachingCredentialsProvider getCredentialsProvider() {
        return new CognitoCachingCredentialsProvider(
                context.getApplicationContext(),
                identityPoolId, // Identity pool ID
                cognitoRegion// Region;
        );
    }

}

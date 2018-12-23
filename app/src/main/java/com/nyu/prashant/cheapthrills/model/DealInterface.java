package com.nyu.prashant.cheapthrills.model;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface DealInterface {

        /**
         * Invoke lambda function "getDeals". The function name is the method name
         */
        @LambdaFunction
        String getDeals(String params);

        /**
         * Invoke lambda function "echo". The functionName in the annotation
         * overrides the default which is the method name
         */
        @LambdaFunction(functionName = "echo")
        void noEcho();
}
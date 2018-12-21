package com.nyu.prashant.cheapthrills.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.nyu.prashant.cheapthrills.CognitoSettings;
import com.nyu.prashant.cheapthrills.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editTextUsername = findViewById(R.id.editTextUsername);
        final EditText editTextPassword = findViewById(R.id.editTextPassword);

        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {

                Intent dealsIntent = new Intent(LoginActivity.this, DealsActivity.class);
                LoginActivity.this.startActivity(dealsIntent);
                log();
            }

            private void log() {
                Log.i(TAG, "Login successfull, can get tokens here!");
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation
                    , String userId) {

                Log.i(TAG, "in getAuthenticationDetails()....");

                /*need to get the userId & password to continue*/
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId
                        , String.valueOf(editTextPassword.getText()), null);

                // Pass the user sign-in credentials to the continuation
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                // Allow the sign-in to continue
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

                Log.i(TAG, "in getMFACode()....");

                 /** if Multi-factor authentication is required; get the verification code from
                  * user multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
                  * Allow the sign-in process to continue
                  * multiFactorAuthenticationContinuation.continueTask();*/
            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {
                Log.i(TAG, "in authenticationChallenge()....");
            }

            @Override
            public void onFailure(Exception exception) {

                String localizedMessage = exception.getLocalizedMessage();
                final StringBuilder msg = new StringBuilder("Login failed");
                Log.i(TAG, msg.toString());
                if (localizedMessage.contains("User does not exist")) {
                    msg.append(": User does not exist");
                } else if (localizedMessage.contains("Incorrect username or password")) {
                    msg.append(": Incorrect username or password");
                }
                Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        };

        initButtons(editTextUsername, authenticationHandler);
    }

    private void initButtons(final EditText editTextUsername, final AuthenticationHandler authenticationHandler) {

        initSignIn(editTextUsername, authenticationHandler);
        initSignUp();
    }

    private void initSignIn(final EditText editTextUsername, final AuthenticationHandler authenticationHandler) {

        final Button signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CognitoSettings cognitoSettings = new CognitoSettings(LoginActivity.this);

                CognitoUser thisUser = cognitoSettings
                        .getUserPool()
                        .getUser(String.valueOf(editTextUsername.getText()));
                // Sign in the user
                Log.i(TAG, "in button clicked....");
                thisUser.getSessionInBackground(authenticationHandler);
            }
        });
    }

    private void initSignUp() {

        final Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                // signUpIntent.putExtra("key", value);
                LoginActivity.this.startActivity(signUpIntent);
            }
        });
    }
}

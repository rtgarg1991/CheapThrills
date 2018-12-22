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

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.nyu.prashant.cheapthrills.CognitoSettings;
import com.nyu.prashant.cheapthrills.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "SignInSignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_register);

        registerUser();
    }

    private void registerUser() {

        final EditText inputName = findViewById(R.id.editText2);
        final EditText inputTelephone = findViewById(R.id.editText);
        final EditText inputEmail = findViewById(R.id.editText3);

        final EditText inputPassword = findViewById(R.id.editText4);
        final EditText inputUsername = findViewById(R.id.editText5);

        // Create a CognitoUserAttributes object and add user attributes
        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        final SignUpHandler signupCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user,
                                  boolean signUpConfirmationState,
                                  CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                // Sign-up was successful
                Log.i(TAG, "sign up success...is confirmed: " + signUpConfirmationState);
                // Check if this user (cognitoUser) needs to be confirmed
                if (signUpConfirmationState) {
                    // The user has already been confirmed
                    Log.i(TAG, "sign up success...confirmed");
                } else {
                    Log.i(TAG, "sign up success...not confirmed, verification code sent to: "
                            + cognitoUserCodeDeliveryDetails.getDestination());
                }
                final String msg = "Sign Up Successful";
                Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.GREEN)
                        .show();
                Intent signInIntent = new Intent(
                        RegisterActivity.this,
                        LoginActivity.class);
                RegisterActivity.this.startActivity(signInIntent);
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-up failed, check exception for the cause
                Log.i(TAG, "sign up failure: " + exception.getLocalizedMessage());
                final String msg = "Sign Up Failed";
                Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();
            }
        };

        Button buttonRegister = findViewById(R.id.signUpButton);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAttributes.addAttribute("given_name", String.valueOf(inputName.getText()));
                userAttributes.addAttribute("phone_number", String.valueOf(inputTelephone.getText()));
                userAttributes.addAttribute("email", String.valueOf(inputEmail.getText()));

                CognitoSettings cognitoSettings = new CognitoSettings(RegisterActivity.this);

                cognitoSettings.getUserPool().signUpInBackground(String.valueOf(inputUsername.getText())
                        , String.valueOf(inputPassword.getText()), userAttributes
                        , null, signupCallback);
            }
        });

        Button signInRedirect = findViewById(R.id.signInButton);
        signInRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = new Intent(
                        RegisterActivity.this,
                        LoginActivity.class);
                RegisterActivity.this.startActivity(signInIntent);
            }
        });
    }
}

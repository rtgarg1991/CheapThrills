package com.nyu.prashant.cheapthrills.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.nyu.prashant.cheapthrills.CognitoSettings;
import com.nyu.prashant.cheapthrills.MainApplication;
import com.nyu.prashant.cheapthrills.R;
import com.nyu.prashant.cheapthrills.model.Deal;
import com.nyu.prashant.cheapthrills.model.MainDeal;
import com.nyu.prashant.cheapthrills.model.SelectedDeal;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rohit on 20/12/18.
 */

public class DealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);

        if (getIntent() == null || !getIntent().hasExtra("deal_id") || getIntent().getLongExtra("deal_id", 0) == 0) {
            finish();
        }

        long dealId = getIntent().getLongExtra("deal_id", 0);

        final View progressLayout = findViewById(R.id.progress_layout);
        final View dealLayout = findViewById(R.id.deal_layout);

        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        final TextView loading = findViewById(R.id.loading);

        final ImageView imageView = findViewById(R.id.image);

        final TextView title = findViewById(R.id.title);
        final TextView discount = findViewById(R.id.discount);
        final TextView description = findViewById(R.id.description);
        final TextView provider = findViewById(R.id.provider);
        final TextView merchant = findViewById(R.id.merchant);
        final TextView actualPrice = findViewById(R.id.actual_price);
        final TextView offeredPrice = findViewById(R.id.offered_price);

        final Button map = findViewById(R.id.map);
        final Button link = findViewById(R.id.link);


        CognitoUserPool cognitoUserPool = new CognitoSettings(this).getUserPool();
        final CognitoUser cognitoUser = cognitoUserPool.getCurrentUser();


        Call<Deal> mainDealCall = MainApplication.getInstance().getService().getDeal(dealId);

        mainDealCall.enqueue(new Callback<Deal>() {
            @Override
            public void onResponse(Call<Deal> call, Response<Deal> response) {
                if (response == null || response.body() == null || response.body().getDeal() == null) {

                    progressBar.setVisibility(View.GONE);
                    loading.setText("Some unknown error occurred!");

                    progressLayout.setVisibility(View.VISIBLE);
                    dealLayout.setVisibility(View.GONE);
                } else {
                    final MainDeal mainDeal = response.body().getDeal();

                    progressLayout.setVisibility(View.GONE);
                    dealLayout.setVisibility(View.VISIBLE);


                    if (!TextUtils.isEmpty(mainDeal.getImageUrl())) {
                        imageView.setVisibility(View.VISIBLE);
                        Picasso.get().load(mainDeal.getImageUrl()).into(imageView);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }

                    if (mainDeal.getDiscountPercentage() != null) {
                        discount.setText(String.format("%.2f", mainDeal.getDiscountPercentage() * 100) + " %");
                    } else {
                        discount.setText("N/A");
                    }

                    if (mainDeal.getTitle() != null) {
                        title.setText(mainDeal.getTitle());
                    } else {
                        title.setText("N/A");
                    }

                    if (mainDeal.getDescription() != null) {
                        description.setText(mainDeal.getDescription());
                    } else {
                        description.setText("N/A");
                    }


                    if (mainDeal.getProviderName() != null) {
                        provider.setText(mainDeal.getProviderName());
                    } else {
                        provider.setText("N/A");
                    }

                    if (mainDeal.getPrice() != null && !Double.isNaN(mainDeal.getPrice())) {
                        offeredPrice.setText(String.valueOf(mainDeal.getPrice()));
                    } else {
                        offeredPrice.setText("N/A");
                    }

                    if (mainDeal.getValue() != null) {
                        actualPrice.setText(String.valueOf(mainDeal.getValue()));
                    } else {
                        actualPrice.setText("N/A");
                    }

                    if (mainDeal.getUrl() != null) {

                        link.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mainDeal.getUrl()));
                                startActivity(intent);
                            }
                        });

                    } else {
                        link.setVisibility(View.GONE);
                    }

                    if (mainDeal.getMerchant() != null) {

                        if (mainDeal.getMerchant().getName() != null) {
                            merchant.setText(mainDeal.getMerchant().getName());
                        } else {
                            merchant.setText("N/A");
                        }

                        if (mainDeal.getMerchant().getLatitude() != null
                                && mainDeal.getMerchant().getLongitude() != null
                                && !Double.isNaN(mainDeal.getMerchant().getLatitude())
                                && !Double.isNaN(mainDeal.getMerchant().getLongitude())) {

                            map.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(String.format("https://www.google.com/maps/search/?api=1&query=%f,%f",
                                                    mainDeal.getMerchant().getLatitude(), mainDeal.getMerchant().getLongitude())));
                                    startActivity(intent);
                                }
                            });


                        } else {
                            map.setVisibility(View.GONE);
                        }

                    } else {
                        merchant.setText("N/A");
                        map.setVisibility(View.GONE);
                    }

                    if (!TextUtils.isEmpty(mainDeal.getCategorySlug()) && cognitoUser != null && !TextUtils.isEmpty(cognitoUser.getUserId())) {
                        SelectedDeal selectedDeal = new SelectedDeal();
                        selectedDeal.setUserId(cognitoUser.getUserId());
                        selectedDeal.setCategorySlug(mainDeal.getCategorySlug());

                        Call<ResponseBody> mainDealCall = MainApplication.getInstance().getService().postDeal(selectedDeal);

                        mainDealCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response != null) {

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                }


            }

            @Override
            public void onFailure(Call<Deal> call, Throwable t) {

                progressLayout.setVisibility(View.VISIBLE);
                dealLayout.setVisibility(View.GONE);

                progressBar.setVisibility(View.GONE);
                loading.setText("Some unknown error occurred!");
            }
        });

        progressLayout.setVisibility(View.VISIBLE);
        dealLayout.setVisibility(View.GONE);


    }
}

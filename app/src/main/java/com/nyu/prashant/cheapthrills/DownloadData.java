package com.nyu.prashant.cheapthrills;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.nyu.prashant.cheapthrills.model.ApiResponseModel;
import com.nyu.prashant.cheapthrills.model.Deal;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by jatin on 21/5/15.
 */
public class DownloadData extends AsyncTask<Void,Integer, String> {
    Location location;
    Context callingContext;
    ProgressDialog pbDialog;
    TextView textView;

    @Override
    protected void onPreExecute() {
        pbDialog = pbDialog.show(callingContext, "GRABBING LATEST DEALS", "Please wait", true, false);
        super.onPreExecute();
    }

  public DownloadData(Context callingContext, Location location,TextView textView) {
        this.callingContext = callingContext;
         this.location =  location;
         this.textView = textView;

    }


    @Override
    protected String doInBackground(Void... url1) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(
                "https://api.discountapi.com/v2/deals?api_key=IBWXKxPd&location="+location.getLatitude()+","+location.getLongitude());
        getRequest.addHeader("accept", "application/json");

        try {
            HttpResponse response = httpClient.execute(getRequest);
            String responseString = IOUtils.toString(response.getEntity().getContent());
            System.out.print(responseString);
            return responseString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String abc) {
        pbDialog.dismiss();
        pbDialog.cancel();
        Gson gson = new Gson();
        ApiResponseModel apiResponseModel= gson.fromJson(abc, ApiResponseModel.class);
        String city = cityName();
        String deals = createString(city,apiResponseModel);
        textView.setText(deals);

    }

    private String cityName() {
        /*------- To get city name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(callingContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            System.out.println(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
        }
        return cityName;
    }


    private String createString(String city,ApiResponseModel apiResponseModel) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Current Location : "+ city+"\n\n\n\n");
        stringBuilder.append("-------------------- DEALS --------------------"+"\n\n");


        List<Deal> deals = apiResponseModel.getDeals();

        for(Deal deal: deals)
        {

            /// SHIVIKA Add code here


            //---Title
            stringBuilder.append(deal.getDeal().getTitle()+"\n");

            //---Price
            stringBuilder.append("Price: " + deal.getDeal().getPrice()+"\n");

            //-------Amount
            stringBuilder.append("Discounted Amount: " + deal.getDeal().getDiscountAmount()+"\n");

            //////--------------- Type -------- ///
            stringBuilder.append("Type: " + deal.getDeal().getCategoryName()+"\n");

            //////--------------- Type -------- ///
            stringBuilder.append("Descrption: "+deal.getDeal().getDescription());




            stringBuilder.append("\n ----------------------------------------------------"+"\n\n");
        }
        return stringBuilder.toString();
    }

}
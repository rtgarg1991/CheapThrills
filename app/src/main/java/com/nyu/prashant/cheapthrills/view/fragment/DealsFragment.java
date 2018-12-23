package com.nyu.prashant.cheapthrills.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nyu.prashant.cheapthrills.MainApplication;
import com.nyu.prashant.cheapthrills.R;
import com.nyu.prashant.cheapthrills.model.DealList;
import com.nyu.prashant.cheapthrills.view.adapter.DealsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rohit on 20/12/18.
 */

public class DealsFragment extends Fragment implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private LocationManager locationManager;
    private String provider;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView loading;
    TextView error;
    Button permission;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_deals, container, false);
        Bundle args = getArguments();


        recyclerView = rootView.findViewById(R.id.recycler_view);
        progressBar = rootView.findViewById(R.id.progress_bar);
        loading = rootView.findViewById(R.id.loading);
        error = rootView.findViewById(R.id.error);
        permission = rootView.findViewById(R.id.permission);

        recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        permission.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    checkLocationPermission();
                } else {

                    // for now get last known location and update offers list

                    Location location = locationManager.getLastKnownLocation(provider);

                    onLocationChanged(location);

                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        DealsAdapter adapter = new DealsAdapter();
        recyclerView.setAdapter(adapter);


        // get location manager and provider
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        checkLocationPermission();


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // if
        if (getContext() != null && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            recyclerView.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            permission.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(provider)) {
                provider = locationManager.getBestProvider(new Criteria(), false);
            }

            // request location updates
            locationManager.requestLocationUpdates(provider, 400, 1, this);

            // for now get last known location and update offers list

            Location location = locationManager.getLastKnownLocation(provider);

            onLocationChanged(location);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // check if we have permission, then we need to disable location updates
        if (getContext() != null && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }

    private boolean checkLocationPermission() {
        // check if we have the permission or not
        if (getContext() != null && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            error.setText("No Permission");

            // Should we show an explanation to user before asking for permission
            if (getActivity() != null && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // show explaination dialog to user and if user accepts, then ask for permission
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission")
                        .setMessage("Please provide location permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                if (getActivity() != null) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            MY_PERMISSIONS_REQUEST_LOCATION);
                                }
                            }
                        })
                        .create()
                        .show();


            } else {
                if (getActivity() != null) {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
            return false;
        } else {
            // we have the permission
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // gets triggered when user responds to the permission dialog
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted,
                    // we can start requesting location updates now

                    // just make sure first
                    if (getContext() != null && ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (TextUtils.isEmpty(provider)) {
                            provider = locationManager.getBestProvider(new Criteria(), false);
                        }

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                        return;
                    }

                }


                recyclerView.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                permission.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            return;
        }

        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Location info: Lat", lat.toString());
        Log.i("Location info: Lng", lng.toString());

        recyclerView.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        permission.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);


        Call<DealList> getDealsCall = MainApplication.getInstance().getService().getDeals(lat, lng);
        getDealsCall.enqueue(new Callback<DealList>() {
            @Override
            public void onResponse(Call<DealList> call, Response<DealList> response) {
                if (response != null) {
                    if (response.body() != null) {
                        DealList dealList = response.body();

                        if (recyclerView.getAdapter() == null || !(recyclerView.getAdapter() instanceof DealsAdapter)) {

                            DealsAdapter adapter = new DealsAdapter();
                            recyclerView.setAdapter(adapter);
                        }

                        ((DealsAdapter) recyclerView.getAdapter()).setData(dealList);


                        recyclerView.setVisibility(View.VISIBLE);
                        error.setVisibility(View.GONE);
                        permission.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    } else {


                        error.setText("Not deals found. Try again");


                        recyclerView.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);
                        permission.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DealList> call, Throwable t) {

                error.setText("Some error occrred! Try Again");


                recyclerView.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
                permission.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

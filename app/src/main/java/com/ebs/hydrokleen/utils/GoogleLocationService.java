package com.ebs.hydrokleen.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

public class GoogleLocationService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Context context;
    private Location mylocation;
    private LoadingDialog loadingDialog;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    private String latitude = "", longitude = "";

    public GoogleLocationService(Context context) {
        this.context = context;
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}

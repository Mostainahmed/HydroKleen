package com.ebs.hydrokleen.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ebs.hydrokleen.BuildConfig;
import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.databinding.ActivitySplashBinding;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.LatLng;
import com.ebs.hydrokleen.networkutils.PermissionCallBack;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LatLngProvider;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.LocationForLatLng;
import com.ebs.hydrokleen.utils.MyBackGroundService;
import com.ebs.hydrokleen.utils.SendLocationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, PermissionCallBack {
    public ActivitySplashBinding binding;
    private static final int SPLASH_TIME_OUT = 2000;
    private TelephonyManager manager;
    private DeviceItem deviceItem;
    private Location mylocation;
    private LoadingDialog loadingDialog;
    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    private String latitude = "", longitude = "";
    private PermissionCallBack permissionCallback;
    private AlertDialog permissionalertDialog;
    private static final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Request to show full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        init();
        setUpGClient();

//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                Intent mainIntent = new Intent(SplashActivity.this, TestLocationActivityDemo.class);
//                Bundle anim = ActivityOptions.makeCustomAnimation(getApplicationContext(),
//                        R.anim.animation, R.anim.animation2).toBundle();
//                startActivity(mainIntent, anim);
//
//                SplashActivity.this.finish();
//
//            }
//        }, (long) SPLASH_TIME_OUT);
    }

    private void init() {
        deviceItem = new DeviceItem();
        manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //deviceItem = new DeviceItem();
        //permissionCallback = this;
        loadingDialog = new LoadingDialog(SplashActivity.this);
        permissionCallback = this;
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //checkPermissions();
        checkpermission();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            latitude=String.valueOf(mylocation.getLatitude());
            longitude=String.valueOf(mylocation.getLongitude());
            //Toast.makeText(this, latitude+longitude, Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            finish();
            //Or Do whatever you want with your location
        }
    }

    private void getMyLocation(){
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ActivityCompat
                                            .checkSelfPermission(getApplicationContext(),
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                        if(mylocation!=null){
                                            splashScreenLoading();
                                        }else{
                                            splashScreenLoading();
                                        }
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(SplashActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        //splashScreenLoading();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
            case PERMISSION_REQUEST_CODE:
                checkpermission();
                break;
        }
    }

    /*private void checkPermissions(){
        int permissionLocation = ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        }else{
            getMyLocation();
            //splashScreenLoading();
        }

    }*/

    private void checkpermission() {
        Dexter.withContext(this)
                .withPermissions(Arrays.asList(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE

                ))
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        Log.d("TAG", "onPermissionsChecked:  Check "+report.areAllPermissionsGranted());
                        Log.d("TAG", "onPermissionsChecked permanently: "+report.isAnyPermissionPermanentlyDenied());
                        Log.d("TAG", "onPermissionsChecked Size: "+report.getGrantedPermissionResponses().size());
                        Log.d("TAG", "onPermissionsChecked Size: "+report.getGrantedPermissionResponses().size());
                        for (int i = 0; i < report.getGrantedPermissionResponses().size(); i++) {
                            Log.d("TAG", "onPermissionsChecked: "+report.getGrantedPermissionResponses().get(i).getPermissionName());
                        }
                        if (report.areAllPermissionsGranted()){
                            permissionCallback.OnResult(true);
                        }

                        else if (!report.areAllPermissionsGranted()){
                            showSettingsDialog();
                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })/*.
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })*/.check();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }*/

    public void splashScreenLoading(){
        loadingDialog.startLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
//                mainIntent.putExtra("lat", latitude);
//                mainIntent.putExtra("lng", longitude);
                Bundle anim = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                        R.anim.animation, R.anim.animation2).toBundle();
                startActivity(mainIntent, anim);
                loadingDialog.dismissDialog();
                SplashActivity.this.finish();

            }
        }, (long) SPLASH_TIME_OUT);
    }

    @Override
    public void OnResult(boolean code) {
        getDeviceInfo();
        getMyLocation();
    }

    private void showSettingsDialog() {
        permissionalertDialog = new AlertDialog.Builder(this)
                .setTitle("Need Permission")
                .setMessage("This app requires LOCATION and PHONE STATE permission. You can grant them in app settings")
                .setPositiveButton("Goto Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSettings();
                    }
                })
                .setIcon(R.drawable.ic_settings_applications_black_24dp)
                .setCancelable(false)
                .show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
    }
    private String getDeviceId() throws SecurityException{
        String deviceUniqueIdentifier = null;
        if (null != manager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceUniqueIdentifier = manager.getImei();
            } else {
                deviceUniqueIdentifier = manager.getDeviceId();
            }
        }
        else if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
            deviceUniqueIdentifier = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        return deviceUniqueIdentifier;
    }

    private void getDeviceInfo() {
        deviceItem.deviceId = getDeviceId();
        if(deviceItem.deviceId == null){
            deviceItem.deviceId = " ";
        }

        deviceItem.imsi = manager.getSubscriberId(); // IMSI
        if(deviceItem.imsi == null){
            deviceItem.imsi = " ";
        }

        deviceItem.imei1 = manager.getDeviceId(); // IMEI 1
        if(deviceItem.imei1 == null){
            deviceItem.imei1 = " ";
        }

        deviceItem.imei2 = manager.getImei();  //IMEI 2
        if(deviceItem.imei2 == null){
            deviceItem.imei2 = " ";
        }

        deviceItem.softwareVersion = manager.getDeviceSoftwareVersion(); // SOFTWARE // VERSION
        if(deviceItem.softwareVersion == null){
            deviceItem.softwareVersion = " ";
        }

        deviceItem.simSerialNumber = manager.getSimSerialNumber(); // SIM SERIAL
        if(deviceItem.simSerialNumber == null){
            deviceItem.simSerialNumber = " ";
        }

        deviceItem.operator = manager.getNetworkOperator(); // OPERATOR ID
        if(deviceItem.operator == null){
            deviceItem.operator = " ";
        }

        deviceItem.operatorName = manager.getNetworkOperatorName(); // OPERATOR// NAME
        if(deviceItem.operatorName == null){
            deviceItem.operatorName = " ";
        }

        deviceItem.brand = Build.BRAND; // DEVICE BRAND
        if(deviceItem.brand == null){
            deviceItem.brand = " ";
        }

        deviceItem.model = Build.MODEL; // DEVICE MODEL
        if(deviceItem.model == null){
            deviceItem.model = " ";
        }

        deviceItem.release = Build.VERSION.RELEASE; // ANDROID VERSION LIKE 4.4.4
        if(deviceItem.release == null){
            deviceItem.release = " ";
        }

        deviceItem.sdkVersion = String.valueOf(Build.VERSION.SDK_INT); // API VERSION LIKE 19
        deviceItem.versionCode = String.valueOf(BuildConfig.VERSION_CODE);

        Log.d("TAG", "getDeviceInfo: "+deviceItem.deviceId);

        saveDeviceInfoToShardPref(deviceItem);

    }

    private void saveDeviceInfoToShardPref(DeviceItem di) {

        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(di);
        editor.putString(GlobalVariables.mobileInfo, json);
        editor.commit();

    }
}

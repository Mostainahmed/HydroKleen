package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.location.Location;

import android.location.LocationManager;

import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.ebs.hydrokleen.BuildConfig;
import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.databinding.ActivityLoginBinding;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.Login;
import com.ebs.hydrokleen.models.Technician;
import com.ebs.hydrokleen.networkutils.PermissionCallBack;
import com.ebs.hydrokleen.networkutils.RetrofitClient;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity{

    private static final int GPS_REQUEST_CODE = 9003;
    private static final int PERMISSION_REQUEST_CODE = 101;
    public ActivityLoginBinding binding;
    private LoadingDialog loadingDialog;
    private DeviceItem deviceItem;
    public String stringLatitude;
    public String stringLongitude;
    public GlobalVariables globalVariables = new GlobalVariables();
    private Location location;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        checkForSavedLoginStatus();
        init();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGPSEnabled()) {
                    if(getLocationFinal()){
                        if (NetworkCheck.isNetworkAvailable(getApplicationContext())) {
                            String teamID = binding.etUserId.getText().toString();
                            String teamPassword = binding.etPassword.getText().toString();
                            boolean result = checkField(teamID, teamPassword); // checking if any of the field is empty

                            if (result) {
                                teamLogin(teamID, teamPassword);
                            } else {
                                if (teamID.isEmpty()) {
                                    binding.etUserId.setError("Team ID Can't Be Empty");
                                    return;
                                } else if (teamPassword.isEmpty()) {
                                    binding.etPassword.setError("Password Can't Be empty");
                                    return;
                                }

                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Please wait a while until the location is set by GPS.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please Turn on your GPS first", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }


    private boolean checkField(String teamID, String teamPassword) {

        if(teamID.isEmpty() || teamPassword.isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    private void init() {
        loadingDialog = new LoadingDialog(LoginActivity.this);
        gps = new GPSTracker(getApplicationContext());
        getDeviceInfo();
        //loadingDialog.startLoadingDialog();
        getLocationFinal();
        //manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        //deviceItem = new DeviceItem();
        //permissionCallback = this;
    }

    public void teamLogin(final String teamID, final String teamPassword){

        loadingDialog.startLoadingDialog(); // Added Loading Screen

        Call<Login> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .loginUser(teamID, teamPassword,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode, stringLatitude, stringLongitude);

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try{
                    if(response.code() == 200){
                        assert response.body() != null;
                        if(response.body().getResult().equals("success")){
                            saveDataToSharedPreferences(teamID, teamPassword);
                            loadingDialog.dismissDialog();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Team ID or Password didn't matched", Toast.LENGTH_LONG).show();
                            loadingDialog.dismissDialog();
                        }

                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismissDialog();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }

        });

    }

    private void saveDataToSharedPreferences(String teamID, String teamPassword){

        GlobalVariables globalVariables = new GlobalVariables();
        globalVariables.saveDataForLogin(getApplicationContext(), teamID, teamPassword);

    }

    private void checkForSavedLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVariables.sharedPref, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(GlobalVariables.savedDataStatus, false)){
            String teamId = sharedPreferences.getString(GlobalVariables.teamId, "");
            String teamPassword = sharedPreferences.getString(GlobalVariables.teamPassword, "");
            binding.etUserId.setText(teamId);
            binding.etPassword.setText(teamPassword);
        }
    }

    private void getDeviceInfo() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(GlobalVariables.mobileInfo, "");
        Gson gson = new Gson();
        deviceItem = gson.fromJson(json, DeviceItem.class);
    }



    private boolean isGPSEnabled(){
        LocationManager locationManagerNew = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManagerNew.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(providerEnabled){
            return true;
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app to work")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, GPS_REQUEST_CODE);
                        }
                    })
                    .setCancelable(false)
                    .show();

        }

        return false;
    }


    public boolean getLocationFinal(){

        location = gps.getLocation();
        if(location!=null){
            stringLatitude = String.valueOf(location.getLatitude());
            stringLongitude = String.valueOf(location.getLongitude());
            return true;
        }
        else{
            stringLongitude = " ";
            stringLatitude = " ";
            return true;
        }
    }

}

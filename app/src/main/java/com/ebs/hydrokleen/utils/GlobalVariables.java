package com.ebs.hydrokleen.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.ebs.hydrokleen.BuildConfig;
import com.ebs.hydrokleen.models.DeviceItem;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class GlobalVariables {

    public static final String sharedPref = "logincredentials";
    public static final String teamId = "teamid";
    public static final String teamPassword = "teampassword";
    public static final String savedDataStatus = "savedLoginStatus";
    public static final String mobileInfo = "mobileInfo";
    public static final String teamObject = "teamobject";
    public static final String technicianArrayList = "technicianarraylist";


    public static void init(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPref, MODE_PRIVATE);
    }

    public void saveDataForLogin(Context context, String teamid, String teampassword) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPref, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(teamId, teamid);
        editor.putString(teamPassword, teampassword);
        editor.putBoolean(savedDataStatus, true);
        //editor.commit();
        editor.apply();
    }

    public String getTeamID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPref, MODE_PRIVATE);
        String teamid = sharedPreferences.getString(teamId, "");
        return teamid;
    }
}

package com.ebs.hydrokleen.networkutils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ebs.hydrokleen.models.AcDetails;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.StatusUpdate;
import com.ebs.hydrokleen.models.Team;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amit on 16,April,2020
 * kundu.amit517@gmail.com
 */
public class StatusUpdateNetworkCall {

    public static DeviceItem deviceItem = new DeviceItem();
    public static String lat = "", lng = "", teamId = "";


    public static void changeServiceStatus(String lat, String lng, final String statusCode, String status, String remarks, final Activity activity, String serviceid, final StatusUpdateCallBack statusUpdateCallBack) {
        Log.d("TAG", "changeServiceStatus: "+statusCode+status+remarks+serviceid);

        init(activity);

        Call<StatusUpdate> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .serviceStatusUpdate(serviceid,statusCode,status,remarks,
                        teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode, lat, lng);


        call.enqueue(new Callback<StatusUpdate>() {
            @Override
            public void onResponse(Call<StatusUpdate> call, Response<StatusUpdate> response) {

                try{
                    if(response.code() == 200){
                        Log.d("TAG", "onResponse: amit"+response.body().getStatus().getResponseCode());
                        assert response.body() != null;
                        if(response.body().getStatus().getResponseCode().equals("1")){
                            StatusUpdate serviceStatus = response.body();  // Data Stored for any later use

                            statusUpdateCallBack.OnResult(true);

                        }
                        else{

                            statusUpdateCallBack.OnResult(false);
                            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        statusUpdateCallBack.OnResult(false);
                        Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    statusUpdateCallBack.OnResult(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StatusUpdate> call, Throwable t) {

                statusUpdateCallBack.OnResult(false);
                Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public static void changeItemStatus(String lat, String lng, final String statusCode, String serviceitemid, final Activity activity, final StatusUpdateCallBack statusUpdateCallBack){
        init(activity);
        Call<StatusUpdate> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .serviceItemUpdate(serviceitemid,statusCode,teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode,lat,lng);


        call.enqueue(new Callback<StatusUpdate>() {
            @Override
            public void onResponse(Call<StatusUpdate> call, Response<StatusUpdate> response) {

                try{
                    if(response.code() == 200){
                        Log.d("TAG", "onResponse: "+response.body().getStatus().getResponseCode());
                        assert response.body() != null;
                        if(response.body().getStatus().getResponseCode().equals("1")){

                            statusUpdateCallBack.OnResult(true);


                        }
                        else{
                            statusUpdateCallBack.OnResult(false);
                            Toast.makeText(activity, "Something Sent Wrong! Please try again later!", Toast.LENGTH_LONG).show();
                            //loadingDialog.dismissDialog();
                        }

                    }
                    else {
                        statusUpdateCallBack.OnResult(false);
                        Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        //loadingDialog.dismissDialog();
                    }

                }catch(Exception e){
                    statusUpdateCallBack.OnResult(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StatusUpdate> call, Throwable t) {
                statusUpdateCallBack.OnResult(false);
                Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                //loadingDialog.dismissDialog();
            }
        });

    }

    public static void teamStatusUpdate(String latitude, String lontitude, final String statusCode, final Activity activity, final StatusUpdateCallBack statusUpdateCallBack){
        init(activity);
        Call<StatusUpdate> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .teamStatusUpdate(statusCode,teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode,latitude,lontitude);


        call.enqueue(new Callback<StatusUpdate>() {
            @Override
            public void onResponse(Call<StatusUpdate> call, Response<StatusUpdate> response) {

                try{
                    if(response.code() == 200){
                        Log.d("TAG", "onResponse: "+response.body().getStatus().getResponseCode());
                        assert response.body() != null;
                        if(response.body().getStatus().getResponseCode().equals("1")){

                            statusUpdateCallBack.OnResult(true);


                        }
                        else{
                            statusUpdateCallBack.OnResult(false);
                            Toast.makeText(activity, "Something Sent Wrong! Please try again later!", Toast.LENGTH_LONG).show();
                            //loadingDialog.dismissDialog();
                        }

                    }
                    else {
                        statusUpdateCallBack.OnResult(false);
                        Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        //loadingDialog.dismissDialog();
                    }

                }catch(Exception e){
                    statusUpdateCallBack.OnResult(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StatusUpdate> call, Throwable t) {
                statusUpdateCallBack.OnResult(false);
                Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                //loadingDialog.dismissDialog();
            }
        });

    }


    public static void getAcInfo(String lat, String lng, final String acCode, final Activity activity, final AcInfoCallBack acInfoCallBack){
        init(activity);
        Call<AcDetails> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .getAcInfo(acCode,teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode,lat,lng);


        call.enqueue(new Callback<AcDetails>() {
            @Override
            public void onResponse(Call<AcDetails> call, Response<AcDetails> response) {

                try{
                    if(response.code() == 200){
                        Log.d("TAG", "onResponse: "+response.body().getStatus().getResponseCode());
                        assert response.body() != null;
                        if(response.body().getStatus().getResponseCode().equals("1")){

                            AcDetails acDetails = response.body();
                            acInfoCallBack.OnResult(true);
                            acInfoCallBack.AcDetails(acDetails);

                        }
                        else{
                            acInfoCallBack.OnResult(false);
                            Toast.makeText(activity, "Something Sent Wrong! Please try again later!", Toast.LENGTH_LONG).show();
                            //loadingDialog.dismissDialog();
                        }

                    }
                    else {
                        acInfoCallBack.OnResult(false);
                        Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        //loadingDialog.dismissDialog();
                    }

                }catch(Exception e){
                    acInfoCallBack.OnResult(false);
                    e.printStackTrace();
                }
            }



            @Override
            public void onFailure(Call<AcDetails> call, Throwable t) {
                acInfoCallBack.OnResult(false);
                Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                //loadingDialog.dismissDialog();
            }
        });
    }

    public static void postAcInfo(String lat, String lng, final String acCode, final String acbrand, final String ton,
                                  final String type, final String tech, final String installationdate,
                                  final String extrainfo, final Activity activity,
                                  final StatusUpdateCallBack statusUpdateCallBack){
        init(activity);
        Call<StatusUpdate> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .postAcInfo(acCode,teamId,deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode,acbrand,
                        ton,type,tech,installationdate,extrainfo,lat,lng);


        call.enqueue(new Callback<StatusUpdate>() {
            @Override
            public void onResponse(Call<StatusUpdate> call, Response<StatusUpdate> response) {

                try{
                    if(response.code() == 200){
                        Log.d("TAG", "onResponse: "+response.body().getStatus().getResponseCode());
                        assert response.body() != null;
                        if(response.body().getStatus().getResponseCode().equals("1")){

                            statusUpdateCallBack.OnResult(true);

                        }
                        else{
                            statusUpdateCallBack.OnResult(false);
                            Toast.makeText(activity, "Something Sent Wrong! Please try again later!", Toast.LENGTH_LONG).show();
                            //loadingDialog.dismissDialog();
                        }

                    }
                    else {
                        statusUpdateCallBack.OnResult(false);
                        Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                        //loadingDialog.dismissDialog();
                    }

                }catch(Exception e){
                    statusUpdateCallBack.OnResult(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StatusUpdate> call, Throwable t) {
                statusUpdateCallBack.OnResult(false);
                Toast.makeText(activity, "Something Went wrong! Please try again later!", Toast.LENGTH_SHORT).show();
                //loadingDialog.dismissDialog();
            }
        });

    }


    public static void uploadImage(String lat, String lng, String serviceId, String serviceItemId,String clientaccode,String finalImage, final Activity activity, final StatusUpdateCallBack statusUpdateCallBack){
        init(activity);

        Call<Team> call = RetrofitClient
                .getInstance()
                .getRetrofitApi()
                .imageUpload(serviceId, serviceItemId, clientaccode, finalImage, teamId, deviceItem.deviceId,deviceItem.imsi,deviceItem.imei1,
                        deviceItem.imei2,deviceItem.softwareVersion,deviceItem.simSerialNumber,
                        deviceItem.brand,deviceItem.model,deviceItem.operator,deviceItem.operatorName,
                        deviceItem.release,deviceItem.sdkVersion,deviceItem.versionCode, lat, lng);

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                if(response.body().getStatus().getResponseCode().equals("1")){
                    statusUpdateCallBack.OnResult(true);

                }else{
                    statusUpdateCallBack.OnResult(false);
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                statusUpdateCallBack.OnResult(false);
            }
        });
    }



    private static void init(Context context){
        getDeviceInfo(context);
        getTeamId(context);
    }

    private static void getDeviceInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalVariables.sharedPref, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(GlobalVariables.mobileInfo, "");
        Gson gson = new Gson();
        deviceItem = gson.fromJson(json, DeviceItem.class);
    }

    private static void getTeamId(Context context){
        GlobalVariables globalVariables = new GlobalVariables();
        teamId = globalVariables.getTeamID(context);
    }

}

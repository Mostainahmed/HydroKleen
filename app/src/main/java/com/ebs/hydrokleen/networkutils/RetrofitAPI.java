package com.ebs.hydrokleen.networkutils;

import com.ebs.hydrokleen.models.AcDetails;
import com.ebs.hydrokleen.models.Login;
import com.ebs.hydrokleen.models.ServiceDetails;
import com.ebs.hydrokleen.models.StatusUpdate;
import com.ebs.hydrokleen.models.Team;
import com.ebs.hydrokleen.models.WorkType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @FormUrlEncoded
    @POST("login")
    Call<Login> loginUser(
            @Field("username") String teamID,
            @Field("password") String teamPassword,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("worklist")
    Call<ArrayList<WorkType>> worklist(
            @Field("teamid") String teamID,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("servicedetails")
    Call<ServiceDetails> serviceDetails(
            @Field("serviceid") String serviceID,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("uploadAcPics")
    Call<Team> imageUpload(
            @Field("serviceid") String serviceId,
            @Field("serviceitemid") String serviceItemID,
            @Field("clientaccode") String clientaccode,
            @Field("image") String image,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("serviceStatusUpdate")
    Call<StatusUpdate> serviceStatusUpdate(
            @Field("serviceid") String serviceId,
            @Field("statuscode") String statusCode,
            @Field("status") String status,
            @Field("statusremark") String statusRemark,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );


    @FormUrlEncoded
    @POST("serviceItemStatusUpdate")
    Call<StatusUpdate> serviceItemUpdate(
            @Field("serviceitemid") String serviceItemId,
            @Field("statuscode") String statusCode,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("dashboardInfo")
    Call<Team> dashboardInfo(
            @Field("username") String teamID,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );


    @FormUrlEncoded
    @POST("teamStatusUpdate")
    Call<StatusUpdate> teamStatusUpdate(
            @Field("statuscode") String statusCode,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("getUpdateAcInfo")
    Call<AcDetails> getAcInfo(
            @Field("clientaccode") String clientaccode,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );

    @FormUrlEncoded
    @POST("updateAcInfo")
    Call<StatusUpdate> postAcInfo(
            @Field("clientaccode") String clientaccode,
            @Field("username") String username,
            @Field("deviceId") String deviceId,
            @Field("imsi") String imsi,
            @Field("imei1") String imei1,
            @Field("imei2") String imei2,
            @Field("softwareVersion") String softwareVersion,
            @Field("simSerialNumber") String simSerialNumber,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("operator") String operator,
            @Field("operatorName") String operatorName,
            @Field("release") String release,
            @Field("sdkVersion") String sdkVersion,
            @Field("versionCode") String versionCode,
            @Field("brand") String acbrand,
            @Field("ton") String ton,
            @Field("type") String type,
            @Field("tech") String tech,
            @Field("installationdate") String installationdate,
            @Field("extrainfo") String extrainfo,
            @Field("lat") String latitude,
            @Field("lng") String longitude
    );



}

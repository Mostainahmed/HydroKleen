package com.ebs.hydrokleen.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("data")
    @Expose
    private Data data;
    /**
     * No args constructor for use in serialization
     *
     */
    public Team() {
    }

    /**
     *
     * @param data
     * @param status
     */
    public Team(Status status, Data data, Workqueue workqueue) {
        super();
        this.status = status;
        this.data = data;
    }



    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}


//    @SerializedName("result")
//    @Expose
//    private String result;
//
//    @SerializedName("teamid")
//    @Expose
//    private String teamId;
//
//    @SerializedName("teamname")
//    @Expose
//    private String teamName;
//
//    @SerializedName("vehiclemodel")
//    @Expose
//    private String vehicleModel;
//
//    @SerializedName("vehicleregno")
//    @Expose
//    private String vehicleRegNo;
//
//    @SerializedName("driverid")
//    @Expose
//    private String driverId;
//
//    @SerializedName("drivername")
//    @Expose
//    private String driverName;
//
//    @SerializedName("phone")
//    @Expose
//    private String driverPhoneNumber;
//
//
//    private List<Technician> technicians;
//
//    public Team(){
//
//    }

//    public Team(String result, String teamId, String teamName, String vehicleModel,
//                String vehicleRegNo, String driverId, String driverName, String driverPhoneNumber,
//                List<Technician> technicians) {
//        this.result = result;
//        this.teamId = teamId;
//        this.teamName = teamName;
//        this.vehicleModel = vehicleModel;
//        this.vehicleRegNo = vehicleRegNo;
//        this.driverId = driverId;
//        this.driverName = driverName;
//        this.driverPhoneNumber = driverPhoneNumber;
//        this.technicians = technicians;
//    }
//
//    protected Team(Parcel in) {
//        result = in.readString();
//        teamId = in.readString();
//        teamName = in.readString();
//        vehicleModel = in.readString();
//        vehicleRegNo = in.readString();
//        driverId = in.readString();
//        driverName = in.readString();
//        driverPhoneNumber = in.readString();
//    }

//    public static final Creator<Team> CREATOR = new Creator<Team>() {
//        @Override
//        public Team createFromParcel(Parcel in) {
//            return new Team(in);
//        }
//
//        @Override
//        public Team[] newArray(int size) {
//            return new Team[size];
//        }
//    };

//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public void setTeamId(String teamId) {
//        this.teamId = teamId;
//    }
//
//    public void setTeamName(String teamName) {
//        this.teamName = teamName;
//    }
//
//    public void setVehicleModel(String vehicleModel) {
//        this.vehicleModel = vehicleModel;
//    }
//
//    public void setVehicleRegNo(String vehicleRegNo) {
//        this.vehicleRegNo = vehicleRegNo;
//    }
//
//    public void setDriverId(String driverId) {
//        this.driverId = driverId;
//    }
//
//    public void setDriverName(String driverName) {
//        this.driverName = driverName;
//    }
//
//    public void setDriverPhoneNumber(String driverPhoneNumber) {
//        this.driverPhoneNumber = driverPhoneNumber;
//    }
//
//    public void setTechnicians(List<Technician> technicians) {
//        this.technicians = technicians;
//    }
//
//    public String getResult() {
//        return result;
//    }
//
//    public String getTeamId() {
//        return teamId;
//    }
//
//    public String getTeamName() {
//        return teamName;
//    }
//
//    public String getVehicleModel() {
//        return vehicleModel;
//    }
//
//    public String getVehicleRegNo() {
//        return vehicleRegNo;
//    }
//
//    public String getDriverId() {
//        return driverId;
//    }
//
//    public String getDriverName() {
//        return driverName;
//    }
//
//    public String getDriverPhoneNumber() {
//        return driverPhoneNumber;
//    }
//
//    public List<Technician> getTechnicians() {
//        return technicians;
//    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(result);
//        dest.writeString(teamId);
//        dest.writeString(teamName);
//        dest.writeString(vehicleModel);
//        dest.writeString(vehicleRegNo);
//        dest.writeString(driverId);
//        dest.writeString(driverName);
//        dest.writeString(driverPhoneNumber);
//    }
//}

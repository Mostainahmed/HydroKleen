package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("driverid")
    @Expose
    private String driverid;
    @SerializedName("drivername")
    @Expose
    private String drivername;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}


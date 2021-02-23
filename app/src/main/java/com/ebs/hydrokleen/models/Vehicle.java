package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehicle {

    @SerializedName("emodel")
    @Expose
    private String emodel;
    @SerializedName("regno")
    @Expose
    private String regno;

    public String getEmodel() {
        return emodel;
    }

    public void setEmodel(String emodel) {
        this.emodel = emodel;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

}


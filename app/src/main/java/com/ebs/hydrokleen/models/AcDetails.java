
package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcDetails {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private AcData data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AcDetails() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public AcDetails(Status status, AcData data) {
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

    public AcData getData() {
        return data;
    }

    public void setData(AcData data) {
        this.data = data;
    }

}

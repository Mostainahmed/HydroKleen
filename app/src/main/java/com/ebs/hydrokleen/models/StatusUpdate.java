
package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusUpdate {

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
    public StatusUpdate() {
    }

    /**
     * 
     * @param data
     * @param status
     */
    public StatusUpdate(Status status, Data data) {
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

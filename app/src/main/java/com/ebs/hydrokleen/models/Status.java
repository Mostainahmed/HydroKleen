
package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Status implements Serializable {

    @SerializedName("responseCode")
    @Expose
    private String  responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    /**
     * No args constructor for use in serialization
     *
     */
    public Status() {
    }

    /**
     * 
     * @param responseMessage
     * @param responseCode
     */
    public Status(String  responseCode, String responseMessage) {
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public String  getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String  responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

}

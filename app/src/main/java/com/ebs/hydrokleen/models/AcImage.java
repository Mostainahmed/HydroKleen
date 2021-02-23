package com.ebs.hydrokleen.models;

import com.google.gson.annotations.SerializedName;

public class AcImage {
    @SerializedName("serviceitemid")
    public String serviceitemid;

    @SerializedName("serviceid")
    public String serviceid;

    @SerializedName("image")
    public String image;

    @SerializedName("comments")
    public String comments;

    @SerializedName("response")
    public String response;

    public String getResponse() {
        return response;
    }
}

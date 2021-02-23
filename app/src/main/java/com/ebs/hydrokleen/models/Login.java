package com.ebs.hydrokleen.models;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("result")
    private String result;

    @SerializedName("response")
    private String response;

    public Login(String result, String response) {
        this.result = result;
        this.response = response;
    }

    public String getResult() {
        return result;
    }

    public String getResponse() {
        return response;
    }
}

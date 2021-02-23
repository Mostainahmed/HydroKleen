
package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ton {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ton")
    @Expose
    private String ton;
    @SerializedName("btu")
    @Expose
    private String btu;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Ton() {
    }

    /**
     * 
     * @param ton
     * @param btu
     * @param id
     */
    public Ton(String id, String ton, String btu) {
        super();
        this.id = id;
        this.ton = ton;
        this.btu = btu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTon() {
        return ton;
    }

    public void setTon(String ton) {
        this.ton = ton;
    }

    public String getBtu() {
        return btu;
    }

    public void setBtu(String btu) {
        this.btu = btu;
    }

}

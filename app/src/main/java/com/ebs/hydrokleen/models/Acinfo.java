
package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Acinfo {

    @SerializedName("clientaccode")
    @Expose
    private String clientaccode;
    @SerializedName("brand")
    @Expose
    private String clientacbrand;
    @SerializedName("ton")
    @Expose
    private String clientacton;
    @SerializedName("btu")
    @Expose
    private String clientacbtu;
    @SerializedName("type")
    @Expose
    private String clientactype;
    @SerializedName("tech")
    @Expose
    private String clientacelectricity;
    @SerializedName("installationdate")
    @Expose
    private String installationdate;
    @SerializedName("extrainfo")
    @Expose
    private String clientacextrainfo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Acinfo() {
    }

    /**
     * 
     * @param clientacbrand
     * @param clientaccode
     * @param clientacextrainfo
     * @param clientactype
     * @param installationdate
     * @param clientacelectricity
     * @param clientacton
     * @param clientacbtu
     */
    public Acinfo(String clientaccode, String clientacbrand, String clientacton, String clientacbtu, String clientactype, String clientacelectricity, String installationdate, String clientacextrainfo) {
        super();
        this.clientaccode = clientaccode;
        this.clientacbrand = clientacbrand;
        this.clientacton = clientacton;
        this.clientacbtu = clientacbtu;
        this.clientactype = clientactype;
        this.clientacelectricity = clientacelectricity;
        this.installationdate = installationdate;
        this.clientacextrainfo = clientacextrainfo;
    }

    public String getClientaccode() {
        return clientaccode;
    }

    public void setClientaccode(String clientaccode) {
        this.clientaccode = clientaccode;
    }

    public String getClientacbrand() {
        return clientacbrand;
    }

    public void setClientacbrand(String clientacbrand) {
        this.clientacbrand = clientacbrand;
    }

    public String getClientacton() {
        return clientacton;
    }

    public void setClientacton(String clientacton) {
        this.clientacton = clientacton;
    }

    public String getClientacbtu() {
        return clientacbtu;
    }

    public void setClientacbtu(String clientacbtu) {
        this.clientacbtu = clientacbtu;
    }

    public String getClientactype() {
        return clientactype;
    }

    public void setClientactype(String clientactype) {
        this.clientactype = clientactype;
    }

    public String getClientacelectricity() {
        return clientacelectricity;
    }

    public void setClientacelectricity(String clientacelectricity) {
        this.clientacelectricity = clientacelectricity;
    }

    public String getInstallationdate() {
        return installationdate;
    }

    public void setInstallationdate(String installationdate) {
        this.installationdate = installationdate;
    }

    public String getClientacextrainfo() {
        return clientacextrainfo;
    }

    public void setClientacextrainfo(String clientacextrainfo) {
        this.clientacextrainfo = clientacextrainfo;
    }

}

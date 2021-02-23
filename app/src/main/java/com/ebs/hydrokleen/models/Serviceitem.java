
package com.ebs.hydrokleen.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Serviceitem implements Parcelable{

    @SerializedName("serviceitemid")
    @Expose
    private String serviceitemid;
    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("locationid")
    @Expose
    private String locationid;
    @SerializedName("clientaccode")
    @Expose
    private String clientaccode;
    @SerializedName("servicedetails")
    @Expose
    private String servicedetails;
    @SerializedName("billedamount")
    @Expose
    private String billedamount;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("workstatus")
    @Expose
    private String workstatus;
    @SerializedName("acdetails")
    @Expose
    private String acdetails;

    /**
     * No args constructor for use in serialization
     *
     */
    public Serviceitem() {
    }

    /**
     *
     * @param statuscode
     * @param servicedetails
     * @param clientaccode
     * @param locationid
     * @param billedamount
     * @param comment
     * @param acdetails
     * @param serviceid
     * @param serviceitemid
     * @param workstatus
     */
    public Serviceitem(String serviceitemid, String serviceid, String locationid, String clientaccode, String servicedetails, String billedamount, String comment, String statuscode, String workstatus, String acdetails) {
        super();
        this.serviceitemid = serviceitemid;
        this.serviceid = serviceid;
        this.locationid = locationid;
        this.clientaccode = clientaccode;
        this.servicedetails = servicedetails;
        this.billedamount = billedamount;
        this.comment = comment;
        this.statuscode = statuscode;
        this.workstatus = workstatus;
        this.acdetails = acdetails;
    }

    protected Serviceitem(Parcel in) {
        serviceitemid = in.readString();
        serviceid = in.readString();
        locationid = in.readString();
        clientaccode = in.readString();
        servicedetails = in.readString();
        billedamount = in.readString();
        comment = in.readString();
        statuscode = in.readString();
        workstatus = in.readString();
        acdetails = in.readString();
    }

    public static final Creator<Serviceitem> CREATOR = new Creator<Serviceitem>() {
        @Override
        public Serviceitem createFromParcel(Parcel in) {
            return new Serviceitem(in);
        }

        @Override
        public Serviceitem[] newArray(int size) {
            return new Serviceitem[size];
        }
    };

    public String getServiceitemid() {
        return serviceitemid;
    }

    public void setServiceitemid(String serviceitemid) {
        this.serviceitemid = serviceitemid;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getClientaccode() {
        return clientaccode;
    }

    public void setClientaccode(String clientaccode) {
        this.clientaccode = clientaccode;
    }

    public String getServicedetails() {
        return servicedetails;
    }

    public void setServicedetails(String servicedetails) {
        this.servicedetails = servicedetails;
    }

    public String getBilledamount() {
        return billedamount;
    }

    public void setBilledamount(String billedamount) {
        this.billedamount = billedamount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(String workstatus) {
        this.workstatus = workstatus;
    }

    public String getAcdetails() {
        return acdetails;
    }

    public void setAcdetails(String acdetails) {
        this.acdetails = acdetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serviceitemid);
        dest.writeString(serviceid);
        dest.writeString(locationid);
        dest.writeString(clientaccode);
        dest.writeString(servicedetails);
        dest.writeString(billedamount);
        dest.writeString(comment);
        dest.writeString(statuscode);
        dest.writeString(workstatus);
        dest.writeString(acdetails);
    }
}
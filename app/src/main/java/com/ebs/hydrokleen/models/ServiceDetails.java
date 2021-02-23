
package com.ebs.hydrokleen.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceDetails {

    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("totalbillamount")
    @Expose
    private String totalbillamount;
    @SerializedName("clientname")
    @Expose
    private String clientname;
    @SerializedName("phone1")
    @Expose
    private String phone1;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusremark")
    @Expose
    private String statusremark;
    @SerializedName("paymenttype")
    @Expose
    private String paymenttype;
    @SerializedName("pending")
    @Expose
    private Integer pending;
    @SerializedName("showpartialbutton")
    @Expose
    private Integer showpartialbutton;
    @SerializedName("serviceitems")
    @Expose
    private List<Serviceitem> serviceitems = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceDetails() {
    }

    /**
     *
     * @param showpartialbutton
     * @param clientname
     * @param statusremark
     * @param pending
     * @param serviceitems
     * @param totalbillamount
     * @param slot
     * @param paymenttype
     * @param phone1
     * @param status
     */
    public ServiceDetails(String slot, String totalbillamount, String clientname, String phone1, String status, String statusremark, String paymenttype, Integer pending, Integer showpartialbutton, List<Serviceitem> serviceitems) {
        super();
        this.slot = slot;
        this.totalbillamount = totalbillamount;
        this.clientname = clientname;
        this.phone1 = phone1;
        this.status = status;
        this.statusremark = statusremark;
        this.paymenttype = paymenttype;
        this.pending = pending;
        this.showpartialbutton = showpartialbutton;
        this.serviceitems = serviceitems;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getTotalbillamount() {
        return totalbillamount;
    }

    public void setTotalbillamount(String totalbillamount) {
        this.totalbillamount = totalbillamount;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusremark() {
        return statusremark;
    }

    public void setStatusremark(String statusremark) {
        this.statusremark = statusremark;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getShowpartialbutton() {
        return showpartialbutton;
    }

    public void setShowpartialbutton(Integer showpartialbutton) {
        this.showpartialbutton = showpartialbutton;
    }

    public List<Serviceitem> getServiceitems() {
        return serviceitems;
    }

    public void setServiceitems(List<Serviceitem> serviceitems) {
        this.serviceitems = serviceitems;
    }

}
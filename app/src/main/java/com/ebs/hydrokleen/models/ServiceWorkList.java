
package com.ebs.hydrokleen.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Amit on 02,April,2020
 * kundu.amit517@gmail.com
 */
public class ServiceWorkList {

    @SerializedName("serviceid")
    @Expose
    private String serviceid;
    @SerializedName("clientid")
    @Expose
    private String clientid;
    @SerializedName("clientname")
    @Expose
    private String clientname;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("membership")
    @Expose
    private String membership;
    @SerializedName("isvip")
    @Expose
    private String isvip;
    @SerializedName("phone")
    @Expose
    private List<String> phone = null;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("account")
    @Expose
    private Integer account;
    @SerializedName("slot")
    @Expose
    private String slot;
    @SerializedName("orderstatus")
    @Expose
    private String orderstatus;
    @SerializedName("orderstatuscode")
    @Expose
    private String orderstatuscode;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiceWorkList() {
    }

    /**
     *
     * @param clientid
     * @param address
     * @param orderstatus
     * @param membership
     * @param slot
     * @param isvip
     * @param phone
     * @param companyname
     * @param clientname
     * @param serviceid
     * @param account
     * @param remarks
     * @param orderstatuscode
     */
    public ServiceWorkList(String serviceid, String clientid, String clientname, String companyname, String membership, String isvip, List<String> phone, String address, Integer account, String slot, String orderstatus, String orderstatuscode, String remarks) {
        super();
        this.serviceid = serviceid;
        this.clientid = clientid;
        this.clientname = clientname;
        this.companyname = companyname;
        this.membership = membership;
        this.isvip = isvip;
        this.phone = phone;
        this.address = address;
        this.account = account;
        this.slot = slot;
        this.orderstatus = orderstatus;
        this.orderstatuscode = orderstatuscode;
        this.remarks = remarks;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getIsvip() {
        return isvip;
    }

    public void setIsvip(String isvip) {
        this.isvip = isvip;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrderstatuscode() {
        return orderstatuscode;
    }

    public void setOrderstatuscode(String orderstatuscode) {
        this.orderstatuscode = orderstatuscode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}


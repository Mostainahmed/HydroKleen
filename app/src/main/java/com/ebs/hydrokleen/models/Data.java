
package com.ebs.hydrokleen.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Data {

    @SerializedName("teamid")
    @Expose
    private String teamid;
    @SerializedName("teamname")
    @Expose
    private String teamname;
    @SerializedName("vehiclemodel")
    @Expose
    private String vehiclemodel;
    @SerializedName("vehicleregno")
    @Expose
    private String vehicleregno;
    @SerializedName("driverid")
    @Expose
    private String driverid;
    @SerializedName("drivername")
    @Expose
    private String drivername;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("startedworking")
    @Expose
    private String startedworking;
    @SerializedName("finishedworking")
    @Expose
    private String finishedworking;
    @SerializedName("tooklunchbreak")
    @Expose
    private String tooklunchbreak;
    @SerializedName("finishedlunchbreak")
    @Expose
    private String finishedlunchbreak;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("technicians")
    @Expose
    private List<Technician> technicians = null;
    @SerializedName("workqueue")
    @Expose
    private Workqueue workqueue;

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getVehiclemodel() {
        return vehiclemodel;
    }

    public void setVehiclemodel(String vehiclemodel) {
        this.vehiclemodel = vehiclemodel;
    }

    public String getVehicleregno() {
        return vehicleregno;
    }

    public void setVehicleregno(String vehicleregno) {
        this.vehicleregno = vehicleregno;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getStartedworking() {
        return startedworking;
    }

    public void setStartedworking(String startedworking) {
        this.startedworking = startedworking;
    }

    public String getFinishedworking() {
        return finishedworking;
    }

    public void setFinishedworking(String finishedworking) {
        this.finishedworking = finishedworking;
    }

    public String getTooklunchbreak() {
        return tooklunchbreak;
    }

    public void setTooklunchbreak(String tooklunchbreak) {
        this.tooklunchbreak = tooklunchbreak;
    }

    public String getFinishedlunchbreak() {
        return finishedlunchbreak;
    }

    public void setFinishedlunchbreak(String finishedlunchbreak) {
        this.finishedlunchbreak = finishedlunchbreak;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(List<Technician> technicians) {
        this.technicians = technicians;
    }

    public Workqueue getWorkqueue() {
        return workqueue;
    }

    public void setWorkqueue(Workqueue workqueue) {
        this.workqueue = workqueue;
    }

}

package com.ebs.hydrokleen.models;

/**
 * Created by Amit on 08,April,2020
 * kundu.amit517@gmail.com
 */
public class DeviceItem {

    public String deviceId;
    public String imsi;
    public String imei1;
    public String imei2;
    public String softwareVersion;
    public String simSerialNumber;
    public String brand;
    public String model;
    public String operator;
    public String operatorName;
    public String release;
    public String sdkVersion;
    public String versionCode;

    public DeviceItem() {
    }

    public DeviceItem(String deviceId, String imsi, String imei1, String imei2, String softwareVersion, String simSerialNumber, String brand, String model, String operator, String operatorName, String release, String  sdkVersion, String  versionCode) {
        this.deviceId = deviceId;
        this.imsi = imsi;
        this.imei1 = imei1;
        this.imei2 = imei2;
        this.softwareVersion = softwareVersion;
        this.simSerialNumber = simSerialNumber;
        this.brand = brand;
        this.model = model;
        this.operator = operator;
        this.operatorName = operatorName;
        this.release = release;
        this.sdkVersion = sdkVersion;
        this.versionCode = versionCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei1() {
        return imei1;
    }

    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }

    public String getImei2() {
        return imei2;
    }

    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSimSerialNumber() {
        return simSerialNumber;
    }

    public void setSimSerialNumber(String simSerialNumber) {
        this.simSerialNumber = simSerialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String  getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String  sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String  getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String  versionCode) {
        this.versionCode = versionCode;
    }
}
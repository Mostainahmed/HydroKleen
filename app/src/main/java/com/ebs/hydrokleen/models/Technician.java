package com.ebs.hydrokleen.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Technician implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phoneNumber;


    public Technician(String id, String designation, String name,String phoneNumber) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.phoneNumber = phoneNumber;
    }

    protected Technician(Parcel in) {
        id = in.readString();
        designation = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Technician> CREATOR = new Creator<Technician>() {
        @Override
        public Technician createFromParcel(Parcel in) {
            return new Technician(in);
        }

        @Override
        public Technician[] newArray(int size) {
            return new Technician[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(designation);
        dest.writeString(name);
        dest.writeString(phoneNumber);
    }
}

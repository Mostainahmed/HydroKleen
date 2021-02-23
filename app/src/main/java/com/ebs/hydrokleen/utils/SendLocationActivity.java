package com.ebs.hydrokleen.utils;

import android.location.Location;

import org.greenrobot.eventbus.Subscribe;

public class SendLocationActivity {

    private Location location;

    public SendLocationActivity(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

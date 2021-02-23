package com.ebs.hydrokleen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Workqueue implements Serializable {

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("pending")
    @Expose
    private Integer pending;

    @SerializedName("cancelled")
    @Expose
    private Integer cancelled;

    @SerializedName("done")
    @Expose
    private Integer done;

    /**
     * No args constructor for use in serialization
     *
     */
    public Workqueue() {
    }

    /**
     *
     * @param total
     * @param pending
     * @param cancelled
     * @param done
     */
    public Workqueue(Integer total, Integer pending, Integer cancelled, Integer done) {
        super();
        this.total = total;
        this.pending = pending;
        this.cancelled = cancelled;
        this.done = done;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPending() {
        return pending;
    }

    public void setPending(Integer pending) {
        this.pending = pending;
    }

    public Integer getCancelled() {
        return cancelled;
    }

    public void setCancelled(Integer cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }
}

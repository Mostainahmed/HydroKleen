
package com.ebs.hydrokleen.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Amit on 02,April,2020
 * kundu.amit517@gmail.com
 */
public class WorkType {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("worklist")
    @Expose
    private List<ServiceWorkList> worklist = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WorkType() {
    }

    /**
     * 
     * @param worklist
     * @param type
     */
    public WorkType(String type, List<ServiceWorkList> worklist) {
        super();
        this.type = type;
        this.worklist = worklist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ServiceWorkList> getWorkList() {
        return worklist;
    }

    public void setWorkList(List<ServiceWorkList> worklist) {
        this.worklist = worklist;
    }

}

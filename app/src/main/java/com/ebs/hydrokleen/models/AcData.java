
package com.ebs.hydrokleen.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcData {

    @SerializedName("acinfo")
    @Expose
    private Acinfo acinfo;
    @SerializedName("brands")
    @Expose
    private List<String> brands = null;
    @SerializedName("tons")
    @Expose
    private List<String> tons = null;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("techs")
    @Expose
    private List<String> techs = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AcData() {
    }

    /**
     * 
     * @param types
     * @param brands
     * @param tons
     * @param acinfo
     * @param techs
     */
    public AcData(Acinfo acinfo, List<String> brands, List<String> tons, List<String> types, List<String> techs) {
        super();
        this.acinfo = acinfo;
        this.brands = brands;
        this.tons = tons;
        this.types = types;
        this.techs = techs;
    }

    public Acinfo getAcinfo() {
        return acinfo;
    }

    public void setAcinfo(Acinfo acinfo) {
        this.acinfo = acinfo;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public List<String> getTons() {
        return tons;
    }

    public void setTons(List<String> tons) {
        this.tons = tons;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getTechs() {
        return techs;
    }

    public void setTechs(List<String> techs) {
        this.techs = techs;
    }

}

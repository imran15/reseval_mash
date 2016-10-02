/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.stubs.beans;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 *
 * @author Muhammad Imran
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "veneuImpact", propOrder = {
    "name",
    "value",
    "venue",
    "percentile"


})
@XmlRootElement(name="veneuImpact")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ImpactCompotations implements Serializable{
    protected String name;
    protected Double value;
    protected String venue;
    protected Double percentile;

    public ImpactCompotations(String name, Double value, String venue, Double percentile) {
        this.name = name;
        this.value = value;
        this.venue = venue;
        this.percentile = percentile;
    }

    public Double getPercentile() {
        return percentile;
    }

    public void setPercentile(Double percentile) {
        this.percentile = percentile;
    }

    



    public ImpactCompotations() {
    }

    public ImpactCompotations(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

     public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    


    @Override
    protected ImpactCompotations clone() throws CloneNotSupportedException {
        ImpactCompotations m = new ImpactCompotations();
        if(this.name != null) {
            m.name = new String(this.name);
        }
        if(this.value != null){
            m.value = new Double(this.value);
        }

        return m;
    }


}
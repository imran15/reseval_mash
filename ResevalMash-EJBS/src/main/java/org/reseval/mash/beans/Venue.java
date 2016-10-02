/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.beans;


import java.io.Serializable;
import java.util.Date;
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
@XmlType(name = "venue", propOrder = {
    "id",
    "name",
    "sname",
    "type",
    "vclass",   // This is a special attribute for UNITN. It represents class of venue like A, B, or C
    "impactFactor",
    "startDate",
    "endDate",
    "city",
    "country"
})
@XmlRootElement(name="venue")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Venue implements Serializable{
    protected Long id;
    protected String name;
    protected String sname;
    protected String type;
    protected String vClass;
    protected Double impactFactor;
    protected Long startDate;
    protected Long endDate;
    protected String city;
    protected String country;

    public Venue() {
    }

    public Venue(Long id) {
        this.id = id;
    }

    public Venue(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getvClass() {
        return vClass;
    }

    public void setvClass(String vClass) {
        this.vClass = vClass;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Double getImpactFactor() {
        return impactFactor;
    }

    public void setImpactFactor(Double impactFactor) {
        this.impactFactor = impactFactor;
    }

    @Override
    protected Venue clone() throws CloneNotSupportedException {
        Venue m = new Venue();
        if(this.id != null) {
            m.id = new Long(this.id);
        }
        if(this.name != null) {
            m.name = new String(this.name);
        }
        if(this.sname != null) {
            m.sname = new String(this.sname);
        }
        if(this.type != null) {
            m.type = new String(this.type);
        }
        
        if(this.vClass != null) {
            m.vClass = new String(this.vClass);
        }
        if(this.impactFactor != null){
            m.impactFactor = new Double(this.impactFactor);
        }
        if(this.startDate != null) {
            m.startDate = new Long(this.startDate);
        }
        if(this.endDate != null) {
            m.endDate = new Long(this.endDate);
        }
        if(this.city != null) {
            m.city = new String(this.city);
        }
        if(this.country != null) {
            m.country = new String(this.country);
        }
        

        return m;
    }


}
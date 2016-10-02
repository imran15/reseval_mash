/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.beans;


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
@XmlType(name = "sector", propOrder = {
    "id",
    "code",
    "name"
})
@XmlRootElement(name="sector")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Sector implements Serializable{
    protected Long id;
    protected String code;
    protected String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    

    public Sector() {
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

   

    @Override
    protected Sector clone() throws CloneNotSupportedException {
        Sector m = new Sector();
        if(this.name != null) {
            m.name = new String(this.name);
        }
        if(this.id != null){
            m.id = new Long(this.id);
        }
        if(this.code != null){
            m.code = new String(this.code);
        }

        return m;
    }


}
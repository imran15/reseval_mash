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
@XmlType(name = "percentile", propOrder = {
    "name",
    "value"
})
@XmlRootElement(name="percentile")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Percentile implements Serializable{
    protected String name;
    protected Integer value;

    public Percentile() {
    }

    public Percentile(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    protected Percentile clone() throws CloneNotSupportedException {
        Percentile m = new Percentile();
        if(this.name != null) {
            m.name = new String(this.name);
        }
        if(this.value != null){
            m.value = new Integer(this.value);
        }

        return m;
    }


}
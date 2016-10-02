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
@XmlType(name = "rank", propOrder = {
    "id",
    "name"
})
@XmlRootElement(name="rank")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Rank implements Serializable{
    protected Long id;
    protected String name;

    public Rank() {
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
    protected Rank clone() throws CloneNotSupportedException {
        Rank m = new Rank();
        if(this.name != null) {
            m.name = new String(this.name);
        }
        if(this.id != null){
            m.id = new Long(this.id);
        }

        return m;
    }


}
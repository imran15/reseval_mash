/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@XmlType(name = "department", propOrder = {
    "id",
    "name"
})
@XmlRootElement(name = "department")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Department implements Serializable{

    private Long id;
    private String name;
    /**
     * 
     */
    public Department() {
    }

    
    /**
     * 
     * @param id
     * @param name
     */
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    /**
     * 
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }



}

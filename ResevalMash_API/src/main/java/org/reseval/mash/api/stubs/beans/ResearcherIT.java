/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.stubs.beans;
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
@XmlType(name = "researcherit", propOrder = {
    "id",
    "name",
    "surname"
 })
@XmlRootElement(name = "researcherit")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ResearcherIT implements Serializable {

    private Long id;
    private String name;
    private String surname;

    public ResearcherIT() {
    }

    public ResearcherIT(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    


}

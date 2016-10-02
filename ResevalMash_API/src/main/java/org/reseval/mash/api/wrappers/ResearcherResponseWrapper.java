/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.wrappers;

import java.io.Serializable;
import java.util.List;
import org.reseval.mash.beans.QueryMapper;
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
@XmlType(name = "ResearchersWrapper", propOrder = {
    "id",
    "name",
    "universityID",
    "universityName",
    "dblpID",
    "masID",
    "numofPubs"
})
@XmlRootElement(name = "ResearchersWrapper")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ResearcherResponseWrapper implements Serializable {

    private Long id;
    private String name;
    private Long universityId;
    private String universityName;
    private Long dblpID;
    private Long masID;
    private Integer numofPubs;

    public Integer getNumofPubs() {
        return numofPubs;
    }

    public void setNumofPubs(Integer numofPubs) {
        this.numofPubs = numofPubs;
    }
    

    public Long getMasID() {
        return masID;
    }

    public void setMasID(Long masID) {
        this.masID = masID;
    }

    public ResearcherResponseWrapper() {
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

    
   
    /**
     * @return the universityId
     */
    public Long getUniversityId() {
        return universityId;
    }

    /**
     * @param universityId the universityId to set
     */
    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    /**
     * @return the universityName
     */
    public String getUniversityName() {
        return universityName;
    }

    /**
     * @param universityName the universityName to set
     */
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Long getDblpID() {
        return dblpID;
    }

    public void setDblpID(Long dblpID) {
        this.dblpID = dblpID;
    }

 
}

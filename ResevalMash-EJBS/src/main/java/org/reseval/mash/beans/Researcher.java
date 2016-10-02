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
@XmlType(name = "researcher", propOrder = {
    "id",
    "name",
    "surname",
    "fullName",
    "metrics",
    "publications",
    "pubsNumber",
    "coAuthors",
    "citers",
    "dblpID",
    "masID",
    "universityID",
    "departmentID",
    "facultyID",
    "sectorID",
    "rankID"
})
@XmlRootElement(name = "researcher")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Researcher implements Serializable {

    protected Long id;
    protected String name;
    protected String surname;
    protected String fullName;
    protected Metrics metrics;
    protected List<Publication> publications;
    protected Integer pubsNumber;
    protected List<Researcher> coAuthors;
    protected List<Researcher> citers;
    protected Long dblpID;
    protected Long masID;
    protected Long universityID;
    protected Long departmentID;
    protected Long facultyID;
    protected Long sectorID;
    protected Long rankID;
    protected Integer coAuthored_Num;

    public Integer getCoAuthored_Num() {
        return coAuthored_Num;
    }

    public void setCoAuthored_Num(Integer coAuthored_Num) {
        this.coAuthored_Num = coAuthored_Num;
    }

    public Researcher() {
    }

    public Researcher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getDblpID() {
        return dblpID;
    }

    public void setDblpID(Long dblpID) {
        this.dblpID = dblpID;
    }

    public Long getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Long departmentID) {
        this.departmentID = departmentID;
    }

    public Long getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Long facultyID) {
        this.facultyID = facultyID;
    }

    public Long getMasID() {
        return masID;
    }

    public void setMasID(Long masID) {
        this.masID = masID;
    }

    public Long getRankID() {
        return rankID;
    }

    public void setRankID(Long rankID) {
        this.rankID = rankID;
    }

    public Long getSectorID() {
        return sectorID;
    }

    public void setSectorID(Long sectorID) {
        this.sectorID = sectorID;
    }

    public Long getUniversityID() {
        return universityID;
    }

    public void setUniversityID(Long universityID) {
        this.universityID = universityID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Researcher(Long id, String name, Integer publications) {
        this.id = id;
        this.name = name;
        this.pubsNumber = publications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metrics getMetrics() {
        if (metrics == null) {
            metrics = new Metrics();
        }
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Publication> getPublications() {
//        if (publications == null) {
//            publications = new ArrayList<Publication>();
//        }
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public Integer getPubsNumber() {
        return pubsNumber;
    }

    public void setPubsNumber(Integer pubsNumber) {
        this.pubsNumber = pubsNumber;
    }

    public List<Researcher> getCiters() {
        if (citers == null) {
            citers = new ArrayList<Researcher>();
        }
        return citers;
    }

    public void setCiters(List<Researcher> citers) {
        this.citers = citers;
    }

    public List<Researcher> getCoAuthors() {
        if (coAuthors == null) {
            coAuthors = new ArrayList<Researcher>();
        }
        return coAuthors;
    }

    public void setCoAuthors(List<Researcher> coauthors) {
        this.coAuthors = coauthors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Researcher other = (Researcher) obj;

        if (other.id == null || !other.id.equals(this.id)) {
            return false;
        } else if (other.id.equals(this.id)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }

    @Override
    public Researcher clone() throws CloneNotSupportedException {
        Researcher res = new Researcher();
        if (this.id != null) {
            res.id = new Long(this.id);
        }
        if (this.name != null) {
            res.name = new String(this.name);
        }
        if (this.surname != null) {
            res.surname = new String(this.surname);
        }
        if (this.fullName != null) {
            res.fullName = new String(this.fullName);
        }
        if (this.metrics != null) {
            res.metrics = this.metrics.clone();
        }
        if (this.publications != null) {
            for (Publication p : this.publications) {
                res.getPublications().add(p.clone());
            }
        }
        if (this.pubsNumber != null) {
            res.pubsNumber = new Integer(this.pubsNumber);
        }
        if (this.coAuthors != null) {
            List<Researcher> resCoauthors = res.getCoAuthors();
            for (Researcher r : this.coAuthors) {
                resCoauthors.add(r.clone());
            }
        }
        if (this.citers != null) {
            List<Researcher> resCiters = res.getCiters();
            for (Researcher r : this.citers) {
                resCiters.add(r.clone());
            }
        }
        if (this.dblpID != null) {
            res.dblpID = new Long(this.dblpID);
        }

        if (this.masID != null) {
            res.masID = new Long(this.masID);
        }

        if (this.universityID != null) {
            res.universityID = new Long(this.universityID);
        }

        if (this.departmentID != null) {
            res.departmentID = new Long(this.departmentID);
        }
        if (this.facultyID != null) {
            res.facultyID = new Long(this.facultyID);
        }

        if (this.rankID != null) {
            res.rankID = new Long(this.rankID);
        }

        if (this.sectorID != null) {
            res.sectorID = new Long(this.sectorID);
        }

        if (this.coAuthored_Num !=null){
            res.coAuthored_Num = new Integer(this.coAuthored_Num);
        }

        return res;

    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

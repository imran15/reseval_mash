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
@XmlType(name = "university", propOrder = {
    "id",
    "name",
    "website",
    "street",
    "zip",
    "city",
    "region"
})
@XmlRootElement(name = "university")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class University implements Serializable{
    private Long id;
    private String name;
    private String website;
    private String street;
    private Long zip;
    private String city;
    private String region;
    private List<Department> departments;
    private List<Researcher> researchers;
    private List<Faculty> faculties;

    public University() {
    }

    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return the departments
     */
    public List<Department> getDepartments() {
         if (departments == null) {
            departments = new ArrayList<Department>();
        }
        return departments;
      }

    /**
     * @param departments the departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * @return the researchers
     */
    public List<Researcher> getResearchers() {
        if (researchers == null){
            researchers = new ArrayList<Researcher>();
        }
        return researchers;
    }

    /**
     * @param researchers the researchers to set
     */
    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    /**
     * @return the faculties
     */
    public List<Faculty> getFaculties() {
        if (faculties == null){
            faculties = new ArrayList<Faculty>();
        }
            
        return faculties;
    }

    /**
     * @param faculties the faculties to set
     */
    public void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    /**
     * @return the zip
     */
    public Long getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(Long zip) {
        this.zip = zip;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }



  /*  @Override
    protected University clone() throws CloneNotSupportedException {
        University c = new University();
        if (this.getId() != null) {
            c.id = new Long(this.getId());
        }
        if (this.getName() != null) {
            c.name = new String(this.getName());
        }
        if (this.groups != null) {
            List<Group> cGroups = c.getGroups();
            for (Group g : this.groups) {
                cGroups.add(g.clone());
            }
        }

        return c;
    }*/
}

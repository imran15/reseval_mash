/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Muhammad Imran
 */
@Entity
@Table(name = "UNIVERSITIES")
@NamedQueries({
    @NamedQuery(name = "Universities.findAll", query = "SELECT u FROM Universities u")})
public class Universities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "U_ID_PK")
    private BigDecimal uIdPk;
    @Column(name = "U_NAME")
    private String uName;
    @Column(name = "U_WEB_SITE")
    private String uWebSite;
    @Column(name = "U_STREET")
    private String uStreet;
    @Column(name = "U_ZIP")
    private BigInteger uZip;
    @Column(name = "U_CITY")
    private String uCity;
    @Column(name = "U_REGION")
    private String uRegion;
    @OneToMany(mappedBy = "universities")
    private Collection<Departments> departmentsCollection;
    @OneToMany(mappedBy = "universities")
    private Collection<Faculties> facultiesCollection;
    @OneToMany(mappedBy = "universities")
    private Collection<Researchers> researchersCollection;

    public Universities() {
    }

    public Universities(BigDecimal uIdPk) {
        this.uIdPk = uIdPk;
    }

    public BigDecimal getUIdPk() {
        return uIdPk;
    }

    public void setUIdPk(BigDecimal uIdPk) {
        this.uIdPk = uIdPk;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUWebSite() {
        return uWebSite;
    }

    public void setUWebSite(String uWebSite) {
        this.uWebSite = uWebSite;
    }

    public String getUStreet() {
        return uStreet;
    }

    public void setUStreet(String uStreet) {
        this.uStreet = uStreet;
    }

    public BigInteger getUZip() {
        return uZip;
    }

    public void setUZip(BigInteger uZip) {
        this.uZip = uZip;
    }

    public String getUCity() {
        return uCity;
    }

    public void setUCity(String uCity) {
        this.uCity = uCity;
    }

    public String getURegion() {
        return uRegion;
    }

    public void setURegion(String uRegion) {
        this.uRegion = uRegion;
    }

    public Collection<Departments> getDepartmentsCollection() {
        return departmentsCollection;
    }

    public void setDepartmentsCollection(Collection<Departments> departmentsCollection) {
        this.departmentsCollection = departmentsCollection;
    }

    public Collection<Faculties> getFacultiesCollection() {
        return facultiesCollection;
    }

    public void setFacultiesCollection(Collection<Faculties> facultiesCollection) {
        this.facultiesCollection = facultiesCollection;
    }

    public Collection<Researchers> getResearchersCollection() {
        return researchersCollection;
    }

    public void setResearchersCollection(Collection<Researchers> researchersCollection) {
        this.researchersCollection = researchersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uIdPk != null ? uIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universities)) {
            return false;
        }
        Universities other = (Universities) object;
        if ((this.uIdPk == null && other.uIdPk != null) || (this.uIdPk != null && !this.uIdPk.equals(other.uIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Universities[uIdPk=" + uIdPk + "]";
    }

}

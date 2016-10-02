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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Muhammad Imran
 */
@Entity
@Table(name = "FACULTIES")
@NamedQueries({
    @NamedQuery(name = "Faculties.findAll", query = "SELECT f FROM Faculties f")})
public class Faculties implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "F_ID_PK")
    private BigDecimal fIdPk;
    @Column(name = "F_NAME")
    private String fName;
    @Column(name = "F_STREET")
    private String fStreet;
    @Column(name = "F_ZIP")
    private BigInteger fZip;
    @Column(name = "F_CITY")
    private String fCity;
    @JoinColumn(name = "F_U_ID", referencedColumnName = "U_ID_PK")
    @ManyToOne
    private Universities universities;
    @OneToMany(mappedBy = "faculties")
    private Collection<Researchers> researchersCollection;

    public Faculties() {
    }

    public Faculties(BigDecimal fIdPk) {
        this.fIdPk = fIdPk;
    }

    public BigDecimal getFIdPk() {
        return fIdPk;
    }

    public void setFIdPk(BigDecimal fIdPk) {
        this.fIdPk = fIdPk;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFStreet() {
        return fStreet;
    }

    public void setFStreet(String fStreet) {
        this.fStreet = fStreet;
    }

    public BigInteger getFZip() {
        return fZip;
    }

    public void setFZip(BigInteger fZip) {
        this.fZip = fZip;
    }

    public String getFCity() {
        return fCity;
    }

    public void setFCity(String fCity) {
        this.fCity = fCity;
    }

    public Universities getUniversities() {
        return universities;
    }

    public void setUniversities(Universities universities) {
        this.universities = universities;
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
        hash += (fIdPk != null ? fIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faculties)) {
            return false;
        }
        Faculties other = (Faculties) object;
        if ((this.fIdPk == null && other.fIdPk != null) || (this.fIdPk != null && !this.fIdPk.equals(other.fIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Faculties[fIdPk=" + fIdPk + "]";
    }

}

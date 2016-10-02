/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "DEPARTMENTS")
@NamedQueries({
    @NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d")})
public class Departments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "D_ID_PK")
    private BigDecimal dIdPk;
    @Column(name = "D_NAME")
    private String dName;
    @JoinColumn(name = "D_U_ID", referencedColumnName = "U_ID_PK")
    @ManyToOne
    private Universities universities;
    @OneToMany(mappedBy = "departments")
    private Collection<Researchers> researchersCollection;

    public Departments() {
    }

    public Departments(BigDecimal dIdPk) {
        this.dIdPk = dIdPk;
    }

    public BigDecimal getDIdPk() {
        return dIdPk;
    }

    public void setDIdPk(BigDecimal dIdPk) {
        this.dIdPk = dIdPk;
    }

    public String getDName() {
        return dName;
    }

    public void setDName(String dName) {
        this.dName = dName;
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
        hash += (dIdPk != null ? dIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.dIdPk == null && other.dIdPk != null) || (this.dIdPk != null && !this.dIdPk.equals(other.dIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Departments[dIdPk=" + dIdPk + "]";
    }

}

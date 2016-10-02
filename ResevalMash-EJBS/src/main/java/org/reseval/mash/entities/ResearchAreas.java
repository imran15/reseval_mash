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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Muhammad Imran
 */
@Entity
@Table(name = "RESEARCH_AREAS")
@NamedQueries({
    @NamedQuery(name = "ResearchAreas.findAll", query = "SELECT r FROM ResearchAreas r")})
public class ResearchAreas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RA_ID_PK")
    private BigDecimal raIdPk;
    @Column(name = "RA_NAME")
    private String raName;
    @OneToMany(mappedBy = "researchAreas")
    private Collection<Sectors> sectorsCollection;

    public ResearchAreas() {
    }

    public ResearchAreas(BigDecimal raIdPk) {
        this.raIdPk = raIdPk;
    }

    public BigDecimal getRaIdPk() {
        return raIdPk;
    }

    public void setRaIdPk(BigDecimal raIdPk) {
        this.raIdPk = raIdPk;
    }

    public String getRaName() {
        return raName;
    }

    public void setRaName(String raName) {
        this.raName = raName;
    }

    public Collection<Sectors> getSectorsCollection() {
        return sectorsCollection;
    }

    public void setSectorsCollection(Collection<Sectors> sectorsCollection) {
        this.sectorsCollection = sectorsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raIdPk != null ? raIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResearchAreas)) {
            return false;
        }
        ResearchAreas other = (ResearchAreas) object;
        if ((this.raIdPk == null && other.raIdPk != null) || (this.raIdPk != null && !this.raIdPk.equals(other.raIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.ResearchAreas[raIdPk=" + raIdPk + "]";
    }

}

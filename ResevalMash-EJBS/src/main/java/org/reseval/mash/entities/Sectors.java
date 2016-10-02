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
@Table(name = "SECTORS")
@NamedQueries({
    @NamedQuery(name = "Sectors.findAll", query = "SELECT s FROM Sectors s")})
public class Sectors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "S_ID_PK")
    private BigDecimal sIdPk;
    @Column(name = "S_CODE")
    private String sCode;
    @Column(name = "S_NAME")
    private String sName;
    @OneToMany(mappedBy = "sectors")
    private Collection<Researchers> researchersCollection;
    @JoinColumn(name = "S_RA_ID", referencedColumnName = "RA_ID_PK")
    @ManyToOne
    private ResearchAreas researchAreas;

    public Sectors() {
    }

    public Sectors(BigDecimal sIdPk) {
        this.sIdPk = sIdPk;
    }

    public BigDecimal getSIdPk() {
        return sIdPk;
    }

    public void setSIdPk(BigDecimal sIdPk) {
        this.sIdPk = sIdPk;
    }

    public String getSCode() {
        return sCode;
    }

    public void setSCode(String sCode) {
        this.sCode = sCode;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public Collection<Researchers> getResearchersCollection() {
        return researchersCollection;
    }

    public void setResearchersCollection(Collection<Researchers> researchersCollection) {
        this.researchersCollection = researchersCollection;
    }

    public ResearchAreas getResearchAreas() {
        return researchAreas;
    }

    public void setResearchAreas(ResearchAreas researchAreas) {
        this.researchAreas = researchAreas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sIdPk != null ? sIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sectors)) {
            return false;
        }
        Sectors other = (Sectors) object;
        if ((this.sIdPk == null && other.sIdPk != null) || (this.sIdPk != null && !this.sIdPk.equals(other.sIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Sectors[sIdPk=" + sIdPk + "]";
    }

}

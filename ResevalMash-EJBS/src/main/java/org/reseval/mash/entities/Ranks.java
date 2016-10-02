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
@Table(name = "RANKS")
@NamedQueries({
    @NamedQuery(name = "Ranks.findAll", query = "SELECT r FROM Ranks r")})
public class Ranks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "R_ID_PK")
    private BigDecimal rIdPk;
    @Column(name = "R_NAME")
    private String rName;
    @OneToMany(mappedBy = "ranks")
    private Collection<Researchers> researchersCollection;

    public Ranks() {
    }

    public Ranks(BigDecimal rIdPk) {
        this.rIdPk = rIdPk;
    }

    public BigDecimal getRIdPk() {
        return rIdPk;
    }

    public void setRIdPk(BigDecimal rIdPk) {
        this.rIdPk = rIdPk;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
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
        hash += (rIdPk != null ? rIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ranks)) {
            return false;
        }
        Ranks other = (Ranks) object;
        if ((this.rIdPk == null && other.rIdPk != null) || (this.rIdPk != null && !this.rIdPk.equals(other.rIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Ranks[rIdPk=" + rIdPk + "]";
    }

}

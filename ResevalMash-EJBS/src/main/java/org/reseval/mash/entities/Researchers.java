/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Muhammad Imran
 */
@Entity
@Table(name = "RESEARCHERS")
@NamedQueries({
    @NamedQuery(name = "Researchers.findAll", query = "SELECT r FROM Researchers r")})
public class Researchers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RSC_ID_PK")
    private BigDecimal rscIdPk;
    @Column(name = "RSC_NAME")
    private String rscName;
    @Column(name = "RSC_SURNAME")
    private String rscSurname;
    @JoinColumn(name = "RSC_U_ID", referencedColumnName = "U_ID_PK")
    @ManyToOne
    private Universities universities;
    @JoinColumn(name = "RSC_S_ID", referencedColumnName = "S_ID_PK")
    @ManyToOne
    private Sectors sectors;
    @JoinColumn(name = "RSC_R_ID", referencedColumnName = "R_ID_PK")
    @ManyToOne
    private Ranks ranks;
    @JoinColumn(name = "RSC_F_ID", referencedColumnName = "F_ID_PK")
    @ManyToOne
    private Faculties faculties;
    @JoinColumn(name = "RSC_D_ID", referencedColumnName = "D_ID_PK")
    @ManyToOne
    private Departments departments;

    public Researchers() {
    }

    public Researchers(BigDecimal rscIdPk) {
        this.rscIdPk = rscIdPk;
    }

    public BigDecimal getRscIdPk() {
        return rscIdPk;
    }

    public void setRscIdPk(BigDecimal rscIdPk) {
        this.rscIdPk = rscIdPk;
    }

    public String getRscName() {
        return rscName;
    }

    public void setRscName(String rscName) {
        this.rscName = rscName;
    }

    public String getRscSurname() {
        return rscSurname;
    }

    public void setRscSurname(String rscSurname) {
        this.rscSurname = rscSurname;
    }

    public Universities getUniversities() {
        return universities;
    }

    public void setUniversities(Universities universities) {
        this.universities = universities;
    }

    public Sectors getSectors() {
        return sectors;
    }

    public void setSectors(Sectors sectors) {
        this.sectors = sectors;
    }

    public Ranks getRanks() {
        return ranks;
    }

    public void setRanks(Ranks ranks) {
        this.ranks = ranks;
    }

    public Faculties getFaculties() {
        return faculties;
    }

    public void setFaculties(Faculties faculties) {
        this.faculties = faculties;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rscIdPk != null ? rscIdPk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Researchers)) {
            return false;
        }
        Researchers other = (Researchers) object;
        if ((this.rscIdPk == null && other.rscIdPk != null) || (this.rscIdPk != null && !this.rscIdPk.equals(other.rscIdPk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.reseval.resevalmashejbs.Researchers[rscIdPk=" + rscIdPk + "]";
    }

}

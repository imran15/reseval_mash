/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.beans;

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
 * @author miki
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "researcher", propOrder = {
    "id",
    "name",
    "metrics",
    "publications",
    "pubsNumber",
    "coAuthors",
    "citers"
})
@XmlRootElement(name = "researcher")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ResearcherLong implements Serializable {

    protected Long id;
    protected String name;
    protected Metrics metrics;
    protected List<Publication> publications;
    protected Integer pubsNumber;
    protected List<ResearcherLong> coAuthors;
    protected List<ResearcherLong> citers;

    public ResearcherLong() {
    }

    public ResearcherLong(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ResearcherLong(Long id, String name, Integer publications) {
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
        if (publications == null) {
            publications = new ArrayList<Publication>();
        }
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

    public List<ResearcherLong> getCiters() {
        if (citers == null) {
            citers = new ArrayList<ResearcherLong>();
        }
        return citers;
    }

    public void setCiters(List<ResearcherLong> citers) {
        this.citers = citers;
    }

    public List<ResearcherLong> getCoAuthors() {
        if (coAuthors == null) {
            coAuthors = new ArrayList<ResearcherLong>();
        }
        return coAuthors;
    }

    public void setCoAuthors(List<ResearcherLong> coauthors) {
        this.coAuthors = coauthors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        ResearcherLong other = (ResearcherLong) obj;

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
    public ResearcherLong clone() throws CloneNotSupportedException {
        ResearcherLong res = new ResearcherLong();
        if (this.id != null) {
            res.id = new Long(this.id);
        }
        if (this.name != null) {
            res.name = new String(this.name);
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
            List<ResearcherLong> resCoauthors = res.getCoAuthors();
            for (ResearcherLong r : this.coAuthors) {
                resCoauthors.add(r.clone());
            }
        }
        if (this.citers != null) {
            List<ResearcherLong> resCiters = res.getCiters();
            for (ResearcherLong r : this.citers) {
                resCiters.add(r.clone());
            }
        }
        return res;

    }
}

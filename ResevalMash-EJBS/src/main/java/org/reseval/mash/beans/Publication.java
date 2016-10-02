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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 *
 * @author Muhammad Imran
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "publication", propOrder = {
    "id",
    "title",
    "citations",
    "year",
    "venue",
    "uri",
    "excluded",
    "coAuthors"
})
@XmlRootElement(name = "publication")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class Publication implements Serializable {

    protected Long id;
    protected String title;
    protected Integer citations;
    @XmlTransient
    protected Integer withoutSelfCitations;
    protected Long year;
    protected Venue venue;
    protected String uri;
    protected Integer excluded;
    protected List<Researcher> coAuthors;

    public Long getId() {
        return id;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

   

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCitations() {
        return citations;
    }

    public void setCitations(Integer citations) {
        this.citations = citations;
    }

    public Integer getWithoutSelfCitations() {
        return withoutSelfCitations;
    }

    public void setWithoutSelfCitations(Integer withoutSelfCitations) {
        this.withoutSelfCitations = withoutSelfCitations;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long date) {
        this.year = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getExcluded() {
        return excluded;
    }

    public void setExcluded(Integer excluded) {
        this.excluded = excluded;
    }

    public List<Researcher> getCoAuthors() {
        if (coAuthors == null) {
            coAuthors = new ArrayList<Researcher>();
        }
        return coAuthors;
    }

    public void setCoAuthors(List<Researcher> coAuthors) {
        this.coAuthors = coAuthors;
    }

    @Override
    protected Publication clone() throws CloneNotSupportedException {
        Publication p = new Publication();
        if (this.id != null) {
            p.id = new Long(this.id);
        }
        if (this.title != null) {
            p.title = new String(this.title);
        }
        if (this.citations != null) {
            p.citations = new Integer(this.citations);
        }
        if (this.withoutSelfCitations != null) {
            p.withoutSelfCitations = new Integer(this.withoutSelfCitations);
        }
        if (this.year != null) {
            p.year = new Long(this.year);
        }
         if (this.venue != null) {
            p.venue = this.venue.clone();
        }
        if (this.uri != null) {
            p.uri = new String(this.uri);
        }
        if (this.excluded != null) {
            p.excluded = new Integer(this.excluded);
        }
        if (this.coAuthors != null) {
            List<Researcher> pCoauthors = p.getCoAuthors();
            for (Researcher r : this.coAuthors) {
                pCoauthors.add(r.clone());
            }
        }

        return p;
    }
}

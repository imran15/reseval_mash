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
@XmlType(name = "metrics", propOrder = {
    "metricsStandard",
    "metricsWithoutSelfCitations",
    "reputationBased",
    "keynoteCount"
})
@XmlRootElement(name = "metrics")
public class Metrics implements Serializable {

    protected ArrayList<Metric> metricsStandard;
    protected ArrayList<Metric> metricsWithoutSelfCitations;
    protected ArrayList<Metric> reputationBased;
    protected Integer keynoteCount;

    public ArrayList<Metric> getMetricsStandard() {
        if (metricsStandard == null) {
            metricsStandard = new ArrayList<Metric>();
        }
        return metricsStandard;
    }

    public void setMetricsStandard(ArrayList<Metric> metricsStandard) {
        this.metricsStandard = metricsStandard;
    }

    public ArrayList<Metric> getMetricsWithoutSelfCitations() {
        if (metricsWithoutSelfCitations == null) {
            metricsWithoutSelfCitations = new ArrayList<Metric>();
        }
        return metricsWithoutSelfCitations;
    }

    public void setMetricsWithoutSelfCitations(ArrayList<Metric> metricsWithoutSelfCitations) {
        this.metricsWithoutSelfCitations = metricsWithoutSelfCitations;
    }

    public ArrayList<Metric> getReputationBased() {
        if (reputationBased == null) {
            reputationBased = new ArrayList<Metric>();
        }
        return reputationBased;
    }

    public void setReputationBased(ArrayList<Metric> reputationBased) {
        this.reputationBased = reputationBased;
    }

    public Integer getKeynoteCount() {
        if(keynoteCount == null) {
            keynoteCount = new Integer(0);
        }
        return keynoteCount;
    }

    public void setKeynoteCount(Integer keynoteCount) {
        this.keynoteCount = keynoteCount;
    }

    @Override
    protected Metrics clone() throws CloneNotSupportedException {
        Metrics m = new Metrics();
        if (this.metricsStandard != null) {
            List<Metric> ms = m.getMetricsStandard();
            for (Metric mc : this.metricsStandard) {
                ms.add(mc.clone());
            }
        }
        if (this.metricsWithoutSelfCitations != null) {
            List<Metric> mwsc = m.getMetricsWithoutSelfCitations();
            for (Metric mc : this.metricsWithoutSelfCitations) {
                mwsc.add(mc.clone());
            }
        }
        if (this.reputationBased != null) {
            List<Metric> mwsc = m.getReputationBased();
            for (Metric mc : this.reputationBased) {
                mwsc.add(mc.clone());
            }
        }
        if(this.keynoteCount != null) {
            m.keynoteCount = new Integer(this.keynoteCount);
        }
        return m;
    }
}

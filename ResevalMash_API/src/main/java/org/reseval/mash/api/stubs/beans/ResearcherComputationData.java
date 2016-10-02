/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Willy
 */
public class ResearcherComputationData implements Serializable {

    private ResearcherLong researcher;
    private HashMap<Long, Integer> citations;
    private HashMap<Long, Integer> withoutSelfCitations;

    public ResearcherLong getResearcher() {
        if (researcher == null) {
            researcher = new ResearcherLong();
        }
        return researcher;
    }

    public void setResearcher(ResearcherLong researcher) {
        this.researcher = researcher;
    }

    public HashMap<Long, Integer> getCitations() {
        if (citations == null) {
            citations = new HashMap<Long, Integer>();
        }
        return citations;
    }

    public void setCitations(HashMap<Long, Integer> citations) {
        this.citations = citations;
    }

    public HashMap<Long, Integer> getWithoutSelfCitations() {
        if (withoutSelfCitations == null) {
            withoutSelfCitations = new HashMap<Long, Integer>();
        }
        return withoutSelfCitations;
    }

    public void setWithoutSelfCitations(HashMap<Long, Integer> withoutSelfCitations) {
        this.withoutSelfCitations = withoutSelfCitations;
    }

    @Override
    public ResearcherComputationData clone() throws CloneNotSupportedException {
        ResearcherComputationData rcd = new ResearcherComputationData();
        if (this.researcher != null) {
            rcd.researcher = this.researcher.clone();
        }
        if (this.citations != null) {
            for (Entry<Long, Integer> e : this.citations.entrySet()) {
                rcd.getCitations().put(new Long(e.getKey()), new Integer(e.getValue()));
            }
        }
        if (this.withoutSelfCitations != null) {
            for (Entry<Long, Integer> e : this.withoutSelfCitations.entrySet()) {
                rcd.getWithoutSelfCitations().put(new Long(e.getKey()), new Integer(e.getValue()));
            }
        }
        return rcd;
    }
}

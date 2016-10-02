package org.reseval.mash.api.facades.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.facades.CitationLogicLocal;

import org.reseval.mash.beans.Metric;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.facades.remote.CitationSourceFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

@Stateless
public class CitationLogicImp implements CitationLogicLocal {

    @EJB
    private CitationSourceFacadeRemote _citationSourceRemote;

    @Override
    public ResearchersWrapper getSelfCitations(ResearchersWrapper resWrapper) {
        return _citationSourceRemote.getSelfCitations(resWrapper);
    }

    @Override
    public ResearchersWrapper getCitations(ResearchersWrapper resWrapper) {
        List<Researcher> resList = resWrapper.getResearchers();
        
        for(int i=0;i<resList.size();i++){
            
            Integer citationCount = countCitation(resList.get(i));
            Metric m = new Metric();
                m.setName("Citations");
                m.setValue(new BigDecimal(citationCount));
                if (resList.get(i).getMetrics().getMetricsStandard().isEmpty()) {
                    resList.get(i).getMetrics().getMetricsStandard().add(m);
                } else {
                    resList.get(i).getMetrics().setMetricsStandard(checkMetric(resList.get(i).getMetrics().getMetricsStandard(), m));
                }
        }
        
        resWrapper.setResearchers(resList);
        
        return resWrapper;
        
    }
    
    private Integer countCitation(Researcher res){
        Integer citations=0;
        List<Publication> pubList= res.getPublications();
        for (int i=0;i<pubList.size();i++){
            if(pubList.get(i).getCitations() != null)
            citations += pubList.get(i).getCitations();
            
        }
        return citations;
    }
    
    private ArrayList<Metric> checkMetric(ArrayList<Metric> metrics, Metric m) {
        if (metrics != null) {
            for (int i = 0; i < metrics.size(); i++) {
                if (metrics.get(i).getName().equals(m.getName())) {
                    metrics.remove(i);
                    metrics.add(m);
                    return metrics;
                }

            }
            metrics.add(m);
        }
        return metrics;

    }
}

package org.reseval.mash.api.facades.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import org.reseval.mash.api.facades.HIndexLogicLocal;

import org.reseval.mash.beans.Metric;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

@Stateless
public class HIndexLogicImp implements HIndexLogicLocal {

    public int compute_H_index(HashMap<Long, Integer> citations) {
        int pNumb = citations.size();
        Integer[] cit = citations.values().toArray(new Integer[0]);
        int i = 0;
        Arrays.sort(cit, Collections.reverseOrder());
        for (; i < pNumb; i++) {
            if (cit[i] < i + 1) {
                break;
            }
        }
        return i;
    }

    private ResearchersWrapper getHIndexForResearchers(ResearchersWrapper resWrapper) {
        List<Researcher> researchersList = resWrapper.getResearchers();
        HashMap<Long, Integer> pubCitations = new HashMap<Long, Integer>();
        int count = 0;
        for (Researcher researcher : researchersList) {
            int hIndex = 0;
            if ((researcher.getPublications() != null)) {
                List<Publication> publicationList = researcher.getPublications();
                for (Publication publication : publicationList) {
                    pubCitations.put(publication.getId(), publication.getCitations());

                }
                hIndex = compute_H_index(pubCitations);
                pubCitations.clear();
            }
            Metric m = new Metric();
            m.setName("H-Index");
            m.setValue(new BigDecimal(hIndex));
            if (resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard().isEmpty()) {
                resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard().add(m);
            } else {
                resWrapper.getResearchers().get(count).getMetrics().setMetricsStandard(checkMetric(resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard(), m));
            }
            count++;
        }
        return resWrapper;
    }

    private ResearchersWrapper getHIndexForCoAuthors(ResearchersWrapper resWrapper) {
        List<Researcher> researchersList = resWrapper.getResearchers();
        List<Researcher> finalResearcherList= new ArrayList<Researcher>();
        HashMap<Long, Integer> pubCitations = new HashMap<Long, Integer>();
        int count = 0;
        for (Researcher researcher : researchersList) {
            List<Researcher> coAuthors = researcher.getCoAuthors();
            for (Researcher coAuthor : coAuthors) {
                int hIndex = 0;
                if ((coAuthor.getPublications() != null)) {
                    List<Publication> publicationList = coAuthor.getPublications();
                    for (Publication publication : publicationList) {
                        pubCitations.put(publication.getId(), publication.getCitations());

                    }
                    hIndex = compute_H_index(pubCitations);
                    pubCitations.clear();
                }
                Metric m = new Metric();
                m.setName("H-Index");
                m.setValue(new BigDecimal(hIndex));
                if (coAuthors.get(count).getMetrics().getMetricsStandard().isEmpty()) {
                    coAuthors.get(count).getMetrics().getMetricsStandard().add(m);
                } else {
                    coAuthors.get(count).getMetrics().setMetricsStandard(checkMetric(coAuthors.get(count).getMetrics().getMetricsStandard(), m));
                }
                count++;
            }
            researcher.setCoAuthors(coAuthors);
            finalResearcherList.add(researcher);
        }
        resWrapper.setResearchers(finalResearcherList);
        return resWrapper;
    }

    @Override
    public ResearchersWrapper getHIndex(ResearchersWrapper resWrapper) {

        if (resWrapper != null) {
            List<Researcher> researchersList = resWrapper.getResearchers();

            if (researchersList.get(0).getCoAuthors().isEmpty()) {
                // get publications for normal researchers
                 resWrapper = getHIndexForResearchers(resWrapper);
               
            } else {
                //get publications for co-authors
                resWrapper = getHIndexForCoAuthors(resWrapper);
            }

//            HashMap<Long, Integer> pubCitations = new HashMap<Long, Integer>();
//            int count = 0;
//            for (Researcher researcher : researchersList) {
//                int hIndex=0;
//                if ((researcher.getPublications() != null) ) {
//                    List<Publication> publicationList = researcher.getPublications();
//                    for (Publication publication : publicationList) {
//                        pubCitations.put(publication.getId(), publication.getCitations());
//
//                    }
//                    hIndex = compute_H_index(pubCitations);
//                    pubCitations.clear();
//                }
//                    Metric m = new Metric();
//                    m.setName("H-Index");
//                    m.setValue(new BigDecimal(hIndex));
//                    if (resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard().isEmpty()) {
//                        resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard().add(m);
//                    } else {
//                        resWrapper.getResearchers().get(count).getMetrics().setMetricsStandard(checkMetric(resWrapper.getResearchers().get(count).getMetrics().getMetricsStandard(), m));
//                    }
//                    count++;
//            }
        }
        return resWrapper;
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

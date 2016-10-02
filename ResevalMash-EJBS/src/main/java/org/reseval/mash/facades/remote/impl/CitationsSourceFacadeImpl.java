/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.facades.remote.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.reseval.mash.beans.Metric;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.facades.remote.CitationSourceFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class CitationsSourceFacadeImpl implements CitationSourceFacadeRemote {

    @PersistenceContext(unitName = "com.reseval_ResevalMash-EJBS_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-Test")
    private EntityManager emTest;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-TestSUSAN")
    private EntityManager emTest_SUSAN;

    @Override
    public ResearchersWrapper getSelfCitations(ResearchersWrapper resWrapper) {
        List<Researcher> resList = resWrapper.getResearchers();
        for (int i = 0; i < resList.size(); i++) {

            if (resList.get(i).getId() != null) {
                Query q = emTest_SUSAN.createNativeQuery("SELECT sum(self_citation_count),author_group_id  "
                        + " FROM mv_self_citation WHERE author_group_id = :author_id group by author_group_id");
                q.setParameter("author_id", resList.get(i).getId());
                List<Object[]> queryRes = q.getResultList();
                for (Object[] row : queryRes) {
                    BigDecimal self_citation_count = (((BigDecimal) row[0]));
                    Metric m = new Metric();
                    m.setName("Self-Citation");
                    m.setValue(self_citation_count);
                    if (resList.get(i).getMetrics().getMetricsStandard().isEmpty()) {
                        resList.get(i).getMetrics().getMetricsStandard().add(m);
                    } else {
                        resList.get(i).getMetrics().setMetricsStandard(checkMetric(resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard(), m));
                    }
                }
            }
        }
        resWrapper.setResearchers(resList);
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

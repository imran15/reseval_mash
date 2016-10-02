/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.facades.remote.impl;

import org.reseval.mash.beans.Researcher;

import org.reseval.mash.facades.remote.ResearchersFacadeRemote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class ResearchersFacadeImpl implements ResearchersFacadeRemote {

    @PersistenceContext(unitName = "com.reseval_ResevalMash-EJBS_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-Test")
    private EntityManager emTest;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-TestSUSAN")
    private EntityManager emTest_SUSAN;

    @Override
    public List<Researcher> getAllResearchers(Integer no_of_res) {
        List<Researcher> res = new ArrayList<Researcher>();
        Query q = em.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
                + " from " + StaticVars.ITResearchers_Researchers_TABLE);
        q.setMaxResults(no_of_res);
        List<Object[]> queryRes = q.getResultList();
        Researcher researcher;
        for (Object[] row : queryRes) {
            researcher = new Researcher();
            researcher.setId(((BigDecimal) row[0]).longValue());
            researcher.setName((String) row[1]);
            researcher.setSurname((String) row[2]);
            res.add(researcher);
        }
        //addResults(queryRes, res);
        return res;

    }

    @Override
    public List<Researcher> getCiters(ResearchersWrapper researchers) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Researcher> getMASCoAuthors(ResearchersWrapper researchers) {
        List<Researcher> resList = researchers.getResearchers();
        for (int i = 0; i < resList.size(); i++) {

            Query q = emTest_SUSAN.createNativeQuery("SELECT coauthor_group_id, coauthor_name, n_coauthored_resource"
                    + " FROM MV_RESEVAL_COAUTHORS where coauthor_source = 010 AND author_group_id = :author_mas_id");
            q.setParameter("author_mas_id", resList.get(i).getMasID());
            //q.setMaxResults(no_of_res);
            List<Object[]> queryRes = q.getResultList();
            List<Researcher> coAuthorsList = new ArrayList<Researcher>();
            for (Object[] row : queryRes) {
                Researcher researcher = new Researcher();
                researcher.setId(((BigDecimal) row[0]).longValue());
                researcher.setName((String) row[1]);
                researcher.setCoAuthored_Num(((BigDecimal) row[2]).intValue());
                researcher.setMasID(((BigDecimal) row[0]).longValue());
                coAuthorsList.add(researcher);
            }
            resList.get(i).setCoAuthors(coAuthorsList);
        }
        return resList;

    }

    @Override
    public List<Researcher> getDBLPCoAuthors(ResearchersWrapper researchers) {
         List<Researcher> resList = researchers.getResearchers();
        for (int i = 0; i < resList.size(); i++) {

            Query q = emTest_SUSAN.createNativeQuery("SELECT coauthor_group_id, coauthor_name, n_coauthored_resource"
                    + " FROM MV_RESEVAL_COAUTHORS where coauthor_source = 100 AND author_group_id = :author_dblp_id");
            q.setParameter("author_dblp_id", resList.get(i).getDblpID());
            //q.setMaxResults(no_of_res);
            List<Object[]> queryRes = q.getResultList();
            List<Researcher> coAuthorsList = new ArrayList<Researcher>();
            for (Object[] row : queryRes) {
                Researcher researcher = new Researcher();
                researcher.setId(((BigDecimal) row[0]).longValue());
                researcher.setName((String) row[1]);
                researcher.setCoAuthored_Num(((BigDecimal) row[2]).intValue());
                researcher.setDblpID(((BigDecimal) row[0]).longValue());
                coAuthorsList.add(researcher);
            }
            resList.get(i).setCoAuthors(coAuthorsList);
        }
        return resList;
    }
}

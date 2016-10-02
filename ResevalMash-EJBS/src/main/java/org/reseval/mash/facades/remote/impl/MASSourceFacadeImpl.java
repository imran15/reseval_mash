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
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.facades.remote.MASSourceFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class MASSourceFacadeImpl implements MASSourceFacadeRemote {
    
    @PersistenceContext(unitName = "com.reseval_ResevalMash-EJBS_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-Test")
    private EntityManager emTest;
     @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-TestSUSAN")
    private EntityManager emTest_SUSAN;
    
      @Override
    public List<Researcher> autoCompleteResearcherName(String partialName) {
        List<Researcher> res = new ArrayList<Researcher>();
        Query q = emTest_SUSAN.createNativeQuery("SELECT author_name,author_group_id, n_resources"
                + " FROM mv_reseval_mas_authors"
                + " where CATSEARCH(author_name, :partialName, NULL) > 0");
                //+ " order by author_name");
        q.setParameter("partialName", "%" + partialName + "%");
        //q.setParameter("partialName",partialName + "*");
        q.setMaxResults(5);
        List<Object[]> queryRes = q.getResultList();
        Researcher researcher;
        for (Object[] row : queryRes) {
            researcher = new Researcher();
            researcher.setName((String) row[0]);
            researcher.setId(((BigDecimal) row[1]).longValue());
            researcher.setMasID(((BigDecimal) row[1]).longValue());
            researcher.setPubsNumber(((BigDecimal) row[2]).intValue());
            res.add(researcher);
            
        }
        return res;
    }
     
      //################ Following method calls MAS_PUBS to get authors. new methods is above
//    @Override
//    public List<Researcher> autoCompleteResearcherName(String partialName) {
//        List<Researcher> res = new ArrayList<Researcher>();
//        Query q = emTest.createNativeQuery("SELECT distinct(author_name),author_group_id"
//                + " FROM MAS_PUBS"
//                + " where UPPER(author_name) like :partialName"
//                + " order by author_name");
//        q.setParameter("partialName", "%" + partialName.toUpperCase() + "%");
//        q.setMaxResults(10);
//        List<Object[]> queryRes = q.getResultList();
//        Researcher researcher;
//        for (Object[] row : queryRes) {
//            researcher = new Researcher();
//            researcher.setName((String) row[0]);
//            researcher.setId(((BigDecimal) row[1]).longValue());
//            res.add(researcher);
//            
//        }
//        return res;
//    }
    Integer fetch = -1;
    
    private String getWhereClause(List<QueryMapper> queryList) {
        
        String whereClause = " where ";
        
        if (queryList != null) {
            for (int i = 0; i < queryList.size(); i++) {
                
                if (queryList.get(i).getParamName().equals("fetch")) {
                    fetch = Integer.parseInt(queryList.get(i).getParamValue().toString());
                } else {
                    if (queryList.get(i).getParamName().equals("startYear")) {
                        whereClause += "publication_year" + queryList.get(i).getCondition() + queryList.get(i).getParamValue();
                        whereClause += " and ";
                    } else if (queryList.get(i).getParamName().equals("endYear")) {
                        whereClause += "publication_year" + queryList.get(i).getCondition() + queryList.get(i).getParamValue();
                        whereClause += " and ";
                    }
                    // if (i <= (queryList.size() - 1)) { //this condition is just to avoid putting AND in the end of where clause
                    // whereClause += " and ";
                    // }
                }
                
            }
        }
        
        if (whereClause.trim().equals("where  and") || whereClause.trim().equals("where")) {
            whereClause = "";
        }
        
        return whereClause;
    }
    
    @Override
    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList) {
        
        String whereClause = getWhereClause(queryList);
        //Integer fetch = -1;
        List<Researcher> resList = resWrapper.getResearchers();
        for (int i = 0; i < resList.size(); i++) {
            
            if (resList.get(i).getMasID() != null) {
                Query q;
                if (whereClause.length() > 7) {
                   q = emTest_SUSAN.createNativeQuery("SELECT resource_id, resource_title, resource_uri, publication_year, citation_count, venue_id, venue_name"
                                    + " FROM MV_MAS_RESOURCE_CITATION_INFO " + whereClause + " author_group_id = :author_mas_id");
                            q.setParameter("author_mas_id", resList.get(i).getMasID());
                            
                        } else {
                            q = emTest_SUSAN.createNativeQuery("SELECT resource_id, resource_title, resource_uri, publication_year, citation_count, venue_id, venue_name "
                                    + " FROM MV_MAS_RESOURCE_CITATION_INFO WHERE author_group_id = :author_mas_id");
                            q.setParameter("author_mas_id", resList.get(i).getMasID());
                        }
                if (fetch != -1) {
                    q.setMaxResults(fetch);
                }
                List<Object[]> queryRes = q.getResultList();
                List<Publication> pubList = new ArrayList();
                for (Object[] row : queryRes) {
                    Publication publication;
                    publication = new Publication();
                    publication.setId(((BigDecimal) row[0]).longValue());
                    publication.setTitle((String) row[1]);
                    publication.setUri((String) row[2]);
                    publication.setYear(Long.valueOf(row[3].toString()).longValue());
                    publication.setCitations(((BigDecimal) row[4]).intValue());
                    if (row[5] != null){
                    publication.setVenue(new Venue(((BigDecimal) row[5]).longValue(), (String) row[6]));}
                    else{
                        publication.setVenue(new Venue(0l,"NA"));
                    }
                    pubList.add(publication);
                }
                resList.get(i).setPublications(pubList);
                //resList.get(i).setPubsNumber(pubList.size());
                
            }
        }
        resWrapper.setResearchers(resList);
        return resWrapper;
    }
    
    @Override
    public ResearchersWrapper getCoAuthorPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList) {
        String whereClause = getWhereClause(queryList);
        List<Researcher> resList = resWrapper.getResearchers();
        
        for (int i = 0; i < resList.size(); i++) {
            if (resList.get(i).getCoAuthors() != null) {
                List<Researcher> coAuthorsList = resList.get(i).getCoAuthors();
                for (int j = 0; j < coAuthorsList.size(); j++) {
                    
                    if (coAuthorsList.get(j).getMasID() != null) {
                        Query q;
                        if (whereClause.length() > 7) {
                            //q = emTest.createNativeQuery("SELECT resource_id, resource_title, resource_uri, publication_year, venue_id, venue_name, citation_count "
                              //      + " FROM RESOURCES_INFO " + whereClause + " author_group_id = :author_mas_id");
                            q = emTest_SUSAN.createNativeQuery("SELECT resource_id, resource_title, resource_uri, publication_year, citation_count "
                                    + " FROM MV_RESOURCE_CITATION_INFO " + whereClause + " author_group_id = :author_mas_id");
                            q.setParameter("author_mas_id", coAuthorsList.get(j).getMasID());
                        } else {
                            q = emTest_SUSAN.createNativeQuery("SELECT resource_id, resource_title, resource_uri, publication_year, citation_count "
                                    + " FROM MV_RESOURCE_CITATION_INFO WHERE author_group_id = :author_mas_id");
                            q.setParameter("author_mas_id", coAuthorsList.get(j).getMasID());
                        }
                        if (fetch != -1) {
                            q.setMaxResults(fetch);
                        }
                        List<Object[]> queryRes = q.getResultList();
                        List<Publication> pubList = new ArrayList();
                        for (Object[] row : queryRes) {
                            Publication publication;
                            publication = new Publication();
                            publication.setId(((BigDecimal) row[0]).longValue());
                            publication.setTitle((String) row[1]);
                            publication.setUri((String) row[2]);
                            publication.setYear(Long.valueOf(row[3].toString()).longValue());
                           // publication.setVenue(new Venue(((BigDecimal) row[4]).longValue(), (String) row[5]));
                            publication.setCitations(((BigDecimal) row[4]).intValue());
                            pubList.add(publication);
                        }
                        coAuthorsList.get(j).setPublications(pubList);
                    }
                    
                }
                resList.get(i).setCoAuthors(coAuthorsList);
            }
        }
        resWrapper.setResearchers(resList);
        return resWrapper;
    }
    
//    @Override
//    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper) {
//        // ResearchersWrapper resWrapper = new ResearchersWrapper();
//        List<Researcher> resList = resWrapper.getResearchers();
//        for (int i = 0; i < resList.size(); i++) {
//            
//            if (resList.get(i).getMasID() != null) {
//                Query q = emTest.createNativeQuery("SELECT resource_id, resource_title, resource_uri, venue_id, venue_name "
//                        + " FROM RESOURCES_INFO WHERE author_group_id = :author_mas_id");
//                q.setParameter("author_mas_id", resList.get(i).getMasID());
//                List<Object[]> queryRes = q.getResultList();
//                
//                List<Publication> pubList = new ArrayList();
//                for (Object[] row : queryRes) {
//                    Publication publication;
//                    publication = new Publication();
//                    publication.setId(((BigDecimal) row[0]).longValue());
//                    publication.setTitle((String) row[1]);
//                    publication.setUri((String) row[2]);
//                    publication.setVenue(new Venue(((BigDecimal) row[3]).longValue(), (String) row[4]));
//                    pubList.add(publication);
//                }
//                resList.get(i).setPublications(pubList);
//            }
//        }
//        resWrapper.setResearchers(resList);
//        return resWrapper;
//    }
    
    private List<Researcher> feedFakeResearchers(List<Long> researchersIds) {
        
        List<Researcher> resList = new ArrayList<Researcher>();
        Researcher r = new Researcher();
        
        Publication p1 = new Publication();
        Publication p2 = new Publication();
        Publication p3 = new Publication();
        Publication p4 = new Publication();
        Publication p5 = new Publication();
        Publication p6 = new Publication();
        
        Venue v1 = new Venue();
        Venue v2 = new Venue();
        Venue v3 = new Venue();
        Venue v4 = new Venue();
        Venue v5 = new Venue();
        Venue v6 = new Venue();
        
        v1.setName("VLDB");
        v1.setImpactFactor(0.0);
        v1.setId(100l);
        
        v2.setName("CIKM");
        v2.setImpactFactor(0.0);
        v2.setId(101l);
        
        v3.setName("ICSOC");
        v3.setImpactFactor(0.0);
        v3.setId(102l);
        
        v4.setName("BPM");
        v4.setImpactFactor(0.0);
        v4.setId(103l);
        
        v5.setName("WWW");
        v5.setImpactFactor(0.0);
        v5.setId(104l);
        
        v6.setName("IEEE INTERNET COMPUTING");
        v6.setImpactFactor(0.0);
        v6.setId(105l);
        
        p1.setId(2798822l);
        p1.setTitle("Privacy Preserving Event Driven Integration for Interoperating Social and Health Systems");
        p1.getCoAuthors().add(new Researcher(12l, "Giampaolo Armellin"));
        p1.getCoAuthors().add(new Researcher(13l, "Dario Betti"));
        p1.getCoAuthors().add(new Researcher(14l, "Annamaria Chiasera"));
        p1.getCoAuthors().add(new Researcher(15l, "Gloria MartAnez"));
        p1.getCoAuthors().add(new Researcher(16l, "Jovan Stevovic"));
        p1.setCitations(15);
        //p1.setDate(datatypeFactory.newXMLGregorianCalendar(calendar));
        p1.setYear(1957l);
        p1.setVenue(v1);
        p1.setUri("");
        
        p2.setId(2798823l);
        p2.setTitle("From People to Services to UI: Distributed Orchestration of User Interfaces");
        p2.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p2.getCoAuthors().add(new Researcher(18l, "Stefano Soi"));
        p2.getCoAuthors().add(new Researcher(19l, "Stefano Tranquillini"));
        p2.getCoAuthors().add(new Researcher(20l, "Chang Heng"));
        p2.getCoAuthors().add(new Researcher(21l, "Li Yan"));
        p2.setCitations(15);
        p2.setYear(1987l);
        p2.setVenue(v2);
        p2.setUri("");
        
        p3.setId(2798824l);
        p3.setTitle("Liquid journals: scientific journals in the Web 2.0 era");
        p3.getCoAuthors().add(new Researcher(22l, "Marcos Baez"));
        p3.getCoAuthors().add(new Researcher(23l, "Alejandro Mussi"));
        p3.getCoAuthors().add(new Researcher(24l, "Aliaksandr Birukou"));
        p3.getCoAuthors().add(new Researcher(25l, "Maurizio Marchese"));
        p3.setCitations(30);
        p3.setYear(1990l);
        p3.setVenue(v3);
        p3.setUri("");
        
        p4.setId(2798825l);
        p4.setTitle("Analysis and applications of timed service protocols");
        p4.getCoAuthors().add(new Researcher(26l, "Julien Ponge"));
        p4.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p4.getCoAuthors().add(new Researcher(28l, "Farouk Toumani"));
        p4.setCitations(30);
        p4.setYear(1999l);
        p4.setVenue(v4);
        p4.setUri("");
        
        p5.setId(2798826l);
        p5.setTitle("Toward Uncertain Business Intelligence: The Case of Key Indicators");
        p5.getCoAuthors().add(new Researcher(29l, "Carlos Rodrguez"));
        p5.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p5.getCoAuthors().add(new Researcher(30l, "Cinzia Cappiello"));
        p5.setCitations(15);
        p5.setYear(2001l);
        p5.setVenue(v5);
        p5.setUri("");
        
        p6.setId(2798827l);
        p6.setTitle("Message Correlation and Web Service Protocol Mining from Inaccurate Logs");
        p6.getCoAuthors().add(new Researcher(31l, "Kreshnik Musaraj"));
        p6.getCoAuthors().add(new Researcher(32l, "Tetsuya Yoshida"));
        p6.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p6.getCoAuthors().add(new Researcher(33l, "Mohand-Said Hacid"));
        p6.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p6.setCitations(15);
        p6.setYear(2010l);
        p6.setVenue(v6);
        p6.setUri("");
        
        r.setId(619l);
        r.setName("Fabio");
        r.setSurname("Casati");
        // r.getMetrics().getMetricsStandard().add(new Metric("H-index", 23));
        //r.getMetrics().getMetricsStandard().add(new Metric("G-index", 60));
        //r.getMetrics().getMetricsStandard().add(new Metric("C-index", 250));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("H-index", 18));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("G-index", 50));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("C-index", 200));
        r.setPubsNumber(120);
        r.getPublications().add(p1);
        r.getPublications().add(p2);
        r.getPublications().add(p3);
        r.getPublications().add(p4);
        r.getPublications().add(p5);
        r.getPublications().add(p6);
        
        
        resList.add(r);

        //########################## id":36756,"name":"John","surname":"MYLOPOULOS"  ###################

        Researcher r2 = new Researcher();
        
        p1.setId(3798822l);
        p1.setTitle("Privacy Preserving Event Driven Integration for Interoperating Social and Health Systems");
        p1.getCoAuthors().add(new Researcher(12l, "Giampaolo Armellin"));
        p1.getCoAuthors().add(new Researcher(13l, "Dario Betti"));
        p1.getCoAuthors().add(new Researcher(14l, "Annamaria Chiasera"));
        p1.getCoAuthors().add(new Researcher(15l, "Gloria MartAnez"));
        p1.getCoAuthors().add(new Researcher(16l, "Jovan Stevovic"));
        p1.setCitations(15);
        //p1.setDate(datatypeFactory.newXMLGregorianCalendar(calendar));
        p1.setYear(1957l);
        p1.setVenue(v1);
        p1.setUri("");
        
        p2.setId(3798823l);
        p2.setTitle("From People to Services to UI: Distributed Orchestration of User Interfaces");
        p2.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p2.getCoAuthors().add(new Researcher(18l, "Stefano Soi"));
        p2.getCoAuthors().add(new Researcher(19l, "Stefano Tranquillini"));
        p2.getCoAuthors().add(new Researcher(20l, "Chang Heng"));
        p2.getCoAuthors().add(new Researcher(21l, "Li Yan"));
        p2.setCitations(15);
        p2.setYear(1987l);
        p2.setVenue(v2);
        p2.setUri("");
        
        p3.setId(3798824l);
        p3.setTitle("Liquid journals: scientific journals in the Web 2.0 era");
        p3.getCoAuthors().add(new Researcher(22l, "Marcos Baez"));
        p3.getCoAuthors().add(new Researcher(23l, "Alejandro Mussi"));
        p3.getCoAuthors().add(new Researcher(24l, "Aliaksandr Birukou"));
        p3.getCoAuthors().add(new Researcher(25l, "Maurizio Marchese"));
        p3.setCitations(30);
        p3.setYear(1990l);
        p3.setVenue(v3);
        p3.setUri("");
        
        p4.setId(3798825l);
        p4.setTitle("Analysis and applications of timed service protocols");
        p4.getCoAuthors().add(new Researcher(26l, "Julien Ponge"));
        p4.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p4.getCoAuthors().add(new Researcher(28l, "Farouk Toumani"));
        p4.setCitations(30);
        p4.setYear(1999l);
        p4.setVenue(v4);
        p4.setUri("");
        
        p5.setId(3798826l);
        p5.setTitle("Toward Uncertain Business Intelligence: The Case of Key Indicators");
        p5.getCoAuthors().add(new Researcher(29l, "Carlos Rodrguez"));
        p5.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p5.getCoAuthors().add(new Researcher(30l, "Cinzia Cappiello"));
        p5.setCitations(15);
        p5.setYear(2001l);
        p5.setVenue(v5);
        p5.setUri("");
        
        p6.setId(3798827l);
        p6.setTitle("Message Correlation and Web Service Protocol Mining from Inaccurate Logs");
        p6.getCoAuthors().add(new Researcher(31l, "Kreshnik Musaraj"));
        p6.getCoAuthors().add(new Researcher(32l, "Tetsuya Yoshida"));
        p6.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p6.getCoAuthors().add(new Researcher(33l, "Mohand-Said Hacid"));
        p6.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p6.setCitations(15);
        p6.setYear(2010l);
        p6.setVenue(v6);
        p6.setUri("");
        
        r2.setId(36756l);
        r2.setName("John");
        r2.setSurname("MYLOPOULOS");
        // r.getMetrics().getMetricsStandard().add(new Metric("H-index", 23));
        //r.getMetrics().getMetricsStandard().add(new Metric("G-index", 60));
        //r.getMetrics().getMetricsStandard().add(new Metric("C-index", 250));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("H-index", 18));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("G-index", 50));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("C-index", 200));
        r2.setPubsNumber(100);
        r2.getPublications().add(p1);
        r2.getPublications().add(p2);
        r2.getPublications().add(p3);
        r2.getPublications().add(p4);
        r2.getPublications().add(p5);
        r2.getPublications().add(p6);
        
        
        
        resList.add(r2);
        
        return resList;
    }
}

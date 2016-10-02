/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.facades.remote.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.facades.remote.VenueRankingFacadeRemote;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.VenuesWrapper;
import org.reseval.mash.facades.remote.ResearchersFacadeRemote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.facades.remote.MASSourceFacadeRemote;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class VenueFacadeImpl implements VenueRankingFacadeRemote {

    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-Test")
    private EntityManager emDEV;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-TestSUSAN")
    private EntityManager emTest_SUSAN;

    @Override
    public ResearchersWrapper getMASPublicationsVenues(ResearchersWrapper resWrapper) {
        List<Researcher> resList = resWrapper.getResearchers();
        for (int i = 0; i < resList.size(); i++) {
            List<Publication> resPubList = resList.get(i).getPublications();
            if (resPubList != null) {
                for (int j = 0; j < resPubList.size(); j++) {
                    Query q;
                    q = emTest_SUSAN.createNativeQuery("SELECT venue_id, venue_name "
                            + " FROM resource_mas_venue_info where resource_id = :resource_id");
                    q.setParameter("resource_id", resPubList.get(j).getId());
                    List<Object[]> queryRes = q.getResultList();
                    for (Object[] row : queryRes) {
                        resPubList.get(j).setVenue(new Venue(((BigDecimal) row[0]).longValue(), (String) row[1]));
                    }
                }
            }

            resList.get(i).setPublications(resPubList);
        }
        resWrapper.setResearchers(resList);
        return resWrapper;
    }

    @Override
    public VenuesWrapper getVenueRanking() {

        VenuesWrapper venueWrapper = new VenuesWrapper();
        venueWrapper.setKey(StaticVars.CACHE_VENUE_KEY);
        venueWrapper.setVenueList(feedFakeVenues());

        return venueWrapper;
    }

    @Override
    public VenuesWrapper getVenueRankingSRS() {

        VenuesWrapper venueWrapper = new VenuesWrapper();

        Query q = emDEV.createNativeQuery("select venue_id, venue_name, venue_sname, venue_type "
                + " from target_db.venue");
        // q.setParameter("author_mas_id", resList.get(i).getMasID());
        List<Object[]> queryRes = q.getResultList();

        List<Venue> venueList = new ArrayList();
        for (Object[] row : queryRes) {
            Venue venue = new Venue();
            venue.setId(((BigDecimal) row[0]).longValue());
            venue.setName((String) row[1]);
            venue.setSname((String) row[2]);
            venue.setType((String) row[3]);
            venueList.add(venue);
        }

        venueWrapper.setVenueList(venueList);
        return venueWrapper;
    }

    @Override
    public VenuesWrapper getVenueRankingUNITN() {

        VenuesWrapper venueWrapper = new VenuesWrapper();

        Query q = emDEV.createNativeQuery("select v_id_pk, v_name, v_if, v_class "
                + " from venues");
        // q.setParameter("author_mas_id", resList.get(i).getMasID());
        List<Object[]> queryRes = q.getResultList();

        List<Venue> venueList = new ArrayList();
        for (Object[] row : queryRes) {
            Venue venue = new Venue();
            venue.setId(((BigDecimal) row[0]).longValue());
            venue.setName((String) row[1]);
            venue.setImpactFactor(((BigDecimal) row[2]).doubleValue());
            venue.setvClass((String) row[3]);
            venueList.add(venue);
        }

        venueWrapper.setVenueList(venueList);
        return venueWrapper;
    }

    private List<Venue> feedFakeVenues() {

        List<Venue> venueList = new ArrayList<Venue>();

        Venue v1 = new Venue();
        Venue v2 = new Venue();
        Venue v3 = new Venue();
        Venue v4 = new Venue();
        Venue v5 = new Venue();
        Venue v6 = new Venue();
        Venue v7 = new Venue();
        Venue v8 = new Venue();
        Venue v9 = new Venue();

        v1.setName("VLDB");
        v1.setImpactFactor(3.8);
        v1.setId(100l);

        v2.setName("CIKM");
        v2.setImpactFactor(2.8);
        v2.setId(101l);

        v3.setName("ICSOC");
        v3.setImpactFactor(1.8);
        v3.setId(102l);

        v4.setName("BPM");
        v4.setImpactFactor(2.6);
        v4.setId(103l);

        v5.setName("WWW");
        v5.setImpactFactor(3.5);
        v5.setId(104l);

        v6.setName("IEEE INTERNET COMPUTING");
        v6.setImpactFactor(3.2);
        v6.setId(105l);

        v7.setName("BDP");
        v7.setImpactFactor(1.7);
        v7.setId(106l);

        v8.setName("JCDL");
        v8.setImpactFactor(2.9);
        v8.setId(107l);

        v9.setName("ECSS");
        v9.setImpactFactor(1.9);
        v9.setId(108l);


        venueList.add(v1);
        venueList.add(v2);
        venueList.add(v3);
        venueList.add(v4);
        venueList.add(v5);
        venueList.add(v6);
        venueList.add(v7);
        venueList.add(v8);
        venueList.add(v9);

        return venueList;
    }
}

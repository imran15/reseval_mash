/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.facades.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DISIImpactPercentileLocal;
import org.reseval.mash.api.facades.VenueImpactComputationLocal;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.facades.remote.VenueRankingFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;
import org.reseval.mash.wrappers.VenuesWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class VenueImpactComputationImpl implements VenueImpactComputationLocal {

    private List<Venue> _distinctVenues;
    @EJB
    private VenueRankingFacadeRemote _venuRankingRemote;

    @Override
    public ResearchersWrapper getImpact(String cacheKey) {
        ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);
        if (resWrapper != null && _distinctVenues != null) {
            int researcherSize = resWrapper.getResearchers().size();
            for (int i = 0; i < researcherSize; i++) {
                List<Publication> pubList = resWrapper.getResearchers().get(i).getPublications();
                int publicationsSize = 0;
                if (pubList == null) {
                    publicationsSize = 0;
                } else {
                    publicationsSize = pubList.size();
                }
                //int publicationsSize = pubList.size();
                for (int j = 0; j < publicationsSize; j++) {
                    for (int k = 0; k < _distinctVenues.size(); k++) {
                        if (_distinctVenues.get(k).getId().equals(pubList.get(j).getVenue().getId())) {
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setImpactFactor(_distinctVenues.get(k).getImpactFactor());
                        }
                    }
                }
            }
            CacheMemoryManager.getInstance().putItalianDSResearchers(cacheKey, resWrapper);
        }
        return resWrapper;
    }

    @Override
    public ResearchersWrapper getImpactNew(String cacheKey) {
        ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);
        if (resWrapper != null && _distinctVenues != null) {
            int researcherSize = resWrapper.getResearchers().size();
            for (int i = 0; i < researcherSize; i++) {
                List<Publication> pubList = resWrapper.getResearchers().get(i).getPublications();
                int publicationsSize = 0;
                if (pubList == null) {
                    publicationsSize = 0;
                } else {
                    publicationsSize = pubList.size();
                }
                //int publicationsSize = pubList.size();
                for (int j = 0; j < publicationsSize; j++) {
                    for (int k = 0; k < _distinctVenues.size(); k++) {
                        if (_distinctVenues.get(k).getName().equalsIgnoreCase(pubList.get(j).getVenue().getName())) {
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setImpactFactor(_distinctVenues.get(k).getImpactFactor());
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setvClass(_distinctVenues.get(k).getvClass());
                        }
                    }
                }
            }
            CacheMemoryManager.getInstance().putItalianDSResearchers(cacheKey, resWrapper);
        }
        return resWrapper;
    }

    @Override
    public List<Venue> setVenueWeights(String cacheKey) {
        VenuesWrapper venueWrapper = CacheMemoryManager.getInstance().getVenuesRanking(cacheKey);
        if (venueWrapper != null) {
            _distinctVenues = venueWrapper.getVenueList();
        }

        return _distinctVenues;
    }

    @Override
    public ResearchersWrapper getVenueImpact(ResearchersWrapper resWrapper) {
        ResearchersWrapper researchers =  _venuRankingRemote.getMASPublicationsVenues(resWrapper);
     
        return calculateImpact(researchers);
    }
    
    private ResearchersWrapper calculateImpact(ResearchersWrapper resWrapper){
        
        if (resWrapper != null && _distinctVenues != null) {
            int researcherSize = resWrapper.getResearchers().size();
            for (int i = 0; i < researcherSize; i++) {
                List<Publication> pubList = resWrapper.getResearchers().get(i).getPublications();
                int publicationsSize = 0;
                if (pubList == null) {
                    publicationsSize = 0;
                } else {
                    publicationsSize = pubList.size();
                }
                //int publicationsSize = pubList.size();
                for (int j = 0; j < publicationsSize; j++) {
                    for (int k = 0; k < _distinctVenues.size(); k++) {
                        if (pubList.get(j).getVenue() != null) // added new line on 26 march
                        if (_distinctVenues.get(k).getName().equalsIgnoreCase(pubList.get(j).getVenue().getName())) {
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setImpactFactor(_distinctVenues.get(k).getImpactFactor());
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setvClass(_distinctVenues.get(k).getvClass());
                        }
                        else
                        {
                         resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setImpactFactor(0.0);
                            resWrapper.getResearchers().get(i).getPublications().get(j).getVenue().setvClass("NA");   
                        }
                    }
                }
            }
            //CacheMemoryManager.getInstance().putItalianDSResearchers(cacheKey, resWrapper);
        }
        return resWrapper;
    }
    
}

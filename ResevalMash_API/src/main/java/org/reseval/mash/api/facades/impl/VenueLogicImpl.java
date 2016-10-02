/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.facades.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.VenueLogicLocal;
import org.reseval.mash.facades.remote.VenueRankingFacadeRemote;
import org.reseval.mash.wrappers.VenuesWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class VenueLogicImpl implements VenueLogicLocal {

    @EJB
    private VenueRankingFacadeRemote _venuRankingRemote;
    
    @Override
    @Deprecated
    public VenuesWrapper getVenuesRanking(String cacheKey) {
        VenuesWrapper venueWrapper = CacheMemoryManager.getInstance().getVenuesRanking(cacheKey);
        if (venueWrapper == null){
            
            venueWrapper = _venuRankingRemote.getVenueRanking();
            if (venueWrapper != null){
                CacheMemoryManager.getInstance().putVenuesRanking(cacheKey, venueWrapper);
            }
        }
        
        return venueWrapper;
    }

    @Override
    public VenuesWrapper getVenuesRankingSRS(String cacheKey) {
        VenuesWrapper venueWrapper = CacheMemoryManager.getInstance().getVenuesRanking(cacheKey);
        if (venueWrapper == null){
            
            venueWrapper = _venuRankingRemote.getVenueRankingSRS();
            if (venueWrapper != null){
                CacheMemoryManager.getInstance().putVenuesRanking(cacheKey, venueWrapper);
            }
        }
        
        return venueWrapper;
    }

    @Override
    public VenuesWrapper getVenuesRankingUNITN(String cacheKey) {
         VenuesWrapper venueWrapper = CacheMemoryManager.getInstance().getVenuesRanking(cacheKey);
        if (venueWrapper == null){
            
            venueWrapper = _venuRankingRemote.getVenueRankingUNITN();
            if (venueWrapper != null){
                CacheMemoryManager.getInstance().putVenuesRanking(cacheKey, venueWrapper);
            }
        }
        
        return venueWrapper;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import javax.ejb.Local;

import org.reseval.mash.wrappers.VenuesWrapper;


/**
 *
 * @author Muhammad Imran
 */
@Local
public interface VenueLogicLocal {

    public VenuesWrapper getVenuesRanking(String cacheKey);
    
    public VenuesWrapper getVenuesRankingSRS(String cacheKey);
    
    public VenuesWrapper getVenuesRankingUNITN(String cacheKey);
}

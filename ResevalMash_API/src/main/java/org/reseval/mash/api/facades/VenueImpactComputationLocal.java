/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import java.util.List;
import javax.ejb.Local;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.wrappers.ResearchersWrapper;



/**
 *
 * @author Muhammad Imran
 */
@Local
public interface VenueImpactComputationLocal {

    public ResearchersWrapper getImpact(String cacheKey);
    
     public ResearchersWrapper getImpactNew(String cacheKey);
     
    public ResearchersWrapper getVenueImpact(ResearchersWrapper resWrapper); 
    
    public List<Venue> setVenueWeights(String cacheKey);
}

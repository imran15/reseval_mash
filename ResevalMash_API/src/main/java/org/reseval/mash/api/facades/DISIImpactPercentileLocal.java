/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import javax.ejb.Local;
import org.reseval.mash.api.stubs.beans.DISIDistribution;
import org.reseval.mash.wrappers.ResearchersWrapper;



/**
 *
 * @author Muhammad Imran
 */
@Local
public interface DISIImpactPercentileLocal {

    public DISIDistribution setDistribution(String cacheKey);
    public ResearchersWrapper getPercentile(String cacheKey);
    //public void getPercentile();
}

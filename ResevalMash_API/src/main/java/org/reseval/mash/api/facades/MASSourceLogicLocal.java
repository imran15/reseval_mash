/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import java.util.List;
import javax.ejb.Local;
import org.reseval.mash.beans.QueryMapper;

import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;


/**
 *
 * @author Muhammad Imran
 */
@Local
public interface MASSourceLogicLocal {

   // public ResearchersWrapper getPublicationsVenue(String cacheKey);
    
   // public ResearchersWrapper getPublications(String cacheKey);
    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList);
    
      public List<Researcher> autoCompleteResearcherName(String partialName);
}

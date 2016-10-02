/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.facades.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DBLPSourceLogicLocal;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.facades.remote.DBLPSourceFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class DBLPSourceLogicImpl implements DBLPSourceLogicLocal {

    @EJB
    private DBLPSourceFacadeRemote _dblpSourceRemote;
    
    
    @Override
    public ResearchersWrapper getPublications(String cacheKey) {
        
       ResearchersWrapper dbResWrapper=null; 
       ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);
       if (cacheResWrapper != null)
       if (cacheResWrapper.getResearchers().get(0).getPublications().isEmpty()){
       dbResWrapper = _dblpSourceRemote.getPublications(cacheResWrapper);
        if (dbResWrapper != null)
        CacheMemoryManager.getInstance().putItalianDSResearchers(cacheKey, dbResWrapper);
       }
       else{
           return cacheResWrapper;
       }
        return dbResWrapper;
    }
    
    @Override
    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList) {
        
       ResearchersWrapper dbResWrapper=null; 
       ResearchersWrapper cacheResWrapper =  resWrapper;
       if (cacheResWrapper != null){
       dbResWrapper = _dblpSourceRemote.getPublications(cacheResWrapper,queryList);
      //  if (dbResWrapper != null)
       // CacheMemoryManager.getInstance().putItalianDSResearchers(resWrapper.getKey(), dbResWrapper);
       }
        return dbResWrapper;
    }

    @Override
    public List<Researcher> autoCompleteResearcherName(String partialName) {
        return _dblpSourceRemote.autoCompleteResearcherName(partialName);
    }
}

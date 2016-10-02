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
import org.reseval.mash.api.facades.MASSourceLogicLocal;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.facades.remote.MASSourceFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class MASSourceLogicImpl implements MASSourceLogicLocal {

    @EJB
    private MASSourceFacadeRemote _masSourceRemote;

    @Override
    public List<Researcher> autoCompleteResearcherName(String partialName) {
        return _masSourceRemote.autoCompleteResearcherName(partialName);
    }

//    @Override
//    public ResearchersWrapper getPublications(String cacheKey) {
//
//        ResearchersWrapper dbResWrapper = null;
//        ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);
//        if (cacheResWrapper != null) {
//            if (cacheResWrapper.getResearchers().get(0).getPublications().isEmpty()) {
//                dbResWrapper = _masSourceRemote.getPublications(cacheResWrapper);
//                if (dbResWrapper != null) {
//                    CacheMemoryManager.getInstance().putItalianDSResearchers(cacheKey, dbResWrapper);
//                }
//            } else {
//                return cacheResWrapper;
//            }
//        }
//        return dbResWrapper;
//    }

    @Override
    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList) {

        ResearchersWrapper dbResWrapper = null;
        if (resWrapper != null) {
            List<Researcher> resList = resWrapper.getResearchers();
            if (!(resList.get(0).getCoAuthors().isEmpty())) {
                //get publications for co-authors
                dbResWrapper = _masSourceRemote.getCoAuthorPublications(resWrapper, queryList);
            } else {
                // get publications for normal researchers
                dbResWrapper = _masSourceRemote.getPublications(resWrapper, queryList);
            }
        }
        return dbResWrapper;
    }
//    @Override
//    public ResearchersWrapper getPublications(ResearchersWrapper resWrapper, List<QueryMapper> queryList) {
//        
//       ResearchersWrapper dbResWrapper=null; 
//       if (resWrapper != null){
//       dbResWrapper = _masSourceRemote.getPublications(resWrapper,queryList);
//       }
//        return dbResWrapper;
//    }
}

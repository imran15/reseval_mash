/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.facades.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.ItalianSourceLogicLocal;
import org.reseval.mash.api.stubs.beans.CacheMetaData;
import org.reseval.mash.api.stubs.beans.CacheWrapper;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.beans.Department;
import org.reseval.mash.beans.Faculty;
import org.reseval.mash.beans.Rank;
import org.reseval.mash.beans.Sector;
import org.reseval.mash.beans.University;
import org.reseval.mash.facades.remote.ItalianSourceFacadeRemote;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class ItalianSourceLogicImpl implements ItalianSourceLogicLocal {

    @EJB
    private ItalianSourceFacadeRemote italianSourceRemote;

    @Override
    public List<University> autoCompleteUName(String input) {
        return italianSourceRemote.autoCompleteUni(input);
    }

    @Override
    public List<Department> getAllDeptByUniID(Long id) {
        return italianSourceRemote.getAllDeptByUnivID(id);
    }
 @Override
    public String getUniNamebyID(Long id) {
        return italianSourceRemote.getUniNamebyID(id);
    }
    @Override
    public List<Faculty> getAllfacultiesByUniID(Long id) {
        return italianSourceRemote.getAllFacByUniID(id);
    }

    @Override
    public ResearchersWrapper getResearcherThroughCache(String key, QueryMapperWrapper queryWrapper) {
        String queryCacheKey = key + StaticVars.CACHE_ITDS_QUERY_KEY;
        QueryMapperWrapper cachedQuery = CacheMemoryManager.getInstance().getQueryWrapper(queryCacheKey);
        ResearchersWrapper resWrapper;
       
        if (cachedQuery == null) {

            resWrapper = italianSourceRemote.getResearchersByQuery(queryWrapper.getMappersList());
            if (resWrapper != null) {

                resWrapper.setKey(key);
                CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
               // CacheMemoryManager.getInstance().putItalianDSResearchersNew(new CacheWrapper(key, resList, new CacheMetaData(null)));
                CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
            }
        } else {
            if (!(cachedQuery.getMappersList().equals(queryWrapper.getMappersList()))) {

                resWrapper = italianSourceRemote.getResearchersByQuery(queryWrapper.getMappersList());
                if (resWrapper != null) {

                    resWrapper.setKey(key);
                    CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
                }
            } else {
                resWrapper = CacheMemoryManager.getInstance().getResearchers(key);
                // Here its better to set null those properties of researcher which are not the requirement of Italian source component. Like publications etc
                //This can be accompolished by creating a bean converter class, which used to set null certain attributes.
            }
        }

        return resWrapper;
    }

    @Override
    public List<Rank> autoCompleteRank(String input) {
        return italianSourceRemote.autoCompleteforRanks(input);
    }

    @Override
    public List<Sector> autoCompleteSectorCode(String input) {
        return italianSourceRemote.autoCompleteforSectorsCode(input);
    }

    @Override
    public List<Sector> autoCompleteSectorName(String input) {
        return italianSourceRemote.autoCompleteforSectorsName(input);
    }
}

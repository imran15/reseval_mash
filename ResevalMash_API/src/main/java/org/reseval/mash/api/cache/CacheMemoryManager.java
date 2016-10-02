/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.cache;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import org.reseval.mash.api.stubs.beans.CacheWrapper;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.stubs.beans.ResearcherLong;
import org.reseval.mash.wrappers.ResearchersWrapper;
import org.reseval.mash.wrappers.VenuesWrapper;

/**
 *
 * @author Muhammad Imran
 */
public class CacheMemoryManager {

    private static CacheMemoryManager instance;
    private CacheManager manager;
    private CacheConfiguration config;
    private Cache cache;

    private CacheMemoryManager() {
        createCache();
    }

    public static CacheMemoryManager getInstance() {
        if (instance == null) {
            instance = new CacheMemoryManager();
        }
        return instance;
    }

    private Cache getCache() {
        return cache;
    }

    public QueryMapperWrapper getQueryWrapper(String key) {

        Status s = cache.getStatus();
        if (!s.equals(Status.STATUS_ALIVE)) {
            createCache();
        }

        QueryMapperWrapper query = null;
        Element tmp = null;
        tmp = cache.get(key);
        if (tmp != null) {
            {
                try {
                    query = ((QueryMapperWrapper) tmp.getObjectValue()).clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return query;
    }
    
    public void removeElement(String key){
        
         try {
           
            cache.remove(key);
        } catch (IllegalStateException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void putQuery(String key, QueryMapperWrapper queryWrapper) {
        QueryMapperWrapper clone;
        try {
            clone = queryWrapper.clone();
            Element e = new Element(key, clone);
            cache.put(e);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public ResearcherList getItalianDSResearchers(Long key) {
//
//        Status s = cache.getStatus();
//        if (!s.equals(Status.STATUS_ALIVE)) {
//            createCache();
//        }
//
//        ResearcherList rData = null;
//        Element tmp = null;
//        tmp = cache.get(key.longValue());
//        if (tmp != null) {
//            {
//                try {
//                    rData = ((ResearcherList) tmp.getObjectValue()).clone();
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//        return rData;
//    }
//
//    public void putItalianDSResearchers(Long key, ResearcherList rData) {
//        // Status s = cache.getStatus();
//        // System.err.println("STATUS CACHE: " + s.intValue());
//        ResearcherList clone;
//        try {
//            clone = rData.clone();
//            Element e = new Element(key.longValue(), clone);
//            cache.put(e);
//        } catch (CloneNotSupportedException ex) {
//            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

    public ResearchersWrapper getResearchers(String key) {

        Status s = cache.getStatus();
        if (!s.equals(Status.STATUS_ALIVE)) {
            createCache();
        }

        ResearchersWrapper rData = null;
        Element tmp = null;
        tmp = cache.get(key);
        if (tmp != null) {
            {
                try {
                    rData = ((ResearchersWrapper) tmp.getObjectValue()).clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return rData;
    }

    public void putItalianDSResearchers(String key, ResearchersWrapper rData) {
        // Status s = cache.getStatus();
        // System.err.println("STATUS CACHE: " + s.intValue());
        ResearchersWrapper clone;
        try {
            clone = rData.clone();
            Element e = new Element(key, clone);
            cache.put(e);
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void putItalianDSResearchersNew(CacheWrapper cacheObject) {
        // Status s = cache.getStatus();
        // System.err.println("STATUS CACHE: " + s.intValue());
        CacheWrapper clone;
        try {
            clone = cacheObject.clone();
            Element e = new Element(cacheObject.getCacheKey(), clone);
            cache.put(e);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public VenuesWrapper getVenuesRanking(String key) {

        Status s = cache.getStatus();
        if (!s.equals(Status.STATUS_ALIVE)) {
            createCache();
        }

        VenuesWrapper vWrapper = null;
        Element tmp = null;
        tmp = cache.get(key);
        if (tmp != null) {
            {
                try {
                    vWrapper = ((VenuesWrapper) tmp.getObjectValue()).clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return vWrapper;
    }

    public void putVenuesRanking(String key, VenuesWrapper vWrapper) {
        // Status s = cache.getStatus();
        // System.err.println("STATUS CACHE: " + s.intValue());
        VenuesWrapper clone;
        try {
            clone = vWrapper.clone();
            Element e = new Element(key, clone);
            cache.put(e);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResearcherLong getResearcherData(Long key) {

        Status s = cache.getStatus();
        if (!s.equals(Status.STATUS_ALIVE)) {
            createCache();
        }

        ResearcherLong vWrapper = null;
        Element tmp = null;
        tmp = cache.get(key.longValue());
        if (tmp != null) {
            {
                try {
                    vWrapper = ((ResearcherLong) tmp.getObjectValue()).clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return vWrapper;
    }

    public void put(Long key, ResearcherLong rData) {
        // Status s = cache.getStatus();
        // System.err.println("STATUS CACHE: " + s.intValue());
        ResearcherLong clone;
        try {
            clone = rData.clone();
            Element e = new Element(key.longValue(), clone);
            cache.put(e);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Integer getSize() {
        return cache.getSize();
    }

    private void setConfiguration(CacheConfiguration config) {

//        <cache name="ResevalMashCache"
//        eternal="true"
//        maxElementsInMemory="100"
//        overflowToDisk="false"
//        diskPersistent="false"
//        memoryStoreEvictionPolicy="LRU"
//        timeToLiveSeconds="100"
//        statistics="true"
//        />

        config.setName("ResevalMashCache");
        config.setEternal(true);
        config.setTimeToIdleSeconds(0);
        config.setTimeToLiveSeconds(600); // 86400 1 day
        config.setOverflowToDisk(false);
        config.setDiskPersistent(false);
        config.maxElementsInMemory(30);
        config.setMemoryStoreEvictionPolicy("LRU");


//        FactoryConfiguration peerListenerFactory = new FactoryConfiguration();
//        peerListenerFactory.className("org.terracotta.ehcachedx.monitor.probe.ProbePeerListenerFactory");
//        peerListenerFactory.properties("monitorAddress=localhost, monitorPort=9889, memoryMeasurement=true");
//        //cacheManagerConfig.addCacheManagerPeerListenerFactory(peerListenerFactory);
//        //config.addca
//        
//       // CacheConfiguration.CacheEventListenerFactoryConfiguration cacheManagerConfig = new CacheEventListenerFactoryConfiguration();
//        config.addCacheEventListenerFactory(new CacheConfiguration.CacheEventListenerFactoryConfiguration().listenFor(peerListenerFactory));
//        //cacheConfig.statistics(true);
//        config.statistics(true);
    }

    public CacheManager getCacheManager() {
        return manager;
    }

    private void createCache() {
        try {
            manager = new CacheManager();
            cache = manager.getCache("ResevalMashCache");
            if (!manager.cacheExists("ResevalMashCache")) {
                manager.addCache(cache);
            }
        } catch (Exception ex) {
            Logger.getLogger(CacheMemoryManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

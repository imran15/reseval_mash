/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.beans;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Muhammad Imran
 */
public class CacheWrapper implements Serializable{
    
   private String cacheKey;
   private Object dataObject;
   private CacheMetaData metaObject;


    public CacheWrapper() {
    }

    public CacheWrapper(String cacheKey, Object dataObject, CacheMetaData metaObject) {
        this.cacheKey = cacheKey;
        this.dataObject = dataObject;
        this.metaObject = metaObject;
    }
    

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Object getDataObject() {
        return dataObject;
    }

    public void setDataObject(Object dataObject) {
        this.dataObject = dataObject;
    }

    public CacheMetaData getMetaObject() {
        return metaObject;
    }

    public void setMetaObject(CacheMetaData metaObject) {
        this.metaObject = metaObject;
    }

   

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CacheWrapper other = (CacheWrapper) obj;
        if ((this.cacheKey == null) ? (other.cacheKey != null) : !this.cacheKey.equals(other.cacheKey)) {
            return false;
        }
        
        if ((this.dataObject == null) ? (other.dataObject != null) : !this.dataObject.equals(other.dataObject)) {
            return false;
        }
        
        if ((this.metaObject == null) ? (other.metaObject != null) : !this.metaObject.equals(other.metaObject)) {
            return false;
        }
       
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public CacheWrapper clone() throws CloneNotSupportedException {
        CacheWrapper clonedObj = new CacheWrapper();
        if (this.cacheKey != null){
            clonedObj.cacheKey = this.cacheKey;
        }
        if (this.dataObject != null){
            clonedObj.dataObject = this.dataObject;
        }
        
        if (this.metaObject != null){
            clonedObj.metaObject = this.metaObject;
        }
        
        return clonedObj;
        }

  
    

   

 
    
    
}

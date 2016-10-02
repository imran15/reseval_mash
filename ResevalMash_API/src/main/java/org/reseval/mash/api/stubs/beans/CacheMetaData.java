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
public class CacheMetaData implements Serializable{
    
   private List<CacheStatusEntry> statusHistory;


    public CacheMetaData() {
    }

    public CacheMetaData(List<CacheStatusEntry> statusHistory) {
        this.statusHistory = statusHistory;
    }
    
    

    public List<CacheStatusEntry> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<CacheStatusEntry> statusHistory) {
        this.statusHistory = statusHistory;
    }

   

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CacheMetaData other = (CacheMetaData) obj;
        if ((this.statusHistory == null) ? (other.statusHistory != null) : !this.statusHistory.equals(other.statusHistory)) {
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
    public CacheMetaData clone() throws CloneNotSupportedException {
        CacheMetaData clonedObj = new CacheMetaData();
        if (this.statusHistory != null){
            clonedObj.statusHistory = this.statusHistory;
        }
        
        return clonedObj;
        }

  
    

   

 
    
    
}

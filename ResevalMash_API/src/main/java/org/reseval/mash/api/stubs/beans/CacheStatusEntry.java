/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.beans;

import java.io.Serializable;

/**
 *
 * @author Muhammad Imran
 */
public class CacheStatusEntry implements Serializable{
    
    private String componentID;
    private String updatedClass;

    public CacheStatusEntry(String componentID, String updatedClass) {
        this.componentID = componentID;
        this.updatedClass = updatedClass;
    }


    
    public CacheStatusEntry() {
    }

    public String getComponentID() {
        return componentID;
    }

    public void setComponentID(String componentID) {
        this.componentID = componentID;
    }

    public String getUpdatedClass() {
        return updatedClass;
    }

    public void setUpdatedClass(String updatedClass) {
        this.updatedClass = updatedClass;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CacheStatusEntry other = (CacheStatusEntry) obj;
        if ((this.componentID == null) ? (other.componentID != null) : !this.componentID.equals(other.componentID)) {
            return false;
        }
        if ((this.updatedClass == null) ? (other.updatedClass != null) : !this.updatedClass.equals(other.updatedClass)) {
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
    public CacheStatusEntry clone() throws CloneNotSupportedException {
        CacheStatusEntry clonedObj = new CacheStatusEntry();
        if (this.componentID != null){
            clonedObj.componentID = this.componentID;
        }
        
        if (this.updatedClass != null){
            clonedObj.updatedClass = this.updatedClass;
        }
        
        return clonedObj;
        }

  
    

   

 
    
    
}

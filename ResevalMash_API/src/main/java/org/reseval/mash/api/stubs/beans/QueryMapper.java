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
public class QueryMapper implements Serializable{
    
    private String paramName;
    private String paramValue;


    public QueryMapper() {
    }

    public QueryMapper(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryMapper other = (QueryMapper) obj;
        if ((this.paramName == null) ? (other.paramName != null) : !this.paramName.equals(other.paramName)) {
            return false;
        }
        if ((this.paramValue == null) ? (other.paramValue != null) : !this.paramValue.equals(other.paramValue)) {
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
    public QueryMapper clone() throws CloneNotSupportedException {
        QueryMapper clonedObj = new QueryMapper();
        if (this.paramName != null){
            clonedObj.paramName = this.paramName;
        }
        
        if (this.paramValue != null){
            clonedObj.paramValue = this.paramValue;
        }
        
        return clonedObj;
        }

  
    

   

 
    
    
}

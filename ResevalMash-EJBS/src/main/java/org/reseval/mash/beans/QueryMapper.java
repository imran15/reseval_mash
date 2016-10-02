/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;


/**
 *
 * @author Muhammad Imran
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryMapper", propOrder = {
    "paramName",
    "paramValue",
    "condition"
})
@XmlRootElement(name = "queryMapper")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class QueryMapper implements Serializable{
    
    private String paramName;
    private Object paramValue;
    private String condition;


    public QueryMapper() {
    }

    public QueryMapper(String paramName, Object paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        
    }

    public QueryMapper(String paramName, Object paramValue, String condition) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.condition = condition;
    }
    

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
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
        if ((this.condition == null) ? (other.condition != null) : !this.condition.equals(other.condition)) {
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
        
        if (this.condition != null){
            clonedObj.condition = this.condition;
        }
        return clonedObj;
        }

    @Override
    public String toString() {
        return "QueryMapper{" + "paramName=" + paramName + ", paramValue=" + paramValue + ", condition=" + condition + '}';
    }

  
    

   

 
    
    
}

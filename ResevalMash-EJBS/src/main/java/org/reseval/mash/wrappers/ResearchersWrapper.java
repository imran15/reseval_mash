/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.wrappers;
import org.reseval.mash.beans.*;
import java.io.Serializable;
import java.util.List;
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
@XmlType(name = "researcherList", propOrder = {
    "key",
    "researchers"
 })
@XmlRootElement(name = "researcherList")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class ResearchersWrapper implements Serializable {

    protected String key;
    protected List<Researcher> researchers;

    public ResearchersWrapper() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = id;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

   
   

    @Override
    public ResearchersWrapper clone() throws CloneNotSupportedException {
        ResearchersWrapper res = new ResearchersWrapper();
        
        if (this.key != null){
            res.key = this.key;
        }
        if (this.researchers != null){
            res.researchers= this.researchers;
            
        }
        
        return res;
    }

    


}

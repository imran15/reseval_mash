/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.beans;

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
@XmlType(name = "distribution", propOrder = {
    "catA",
    "catB",
    "catC",
    "catD"
})
@XmlRootElement(name = "distribution")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class DISIDistribution implements Serializable{
    
   protected Integer [] catA;
   protected Integer [] catB;
   protected Integer [] catC;
   protected Integer [] catD;

    public Integer[] getCatA() {
        return catA;
    }

    public void setCatA(Integer[] catA) {
        this.catA = catA;
    }

    public Integer[] getCatB() {
        return catB;
    }

    public void setCatB(Integer[] catB) {
        this.catB = catB;
    }

    public Integer[] getCatC() {
        return catC;
    }

    public void setCatC(Integer[] catC) {
        this.catC = catC;
    }

    public Integer[] getCatD() {
        return catD;
    }

    public void setCatD(Integer[] catD) {
        this.catD = catD;
    }
   
    

    @Override
    public DISIDistribution clone() throws CloneNotSupportedException {
        DISIDistribution clonedObj = new DISIDistribution();
        if (this.catA != null){
            clonedObj.catA = this.catA;
        }
        
         if (this.catB != null){
            clonedObj.catB = this.catB;
        }
          if (this.catC != null){
            clonedObj.catC = this.catC;
        }
         if (this.catD != null){
            clonedObj.catD = this.catD;
        }
        
        return clonedObj;
        }

  
    

   

 
    
    
}

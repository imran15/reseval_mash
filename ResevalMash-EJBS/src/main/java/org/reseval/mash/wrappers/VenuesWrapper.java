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
@XmlType(name = "venueList", propOrder = {
    "key",
    "venue"
 })
@XmlRootElement(name = "venueList")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class VenuesWrapper implements Serializable {

    protected String key;
    protected List<Venue> venueList;

    public VenuesWrapper() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = id;
    }

    public List<Venue> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venueList = venueList;
    }

   
   

    @Override
    public VenuesWrapper clone() throws CloneNotSupportedException {
        VenuesWrapper venue = new VenuesWrapper();
        
        if (this.key != null){
            venue.key = this.key;
        }
        if (this.venueList != null){
            venue.venueList= this.venueList;
            
        }
        
        return venue;
    }

    


}

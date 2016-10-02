/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.wrappers;

import java.io.Serializable;
import java.util.List;
import org.reseval.mash.beans.QueryMapper;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.reseval.mash.beans.Researcher;

/**
 *
 * @author Muhammad Imran
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryWrapper", propOrder = {
    "key",
    "dataRequest",
    "mappersList",
    "researcherList"
})
@XmlRootElement(name = "queryWrapper")
@JsonSerialize(include = Inclusion.NON_DEFAULT)
public class QueryMapperWrapper implements Serializable {

    String key;
    String dataRequest;
    List<QueryMapper> mappersList;
    List<Researcher> researcherList;

    public List<Researcher> getResearcherList() {
        return researcherList;
    }

    public void setResearcherList(List<Researcher> researcherList) {
        this.researcherList = researcherList;
    }

    public QueryMapperWrapper() {
        this.dataRequest = "no";
    }

    public String getDataRequest() {
        return dataRequest;
    }

    public void setDataRequest(String dataRequest) {
        this.dataRequest = dataRequest;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<QueryMapper> getMappersList() {
        return mappersList;
    }

    public void setMappersList(List<QueryMapper> mappersList) {
        this.mappersList = mappersList;
    }

    @Override
    public QueryMapperWrapper clone() throws CloneNotSupportedException {
        QueryMapperWrapper clonedWrapper = new QueryMapperWrapper();
        if (this.mappersList != null) {
            clonedWrapper.mappersList = this.mappersList;
        }
        if (this.key != null) {
            clonedWrapper.key = this.key;
        }

        if (this.dataRequest != null) {
            clonedWrapper.dataRequest = this.dataRequest;
        }

        return clonedWrapper;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueryMapperWrapper other = (QueryMapperWrapper) obj;
        if (this.key != other.key && (this.key == null || !this.key.equals(other.key))) {
            return false;
        }
        if (this.mappersList != other.mappersList && (this.mappersList == null || !this.mappersList.equals(other.mappersList))) {
            return false;
        }
        if (this.dataRequest != other.dataRequest && (this.dataRequest == null || !this.dataRequest.equals(other.dataRequest))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {

        String result = "";
        for (int i = 0; i < mappersList.size(); i++) {
            result += mappersList.get(i).toString() + "\n";
        }

        return result;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import org.reseval.mash.beans.Department;
import java.util.List;
import javax.ejb.Local;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.beans.Faculty;
import org.reseval.mash.beans.Rank;
import org.reseval.mash.beans.Sector;

import org.reseval.mash.beans.University;
import org.reseval.mash.wrappers.ResearchersWrapper;


/**
 *
 * @author Muhammad Imran
 */
@Local
public interface ItalianSourceLogicLocal {
    
    
     public List<University> autoCompleteUName(String input);

    public List<Department> getAllDeptByUniID(Long id);

    public List<Faculty> getAllfacultiesByUniID(Long id);
    
    public List<Rank> autoCompleteRank(String input);
    
    public List<Sector> autoCompleteSectorCode(String input);
    
    public List<Sector> autoCompleteSectorName(String input);
    
    public String getUniNamebyID(Long id);

//    public List<Researcher> getAllResearchers(Integer no);
//    
//    public List<Researcher> getResearchersByUni(Long uniID);
//    
//    public List<Researcher> getResearchersByQuery(List<QueryMapper> queryList);
    
   // public ResearcherList getResearchersThroughCache(Long id, QueryMapperWrapper queryWrapper);
    
    public ResearchersWrapper getResearcherThroughCache(String key, QueryMapperWrapper queryWrapper);
}

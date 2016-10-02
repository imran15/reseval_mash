/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.facades.remote;

import org.reseval.mash.beans.Department;
import java.util.List;
import org.reseval.mash.beans.Researcher;

import javax.ejb.Remote;
import org.reseval.mash.beans.Faculty;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Rank;
import org.reseval.mash.beans.Sector;
import org.reseval.mash.beans.University;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Remote
public interface ItalianSourceFacadeRemote {

    /* void create(Departments departments);
    
    void edit(Departments departments);
    
    void remove(Departments departments);
    
    Departments find(Object id);
    
    List<Departments> findAll();
    
    List<Departments> findRange(int[] range);*/
    public List<University> autoCompleteUni(String partialName);

    public List<Department> getAllDeptByUnivID(Long id);

    public List<Faculty> getAllFacByUniID(Long id);
    
    public List<Rank> autoCompleteforRanks(String input);
    
    public List<Sector> autoCompleteforSectorsCode(String input);
    
    public List<Sector> autoCompleteforSectorsName(String input);

    public ResearchersWrapper getResearchersByQuery(List<QueryMapper> query);
    
     public String getUniNamebyID(Long id);

    public List<Researcher> getAllResearchers(Integer no);

    public List<Researcher> getResearcherByUniId(Long uniID);
    //  public List<Researcher> getResearchersByQuery(List<QueryMapper> query);
    //  public ResearcherList getResearchersByQuery2(List<QueryMapper> query);
}

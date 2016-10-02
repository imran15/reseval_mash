/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import org.reseval.mash.beans.Department;
import java.util.List;
import javax.ejb.Local;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Local
public interface ResearcherLogicLocal {

    public List<Researcher> autoCompleteMASResearcher(String name);
    
    public List<Researcher> autoCompleteDBLPResearcher(String name);

    public List<Researcher> getAllResearchers(Integer no);

    public List<Researcher> getMASCoAuthors(ResearchersWrapper resWrapper);

    public List<Researcher> getDBLPCoAuthors(ResearchersWrapper resWrapper);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.facades.remote;

import org.reseval.mash.entities.Researchers;
import java.util.List;
import javax.ejb.Remote;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Remote
public interface ResearchersFacadeRemote {

    /*void create(Researchers researchers);

    void edit(Researchers researchers);

    void remove(Researchers researchers);

    Researchers find(Object id);

    List<Researchers> findAll();

    List<Researchers> findRange(int[] range);

    int count();*/
    
    
    public List<Researcher> getMASCoAuthors(ResearchersWrapper researchers);
    public List<Researcher> getDBLPCoAuthors(ResearchersWrapper researchers);
    public List<Researcher> getCiters(ResearchersWrapper researchers);
    public List<Researcher> getAllResearchers(Integer no);

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.facades.remote;

import org.reseval.mash.beans.Department;
import java.util.List;
import org.reseval.mash.beans.Researcher;

import javax.ejb.Remote;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Remote
public interface CitationSourceFacadeRemote {

//    public ResearchersWrapper getPublicationsVenue(List<Long> researchersIds);
    
    public ResearchersWrapper getSelfCitations(ResearchersWrapper resWrapper);
    

}

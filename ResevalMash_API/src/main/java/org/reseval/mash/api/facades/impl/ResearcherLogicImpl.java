/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades.impl;

import org.reseval.mash.beans.Researcher;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.reseval.mash.api.facades.ResearcherLogicLocal;

import org.reseval.mash.facades.remote.ResearchersFacadeRemote;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class ResearcherLogicImpl implements ResearcherLogicLocal {
    @EJB
    private ResearchersFacadeRemote researcherRemote;
    
    

    @Override
    public List<Researcher> getAllResearchers(Integer no_of_reseachers) {
        return researcherRemote.getAllResearchers(no_of_reseachers);
    }

    @Override
    public List<Researcher> autoCompleteMASResearcher(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Researcher> autoCompleteDBLPResearcher(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Researcher> getMASCoAuthors(ResearchersWrapper resWrapper) {
        return researcherRemote.getMASCoAuthors(resWrapper);
    }

    @Override
    public List<Researcher> getDBLPCoAuthors(ResearchersWrapper resWrapper) {
         return researcherRemote.getDBLPCoAuthors(resWrapper);
    }

   
 
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import javax.ejb.Local;

import org.reseval.mash.wrappers.ResearchersWrapper;


/**
 *
 * @author Muhammad Imran
 */
@Local
public interface CitationLogicLocal {

    public ResearchersWrapper getSelfCitations(ResearchersWrapper resWrapper);
    public ResearchersWrapper getCitations(ResearchersWrapper resWrapper);
}

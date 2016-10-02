/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.reseval.mash.api.facades;

import java.util.List;
import javax.ejb.Local;
import org.reseval.mash.beans.QueryMapper;

import org.reseval.mash.wrappers.ResearchersWrapper;


/**
 *
 * @author Muhammad Imran
 */
@Local
public interface GIndexLogicLocal {

    public ResearchersWrapper getGIndex(ResearchersWrapper resWrapper);
}

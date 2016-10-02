/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.CitationLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("citationSource")
@Stateless
public class CitationResource {

    @Context
    private UriInfo context;
    @EJB
    private CitationLogicLocal _citationSourceLocal;
    private List<Researcher> _resList = null;
    private QueryMapperWrapper _queryWrapper = null;

    /** Creates a new instance of ItalianSourceResource */
    public CitationResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/setResearchers")
    public Response setResearchers(QueryMapperWrapper queryWrapper) {
        _queryWrapper = queryWrapper;
        ResponseWrapper response = new ResponseWrapper();
        if ((queryWrapper.getKey().equals(""))) { // if key is not provided
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("key not found in the request.");
            return Response.ok(response).build();

        } else {// key is provided in the request


            if (isDataAvailableinRequest(queryWrapper)) { // if data is available in request

                ResearchersWrapper resWrapper = getDataFromRequest(queryWrapper);
                putDatainCache(resWrapper, queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setCacheKey(queryWrapper.getKey());
                response.setMessage("Request has been completed successfully with caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { // if data is not in cache, then check if data is provided in request
                ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
                if (cacheResWrapper == null) {
                    response.setRequestStatus(Boolean.FALSE);
                    response.setMessage("Key Found. Data does not exist. Provide data and key for caching.");
                    return Response.ok(response).build();
                } else {
                    response.setRequestStatus(Boolean.TRUE);
                    response.setCacheKey(queryWrapper.getKey());
                    response.setMessage("Key and Data found in the cache.");
                    return Response.ok(response).build();
                }
            }

        }


    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/getSelfCitations")
    public Response getSelfCitations(QueryMapperWrapper queryWrapperObj) {

        ResponseWrapper response = new ResponseWrapper();
        QueryMapperWrapper queryWrapper = queryWrapperObj;

        if (!(queryWrapper.getKey().equals(""))) {

            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
            if (cacheResWrapper == null) {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed. Researchers not found in the cache.");
                return Response.ok(response).build();
            } else { // if researcher are in the cahce
                ResearchersWrapper resWrapper = _citationSourceLocal.getSelfCitations(cacheResWrapper);
                resWrapper = _citationSourceLocal.getCitations(resWrapper);
                response.setCacheKey(queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request has completed successfully.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();

            }

        }
        return Response.ok(response).build();

    }

    private boolean isDataAvailableinRequest(QueryMapperWrapper queryWrapperObj) {

        if (queryWrapperObj.getResearcherList() == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isDataAvailbelinCache(QueryMapperWrapper queryWrapper) {
        ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
        if (cacheResWrapper == null) {
            return false;
        } else {
            return true;
        }
    }

    private ResearchersWrapper getDataFromRequest(QueryMapperWrapper queryWrapperObj) {
        ResearchersWrapper resWrapper = new ResearchersWrapper();
        resWrapper.setResearchers(queryWrapperObj.getResearcherList());
        resWrapper.setKey(queryWrapperObj.getKey());
        return resWrapper;
    }

    private ResearchersWrapper getDatafromCache(QueryMapperWrapper queryWrapper) {
        return CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
    }

    private void putDatainCache(ResearchersWrapper resWrapper, String key) {
        CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
    }
}
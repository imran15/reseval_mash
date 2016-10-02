/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DBLPSourceLogicLocal;
import org.reseval.mash.api.facades.MASSourceLogicLocal;
import org.reseval.mash.api.facades.ResearcherLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("researchers")
@Stateless
public class ResearcherResource {

    @Context
    private UriInfo context;
    @EJB
    private ResearcherLogicLocal _researcherLocal;
    @EJB
    private MASSourceLogicLocal _masEJBLocal;
    @EJB
    private DBLPSourceLogicLocal _dblpEJBLocal;

    /** Creates a new instance of ResearcherResource */
    public ResearcherResource() {
    }

    /*This is a autocomplete service, which returns suggestions of researchers
     *names based on user selected source (e.g, dblp, microsoft academic etc) 
     */
    @GET
    @Produces("application/json")
    @Path("/autocomplete/{source}")
    public Response researchersAutocomplete(@PathParam("source") String source, @QueryParam("input") String input) {

        if (source.equalsIgnoreCase("MAS")) {

            List<Researcher> researchers = _masEJBLocal.autoCompleteResearcherName(input);
            for (int i = 0; i < researchers.size(); i++) {
                researchers.get(i).setMasID(researchers.get(i).getId());
            }
            return Response.ok(researchers).build();

        } else if (source.equalsIgnoreCase("DBLP")) {

            List<Researcher> researchers = _dblpEJBLocal.autoCompleteResearcherName(input);
            for (int i = 0; i < researchers.size(); i++) {
                researchers.get(i).setDblpID(researchers.get(i).getId());
            }
            return Response.ok(researchers).build();
        }

        return Response.ok("Invalid Request").build();
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/co-authors")
    public Response getCoAuthors(QueryMapperWrapper queryWrapper) {

        ResponseWrapper response = new ResponseWrapper();

        if ((queryWrapper.getKey().equals(""))) { // if key is not provided, then check if data is available in the request
            if (isDataAvailableinRequest(queryWrapper)) { // if data is in the request

                ResearchersWrapper resWrapper = getDBCoAuthors(queryWrapper);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request has been completed successfully without caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { //Not key nor data is provided in the request

                response.setMessage("No key and data is provided.");
                response.setRequestStatus(Boolean.FALSE);
                return Response.ok(response).build();
            }

        } else {// key is provided in the request

            if (isDataAvailbelinCache(queryWrapper)) { // if data is available in cache

                ResearchersWrapper resWrapper = getDatafromCache(queryWrapper);
                queryWrapper.setResearcherList(resWrapper.getResearchers());
                resWrapper = getDBCoAuthors(queryWrapper);
                putDatainCache(resWrapper, queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setCacheKey(queryWrapper.getKey());
                response.setMessage("Request has been completed successfully with caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { // if data is not in cache, then check if data is provided in request
                if (isDataAvailableinRequest(queryWrapper)) {

                    ResearchersWrapper resWrapper = getDBCoAuthors(queryWrapper);
                    putDatainCache(resWrapper, queryWrapper.getKey());
                    response.setRequestStatus(Boolean.TRUE);
                    response.setCacheKey(queryWrapper.getKey());
                    response.setMessage("Request has been completed successfully with caching.");
                    if (queryWrapper.getDataRequest().equals("yes")) {
                        response.setDataObject(resWrapper);
                    }
                    return Response.ok(response).build();
                } else { // Not key nor data is provided
                    response.setMessage("Request failed. No key and data is provided.");
                    response.setRequestStatus(Boolean.FALSE);
                    return Response.ok(response).build();
                }
            }

        }
    }

    private boolean isDataAvailableinRequest(QueryMapperWrapper queryWrapperObj) {
        if (queryWrapperObj.getResearcherList().isEmpty()) {
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

    private ResearchersWrapper getDatafromCache(QueryMapperWrapper queryWrapper) {
        return CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
    }

    private void putDatainCache(ResearchersWrapper resWrapper, String key) {
        CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
    }

    @POST
    @Produces("application/json")
    @Path("/citers")
    public Response getCiters(QueryMapperWrapper queryWrapper) {


        return Response.ok("").build();
    }

    private ResearchersWrapper getDBCoAuthors(QueryMapperWrapper queryWrapper) {

        if (queryWrapper.getResearcherList().get(0).getMasID() != null) { //if researchers belongs to MAS database
            ResearchersWrapper resWrapper = new ResearchersWrapper();
            resWrapper.setResearchers(queryWrapper.getResearcherList());
            List<Researcher> researchers = _researcherLocal.getMASCoAuthors(resWrapper);
            resWrapper.setResearchers(researchers);
            return resWrapper;

        } else if (queryWrapper.getResearcherList().get(0).getDblpID() != null) {// if researchers belongs to DBLP database

            ResearchersWrapper resWrapper = new ResearchersWrapper();
            resWrapper.setResearchers(queryWrapper.getResearcherList());
             List<Researcher> researchers = _researcherLocal.getDBLPCoAuthors(resWrapper);
            resWrapper.setResearchers(researchers);
            return resWrapper;
        }

        throw new UnsupportedOperationException("Not yet implemented: Probably invalid or missing source ID (e.g., masID, dblpID)");
    }
//    @GET
//    @Produces("application/json")
//    @Path("/")
//    public Response getAllResearchers(@QueryParam("fetch") Integer no_of_researchers, @QueryParam("data") String data) {
//        List<Researcher> researchers = _researcherLocal.getAllResearchers(no_of_researchers);
//        ResponseWrapper response = new ResponseWrapper();
//        response.setCacheKey("12345");
//        
//        if (researchers != null)
//        {
//            response.setRequestStatus(Boolean.TRUE);
//            response.setMessage("Request is successfully completed. "+ researchers.size() + " Researchers are retrived.");
//            if (data.equals("yes"))
//            response.setDataObject(researchers);
//        }
//        else {
//            response.setMessage("Request does not completed.");
//        }
//        
//        return Response.ok(response).build();
//    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import org.reseval.mash.api.gs.crawler.GS_Crawler;
import java.io.IOException;
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
import javax.ws.rs.core.Response;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.wrappers.ResearchersWrapper;


/**
 * REST Web Service
 *
 * @author Kaiser
 */
@Path("GoogleScholarLive")
@Stateless
public class GoogleScholarResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GoogleScholarResource
     */
    public GoogleScholarResource() {
    }

    /**
     * Retrieves representation of an instance of service.GoogleScholarResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GoogleScholarResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
    
    
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/getPublications")
    public Response getPublications(QueryMapperWrapper queryWrapperObj) throws IOException, InterruptedException{
     ResponseWrapper response = new ResponseWrapper();
        QueryMapperWrapper queryWrapper = queryWrapperObj;

        if ((queryWrapper.getKey().equals(""))) { // if key is not provided
            if (isDataAvailableinRequest(queryWrapper)) { // if data is in the request

                ResearchersWrapper resWrapper = getDBPublications(queryWrapper);
                
                // responseOK(queryWrapper, resWrapper); //make response OK and no caching
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request has been completed successfully without caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { //Not key nor data is provided in the request

                response.setMessage("Not key nor data is provided.");
                response.setRequestStatus(Boolean.FALSE);
                return Response.ok(response).build();
            }

        } else {// key is provided in the request
                //no more checks on cache
             //  check if data is provided in request
                if (isDataAvailableinRequest(queryWrapper)) {
                    ResearchersWrapper resWrapper = getDBPublications(queryWrapper);
                   
                    response.setRequestStatus(Boolean.TRUE);
                    
                    response.setMessage("Request has been completed successfully with caching.");
                    if (queryWrapper.getDataRequest().equals("yes")) {
                        response.setDataObject(resWrapper);
                    }
                    return Response.ok(response).build();
                } else { // Not key nor data is provided
                    response.setMessage("Not key nor data is provided.");
                    response.setRequestStatus(Boolean.FALSE);
                    return Response.ok(response).build();
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
     
      private ResearchersWrapper getDBPublications(QueryMapperWrapper queryWrapperObj) throws IOException, InterruptedException {
          GS_Crawler gsc=new GS_Crawler();
        ResearchersWrapper resWrapper = new ResearchersWrapper();
        //resWrapper.setResearchers(queryWrapperObj.getResearcherList());
        
        
        //I call my logic which has the name of the researcher as parameter
        resWrapper = gsc.getPublications(queryWrapperObj.getResearcherList().get(0).getFullName());
        return resWrapper;
    }
    
}

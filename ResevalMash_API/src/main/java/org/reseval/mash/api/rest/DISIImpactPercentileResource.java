/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DISIImpactPercentileLocal;
import org.reseval.mash.api.facades.VenueImpactComputationLocal;
import org.reseval.mash.api.stubs.beans.DISIDistribution;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("disiImpactPercentile")
@Stateless
public class DISIImpactPercentileResource {

    @Context
    private UriInfo context;
    @EJB
    private DISIImpactPercentileLocal _disiImpactPercentile;

    /** Creates a new instance of ItalianSourceResource */
    public DISIImpactPercentileResource() {
    }

    @GET
    @Produces("application/json")
    @Path("/setDistribution")
    public Response setDistribution(@DefaultValue("-1") @QueryParam("key") String key, @DefaultValue("no") @QueryParam("data") String dataReq) {
        ResponseWrapper response = new ResponseWrapper();

        if (!(key.equals("-1"))){
            //ResearchersWrapper resWrapper = _venueImpactEJBLocal.getImpact(key);
            DISIDistribution distribution = _disiImpactPercentile.setDistribution(key);
            if (distribution != null) {
                response.setCacheKey(key);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request is completed successfully.");
                if (dataReq.equals("yes")) {
                    response.setDataObject(distribution);
                }
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed.");
            }
        } else {
            response.setMessage("Key is not provided.");
            response.setRequestStatus(Boolean.FALSE);
        }

        return Response.ok(response).build();

    }
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/setDistribution2")
    public Response setDistribution2(QueryMapperWrapper queryWrapperObj) {
        ResponseWrapper response = new ResponseWrapper();

        if (!(queryWrapperObj.getKey().equals("-1"))){
            //ResearchersWrapper resWrapper = _venueImpactEJBLocal.getImpact(key);
            DISIDistribution distribution = _disiImpactPercentile.setDistribution(queryWrapperObj.getKey());
            if (distribution != null) {
                response.setCacheKey(queryWrapperObj.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request is completed successfully.");
                if (queryWrapperObj.getDataRequest().equals("yes")) {
                    response.setDataObject(distribution);
                }
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed.");
            }
        } else {
            response.setMessage("Key is not provided.");
            response.setRequestStatus(Boolean.FALSE);
        }

        return Response.ok(response).build();

    }
    
    @GET
    @Produces("application/json")
    @Path("/getPercentile")
    public Response getPercentile(@DefaultValue("-1") @QueryParam("key") String key, @DefaultValue("no") @QueryParam("data") String dataReq) {
        ResponseWrapper response = new ResponseWrapper();

        if (!(key.equals("-1"))){
            //ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(key);
            //if (resWrapper.getResearchers().get(0).getMetrics().getMetricsStandard().)
            ResearchersWrapper researchers =  _disiImpactPercentile.getPercentile(key);
            response.setCacheKey(key);
            response.setMessage("Request successfully completed.");
            response.setRequestStatus(Boolean.TRUE);
            if (dataReq.equals("yes"))
            response.setDataObject(researchers);
            
            return Response.ok(response).build();
        } else {
            response.setMessage("Key is not provided.");
            response.setRequestStatus(Boolean.FALSE);
        }

        return Response.ok(response).build();

    }

  
}

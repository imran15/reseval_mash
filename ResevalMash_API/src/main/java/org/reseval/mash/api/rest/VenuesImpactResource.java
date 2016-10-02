/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.VenueImpactComputationLocal;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("venueImpact")
@Stateless
public class VenuesImpactResource {

    @Context
    private UriInfo context;
    @EJB
    private VenueImpactComputationLocal _venueImpactEJBLocal;

    /** Creates a new instance of ItalianSourceResource */
    public VenuesImpactResource() {
    }

    @GET
    @Produces("application/json")
    @Path("/getImpact")
    public Response getVenueImpact(@DefaultValue("-1") @QueryParam("key") String key,
            @DefaultValue("no") @QueryParam("data") String dataReq) {
        ResponseWrapper response = new ResponseWrapper();

        if (!(key.equals("-1"))) {
             ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(key);
             
            if (resWrapper != null) {
                //resWrapper = _venueImpactEJBLocal.getVenueImpact(resWrapper);
                resWrapper = _venueImpactEJBLocal.getImpactNew(key);
                //CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
                response.setCacheKey(key);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request is completed successfully.");
                if (dataReq.equals("yes")) {
                    response.setDataObject(resWrapper);
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
    @Path("/setVenueWeights")
    public Response setVenuesWeights(@DefaultValue("-1") @QueryParam("key") String cacheKey) {

        ResponseWrapper response = new ResponseWrapper();

        if (cacheKey != "-1") {
            List<Venue> venues = _venueImpactEJBLocal.setVenueWeights(cacheKey);
            if (venues != null) {
                response.setCacheKey(cacheKey);
                response.setMessage("Request complected successfully.");
                response.setRequestStatus(Boolean.TRUE);
            } else {

                response.setMessage("Request is not complected. Possible that venues rankings are not present in cache.");
                response.setRequestStatus(Boolean.FALSE);
            }
        } else {
            response.setMessage("Key is not provided.");
            response.setRequestStatus(Boolean.FALSE);
        }

        return Response.ok(response).build();

    }
}


//  BACKUP on 26 Marche
//@GET
//    @Produces("application/json")
//    @Path("/getImpact")
//    public Response getVenueImpact(@DefaultValue("-1") @QueryParam("key") String key,
//            @DefaultValue("no") @QueryParam("data") String dataReq) {
//        ResponseWrapper response = new ResponseWrapper();
//
//        if (!(key.equals("-1"))) {
//            //ResearchersWrapper resWrapper = _venueImpactEJBLocal.getImpact(key);
//            ResearchersWrapper resWrapper = _venueImpactEJBLocal.getImpactNew(key);
//            if (resWrapper != null) {
//                response.setCacheKey(key);
//                response.setRequestStatus(Boolean.TRUE);
//                response.setMessage("Request is completed successfully.");
//                if (dataReq.equals("yes")) {
//                    response.setDataObject(resWrapper);
//                }
//            } else {
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request is not completed.");
//            }
//        } else {
//            response.setMessage("Key is not provided.");
//            response.setRequestStatus(Boolean.FALSE);
//        }
//
//        return Response.ok(response).build();
//
//    }
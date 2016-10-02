/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

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
import org.reseval.mash.api.facades.VenueLogicLocal;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.VenuesWrapper;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("venuesRanking")
@Stateless
public class VenuesRankingResource {
    
    @Context
    private UriInfo context;
    @EJB
    private VenueLogicLocal _venueEJBLocal;

    /** Creates a new instance of ItalianSourceResource */
    public VenuesRankingResource() {
    }
    
    @GET
    @Produces("application/json")
    @Path("/getVenuesRanking")
    public Response getVenuesRanking(@DefaultValue("-1") @QueryParam("key") String key, @DefaultValue("no") @QueryParam("data") String dataReq) {
        ResponseWrapper response = new ResponseWrapper();
       if (!(key.equals("-1"))){           
            //VenuesWrapper venueWrapper = _venueEJBLocal.getVenuesRanking(key);
           VenuesWrapper venueWrapper = _venueEJBLocal.getVenuesRankingUNITN(key);
            if (venueWrapper != null) {
                response.setCacheKey(venueWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage(venueWrapper.getVenueList().size() + " Venues and their ranking are retrieved.");
                if (dataReq.equals("yes")) {
                    response.setDataObject(venueWrapper);
                }
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed.");
            }
        } else {
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("Key is not provided.");
        }
        return Response.ok(response).build();
        
    }
}

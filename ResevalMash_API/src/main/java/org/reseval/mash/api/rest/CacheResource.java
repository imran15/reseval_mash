/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import org.reseval.mash.api.cache.CacheMemoryManager;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("cache")
@Stateless
public class CacheResource {

    @Context
    private UriInfo context;
   
    /** Creates a new instance of ItalianSourceResource */
    public CacheResource() {
    }

    @GET
    @Path("/shutdown")
    public void shutdownCache() {
        CacheMemoryManager.getInstance().getCacheManager().shutdown();
        //return Response.ok(response).build();

    }
    
    @GET
    @Path("/remove/object")
    public void removeObject(@QueryParam("key") String key) {
        CacheMemoryManager.getInstance().removeElement(key);
    }
}

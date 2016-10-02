/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.ArrayList;
import org.reseval.mash.beans.Department;
import org.reseval.mash.beans.University;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.facades.ItalianSourceLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Faculty;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Rank;
import org.reseval.mash.beans.Sector;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("italianSource")
@Stateless
public class ItalianSourceResource {

    @Context
    private UriInfo context;
    @EJB
    private ItalianSourceLogicLocal _italianEJBLocal;

    /** Creates a new instance of ItalianSourceResource */
    public ItalianSourceResource() {
    }

    @GET
    @Produces("application/json")
    @Path("university/autocomplete")
    public Response autoCompleteName(@QueryParam("input") String input) {
        List<University> names = _italianEJBLocal.autoCompleteUName(input);
        return Response.ok(names).build();
    }

    @GET
    @Produces("application/json")
    @Path("university/{id}/departments")
    public Response getAllDepartments(@PathParam("id") Long idUni) {
        List<Department> depts = _italianEJBLocal.getAllDeptByUniID(idUni);
        return Response.ok(depts).build();
    }

    @GET
    @Produces("application/json")
    @Path("university/{id}/faculties")
    public Response getAllFaculties(@PathParam("id") Long idUni) {
        List<Faculty> facs = _italianEJBLocal.getAllfacultiesByUniID(idUni);
        return Response.ok(facs).build();
    }

    @GET
    @Produces("application/json")
    @Path("rank/autocomplete")
    public Response getRankAutocomplete(@QueryParam("input") String input) {
        List<Rank> ranks = _italianEJBLocal.autoCompleteRank(input);
        return Response.ok(ranks).build();
    }

    @GET
    @Produces("application/json")
    @Path("sector/code/autocomplete")
    public Response autocompleteSectorCode(@QueryParam("input") String input) {
        List<Sector> sectors = _italianEJBLocal.autoCompleteSectorCode(input);
        return Response.ok(sectors).build();
    }

    @GET
    @Produces("application/json")
    @Path("sector/name/autocomplete")
    public Response autocompleteSectorName(@QueryParam("input") String input) {
        List<Sector> sectors = _italianEJBLocal.autoCompleteSectorName(input);
        return Response.ok(sectors).build();
    }

    @GET
    @Produces("application/json")
    @Path("/getResearchers")
    public Response getResearchers(
            @DefaultValue("-1") @QueryParam("fetch") Integer no_of_researchers,
            @DefaultValue("-1") @QueryParam("uniID") Long uniID,
            @DefaultValue("-1") @QueryParam("depID") Long depID,
            @DefaultValue("-1") @QueryParam("secID") Long secID,
            @DefaultValue("-1") @QueryParam("rankID") Long rankID,
            @DefaultValue("-1") @QueryParam("facID") Long facID,
            @DefaultValue("-1") @QueryParam("key") String key,
            @DefaultValue("no") @QueryParam("data") String dataReq
            ) {
        ResponseWrapper response = new ResponseWrapper();
        if (!(key.equals("-1"))) {

            List<QueryMapper> queryList = new ArrayList<QueryMapper>();
            if (no_of_researchers != -1) {
                QueryMapper query = new QueryMapper("fetch", no_of_researchers.toString());
                queryList.add(query);
            }
            if (uniID != -1) {
                QueryMapper query = new QueryMapper("uniID", uniID.toString());
                queryList.add(query);
            }
            if (depID != -1) {
                QueryMapper query = new QueryMapper("depID", depID.toString());
                queryList.add(query);
            }
            if (secID != -1) {
                QueryMapper query = new QueryMapper("secID", secID.toString());
                queryList.add(query);
            }
            if (rankID != -1) {
                QueryMapper query = new QueryMapper("rankID", rankID.toString());
                queryList.add(query);
            }
            if (facID != -1) {
                QueryMapper query = new QueryMapper("facID", facID.toString());
                queryList.add(query);
            }

            QueryMapperWrapper queryWrapper = new QueryMapperWrapper();
            queryWrapper.setMappersList(queryList);
            ResearchersWrapper researchers = _italianEJBLocal.getResearcherThroughCache(key, queryWrapper);
            if (researchers != null) {
                response.setCacheKey(key);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage(researchers.getResearchers().size() + " Researchers have been retrieved.");
                if (dataReq.equals("yes")) {
                    response.setDataObject(researchers);
                }
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request does not completed.");
            }

        }
        else{
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("Key is not provided.");
        }
        return Response.ok(response).build();

    }
    
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/getResearchers2")
    public Response getResearchers2(QueryMapperWrapper queryWrapperObj) {

        ResponseWrapper response = new ResponseWrapper();
        QueryMapperWrapper queryWrapper = queryWrapperObj;
        if (!(queryWrapper.getKey().equals("-1"))) { 
            queryWrapper.setMappersList(queryWrapper.getMappersList());
            ResearchersWrapper researchers = _italianEJBLocal.getResearcherThroughCache(queryWrapper.getKey(), queryWrapper);
            if (researchers != null) {
                response.setCacheKey(queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage(researchers.getResearchers().size() + " Researchers have been retrieved.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(researchers);
                }
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request does not completed.");
            }

        }
        else{
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("Key is not provided.");
        }
        return Response.ok(response).build();

    }
    
}

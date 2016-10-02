/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.ArrayList;
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
import org.reseval.mash.api.facades.MASSourceLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResearcherResponseWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("masSource")
@Stateless
public class MASSourceResource {

    @Context
    private UriInfo context;
    @EJB
    private MASSourceLogicLocal _masEJBLocal;
    private ResearchersWrapper _resWrapper = null;

    /** Creates a new instance of ItalianSourceResource */
    public MASSourceResource() {
    }

    
     @GET
    @Produces("application/json")
    @Path("researcher/autocomplete")
    public Response autoCompleteName(@QueryParam("input") String input) {
        List<Researcher> researchers = _masEJBLocal.autoCompleteResearcherName(input);
//        List<ResearcherResponseWrapper> masWrapperList = new ArrayList<ResearcherResponseWrapper>();
//        ResearcherResponseWrapper aWrapper;
//        for (int i = 0; i < researchers.size(); i++) {
//            aWrapper = new ResearcherResponseWrapper();
//            aWrapper.setId(researchers.get(i).getId());
//            aWrapper.setName(researchers.get(i).getName());
//            aWrapper.setMasID(researchers.get(i).getMasID());
//            aWrapper.setNumofPubs(researchers.get(i).getPubsNumber());
//            
//            masWrapperList.add(aWrapper);
//        }
        return Response.ok(researchers).build();
    }
    
    
    @GET
    @Produces("application/json")
    @Path("/setResearchers")
    public Response setResearchers(@DefaultValue("-1") @QueryParam("key") String key,
            @DefaultValue("no") @QueryParam("data") String dataReq) {

        ResponseWrapper response = new ResponseWrapper();
        if (!(key.equals("-1"))) {
            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(key);
            if (cacheResWrapper == null) {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed. Researchers not found in cache.");
                return Response.ok(response).build();
            } else {
                _resWrapper = cacheResWrapper;
                response.setCacheKey(key);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request successfully completed.");
                if (dataReq.equals("yes")) {
                    response.setDataObject(cacheResWrapper);
                }
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
    @Path("/setResearchers2")
    public Response setResearchers2(QueryMapperWrapper queryWrapperObj) {

        ResponseWrapper response = new ResponseWrapper();
        //if key is provided in request call, then retrieve researchers from cache and proceed.
        if (!(queryWrapperObj.getKey().isEmpty())) {
            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
            if (cacheResWrapper == null) {
                if ((queryWrapperObj.getResearcherList() != null)) {
                    List<Researcher> researchersList = queryWrapperObj.getResearcherList();
                    ResearchersWrapper researcherWrapper = new ResearchersWrapper();
                    researcherWrapper.setResearchers(researchersList);
                    
                    putDatainCache(researcherWrapper, queryWrapperObj.getKey());
                    response.setCacheKey(queryWrapperObj.getKey());
                    response.setRequestStatus(Boolean.TRUE);
                    response.setMessage("Request successfully completed. Data is cached.");
                    if (queryWrapperObj.getDataRequest().equals("yes")) {
                        response.setDataObject(researcherWrapper);
                    }
                    return Response.ok(response).build();
                } else {
                    response.setRequestStatus(Boolean.FALSE);
                    response.setMessage("Request Failed. No data and key found.");
                    return Response.ok(response).build();
                }
            } else {//if found in cache
                response.setCacheKey(queryWrapperObj.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request successfully completed. Data found in cache.");
                if (queryWrapperObj.getDataRequest().equals("yes")) {
                    response.setDataObject(cacheResWrapper);
                }
                return Response.ok(response).build();
            }
        }// if key is not provided then no need to check researchers in request, because the purpose of SetResearcher method is
        // to cache the data, and data can not be cached without key.
        else {
            response.setCacheKey("");
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("Request failed. Key not found in the call.");
            return Response.ok(response).build();
        }

    }

    private void putDatainCache(ResearchersWrapper resWrapper, String key) {
        CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
    }

    @GET
    @Produces("application/json")
    @Path("/getPublications")
    public Response getPublications(@DefaultValue("-1") @QueryParam("key") String key,
            @DefaultValue("no") @QueryParam("data") String dataReq,
            @DefaultValue("-1") @QueryParam("startYear") String sYear,
            @DefaultValue(StaticVars.SQL_GreaterThanEqualTo) @QueryParam("startYearOp") String sYearOp,
            @DefaultValue("-1") @QueryParam("endYear") String eYear,
            @DefaultValue(StaticVars.SQL_LessThanEqualTo) @QueryParam("endYearOp") String eYearOp) {

        ResponseWrapper response = new ResponseWrapper();
        if (!(key.equals("-1"))) { // here either key should be provided or researchers
            if (_resWrapper != null) {
                List<QueryMapper> queryList = new ArrayList<QueryMapper>();
                if (!(sYear.equals("-1"))) {
                    QueryMapper query = new QueryMapper("startYear", sYear, sYearOp);
                    queryList.add(query);
                }
                if (!(eYear.equals("-1"))) {
                    QueryMapper query = new QueryMapper("endYear", eYear, eYearOp);
                    queryList.add(query);
                }
                QueryMapperWrapper queryWrapper = new QueryMapperWrapper();
                queryWrapper.setMappersList(queryList);
                //Here it should get researchers which are stored in cache by key provided.


                ResearchersWrapper resWrapper = _masEJBLocal.getPublications(_resWrapper, queryList);
                if (resWrapper != null) {
                    response.setCacheKey(key);
                    response.setRequestStatus(Boolean.TRUE);
                    response.setMessage("Publications data has been added to researchers.");
                    if (dataReq.equals("yes")) {
                        response.setDataObject(resWrapper);
                    }
                } else {
                    response.setRequestStatus(Boolean.FALSE);
                    response.setMessage("Request is not completed.");
                }
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
    @Path("/getPublications2")
    public Response getPublications(QueryMapperWrapper queryWrapperObj) {
        ResponseWrapper response = new ResponseWrapper();
        QueryMapperWrapper queryWrapper = queryWrapperObj;

        if ((queryWrapper.getKey().equals(""))) { // if key is not provided, then check if data is available in the request
            if (isDataAvailableinRequest(queryWrapper)) { // if data is in the request

                ResearchersWrapper resWrapper = getDBPublications(queryWrapper);
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request has been completed successfully without caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { //Not key nor data is provided in the request

                response.setMessage("Not key and data is provided.");
                response.setRequestStatus(Boolean.FALSE);
                return Response.ok(response).build();
            }

        } else {// key is provided in the request

            if (isDataAvailbelinCache(queryWrapper)) { // if data is available in cache

                ResearchersWrapper resWrapper = getDatafromCache(queryWrapper);
                queryWrapper.setResearcherList(resWrapper.getResearchers());
                resWrapper = getDBPublications(queryWrapper);
                putDatainCache(resWrapper, queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setCacheKey(queryWrapper.getKey());
                response.setMessage("Key found, data found in cache. Request has been completed successfully with caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { // if data is not in cache, then check if data is provided in request
                if (isDataAvailableinRequest(queryWrapper)) {

                    ResearchersWrapper resWrapper = getDBPublications(queryWrapper);
                    putDatainCache(resWrapper, queryWrapper.getKey());
                    response.setRequestStatus(Boolean.TRUE);
                      response.setCacheKey(queryWrapper.getKey());
                    response.setMessage("Key found, data found in request. Request has been completed successfully with caching.");
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

    private ResearchersWrapper getDBPublications(QueryMapperWrapper queryWrapperObj) {
        ResearchersWrapper resWrapper = new ResearchersWrapper();
        resWrapper.setResearchers(queryWrapperObj.getResearcherList());
        resWrapper = _masEJBLocal.getPublications(resWrapper, queryWrapperObj.getMappersList());
        return resWrapper;
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
    // setresearcher2 code
//        ResponseWrapper response = new ResponseWrapper();
//        //if key is provided in request call, then retrieve researchers from cache and proceed.
//        if (!(queryWrapperObj.getKey().isEmpty())) {
//            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//            if (cacheResWrapper == null) {
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request is not completed. Researchers not found in cache.");
//                return Response.ok(response).build();
//            } else {
//                _resWrapper = cacheResWrapper;
//                response.setCacheKey(queryWrapperObj.getKey());
//                response.setRequestStatus(Boolean.TRUE);
//                response.setMessage("Request successfully completed.");
//                if (queryWrapperObj.getDataRequest().equals("yes")) {
//                    response.setDataObject(cacheResWrapper);
//                }
//            }
//
//            return Response.ok(response).build();
//        }
//
//        //if key is not provided in request call then check researchers in mappersList
//        if (!(queryWrapperObj.getMappersList().isEmpty())) {
//
//            if (queryWrapperObj.getMappersList().get(0).getParamName().equals("ResearchersList")) {
//                List<Researcher> researchersList = (List<Researcher>) queryWrapperObj.getMappersList().get(0).getParamValue();
//                ResearchersWrapper researcherWrapper = new ResearchersWrapper();
//                researcherWrapper.setResearchers(researchersList);
//                _resWrapper = researcherWrapper;
//                response.setCacheKey("");
//                response.setRequestStatus(Boolean.TRUE);
//                response.setMessage("Request successfully completed.");
//                if (queryWrapperObj.getDataRequest().equals("yes")) {
//                    response.setDataObject(_resWrapper);
//                }
//                return Response.ok(response).build();
//
//            } else {
//
//                response.setCacheKey("");
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request not completed. Researchers are not found in POST call.");
//                return Response.ok(response).build();
//            }
//        } else {
//
//            response.setCacheKey("");
//            response.setRequestStatus(Boolean.FALSE);
//            response.setMessage("Request not completed. Researchers are not found in POST call.");
//            return Response.ok(response).build();
//        }
    //  }
//    @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    @Path("/getPublications2")
//    public Response getPublications2(QueryMapperWrapper queryWrapperObj) {
//
//        ResponseWrapper response = new ResponseWrapper();
//        QueryMapperWrapper queryWrapper = queryWrapperObj;
//        /*
//         * First of all it has to check whether the key is provided in request, bcoz there 
//         * might be the case that no key is provided but the list of researchers.
//         */
//        if (!(queryWrapper.getKey().equals("-1"))) {
//
//            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//            if (cacheResWrapper == null) {
//                
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request is not completed. Researchers not found in cache.");
//                return Response.ok(response).build();
//            } else { // if researcher are in the cahce
//                String queryCacheKey = queryWrapper.getKey() + StaticVars.CACHE_MasPub_QUERY_KEY;
//                QueryMapperWrapper cachedQuery = CacheMemoryManager.getInstance().getQueryWrapper(queryCacheKey);
//                if (cachedQuery == null) { //if there is no publication query in the cache
//                    ResearchersWrapper resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has completed successfully.");
//                    if (queryWrapper.getDataRequest().equals("yes")) {
//                        response.setDataObject(resWrapper);
//                    }
//                    return Response.ok(response).build();
//                    // if there is a publication query in cache then
//                } else if (cachedQuery.getMappersList().equals(queryWrapperObj.getMappersList())) {
//                    ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//                    if (cacheResWrapper.getResearchers().get(0).getPublications() == null) {
//                        resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has completed successfully.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    }
//                    else{
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has completed successfully.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    }
//
//
//                } else { // if there is a key publication query in the cache but is not matched with new query then
//                    ResearchersWrapper resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has completed successfully.");
//                    if (queryWrapper.getDataRequest().equals("yes")) {
//                        response.setDataObject(resWrapper);
//                    }
//                    return Response.ok(response).build();
//                }
//            }
//        } else {
//
//            response.setMessage("Key is not provided.");
//            response.setRequestStatus(Boolean.FALSE);
//            return Response.ok(response).build();
//        }
//        //return Response.ok(response).build();
//    }
//     @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    @Path("/getPublications2")
//    public Response getPublications2(QueryMapperWrapper queryWrapperObj) {
//
//        ResponseWrapper response = new ResponseWrapper();
//        QueryMapperWrapper queryWrapper = queryWrapperObj;
//        /*
//         * First of all it has to check whether the key is provided in request, bcoz there 
//         * might be the case that no key is provided but the list of researchers.
//         */
//        if (!(queryWrapper.getKey().equals("-1"))) {
//
//            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//            if (cacheResWrapper == null) {
//                
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request is not completed. Researchers not found in cache.");
//                return Response.ok(response).build();
//            } else { // if researcher are in the cahce
//                String queryCacheKey = queryWrapper.getKey() + StaticVars.CACHE_MasPub_QUERY_KEY;
//                QueryMapperWrapper cachedQuery = CacheMemoryManager.getInstance().getQueryWrapper(queryCacheKey);
//                if (cachedQuery == null) { //if there is no publication query in the cache
//                    ResearchersWrapper resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has completed successfully."
//                            + "Researchers are set in cache."
//                            + "No pub query found in cahce."
//                            + "so researcher are fetched from database.");
//                    if (queryWrapper.getDataRequest().equals("yes")) {
//                        response.setDataObject(resWrapper);
//                    }
//                    return Response.ok(response).build();
//                    // if there is a publication query in cache then
//                } else if (cachedQuery.getMappersList().equals(queryWrapperObj.getMappersList())) {
//
//                    System.out.println("Cache Query HIT!!!!!!!!!!!!!!!!!!!!!!!");
//                    System.out.println("Cache Query :"  + cachedQuery.toString());
//                    System.out.println("new Query :"  + queryWrapperObj.toString());
//                    ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//                    if (cacheResWrapper.getResearchers().get(0).getPublications() == null) {
//                        resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has completed successfully."
//                                + "A pub cache key found in cache. and matched with new key"
//                                + "Reseasrchers publications are found null."
//                                + "So publication fetched from database.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    }
//                    else{
//                         System.out.println("Same Researchers HIT!!!!!!!!!!!!!!!!!!!!!!!");
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has completed successfully."
//                                + "A pub cache key found in cache. and matched with new key."
//                                + "And researchers publications are not null."
//                                + "So researcher are fetche from cache.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    }
//
//
//                } else { // if there is a key publication query in the cache but is not matched with new query then
//                    ResearchersWrapper resWrapper = _masEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has completed successfully."
//                            + "A different pub key found in cache."
//                            + "So researchers are fetched from database."
//                            + "");
//                    if (queryWrapper.getDataRequest().equals("yes")) {
//                        response.setDataObject(resWrapper);
//                    }
//                    return Response.ok(response).build();
//                }
//            }
//        } else {
//
//            response.setMessage("Key is not provided.");
//            response.setRequestStatus(Boolean.FALSE);
//            return Response.ok(response).build();
//        }
//        //return Response.ok(response).build();
//    }
//    
}

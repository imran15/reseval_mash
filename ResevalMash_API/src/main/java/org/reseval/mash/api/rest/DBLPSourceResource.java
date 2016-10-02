/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.rest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DBLPSourceLogicLocal;
import org.reseval.mash.api.facades.ItalianSourceLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("dblpSource")
@Stateless
public class DBLPSourceResource {

    @Context
    private UriInfo context;
    @EJB
    private DBLPSourceLogicLocal _dblpEJBLocal;
    private ResearchersWrapper _resWrapper = null;
    @EJB
    private ItalianSourceLogicLocal _italianSourceLocal;

    /** Creates a new instance of ItalianSourceResource */
    public DBLPSourceResource() {
    }

//    @GET
//    @Produces("application/json")
//    @Path("researcher/autocomplete")
//    public Response autoCompleteName(@QueryParam("input") String input) {
//        List<Researcher> names = _dblpEJBLocal.autoCompleteResearcherName(input);
//        List<ResearcherResponseWrapper> dblpWrapperList = new ArrayList<ResearcherResponseWrapper>();
//        ResearcherResponseWrapper aWrapper;
//        for (int i = 0; i < names.size(); i++) {
//            String uniName = _italianSourceLocal.getUniNamebyID(names.get(i).getUniversityID());
//            aWrapper = new ResearcherResponseWrapper();
//            aWrapper.setId(names.get(i).getId());
//            aWrapper.setName(names.get(i).getName());
//            aWrapper.setUniversityId(names.get(i).getUniversityID());
//            aWrapper.setUniversityName(uniName);
//            aWrapper.setDblpID(names.get(i).getDblpID());
//            dblpWrapperList.add(aWrapper);
//
//        }
//
//        return Response.ok(dblpWrapperList).build();
//    }
    @GET
    @Produces("application/json")
    @Path("researcher/autocomplete")
    public Response autoCompleteName(@QueryParam("input") String input) {
        List<Researcher> researchers = _dblpEJBLocal.autoCompleteResearcherName(input);
//        List<ResearcherResponseWrapper> dblpWrapperList = new ArrayList<ResearcherResponseWrapper>();
//        ResearcherResponseWrapper aWrapper;
//        for (int i = 0; i < researchers.size(); i++) {
//            aWrapper = new ResearcherResponseWrapper();
//            aWrapper.setId(researchers.get(i).getId());
//            aWrapper.setName(researchers.get(i).getName());
//            aWrapper.setDblpID(researchers.get(i).getDblpID());
//            aWrapper.setNumofPubs(researchers.get(i).getPubsNumber());
//
//            dblpWrapperList.add(aWrapper);
//
//        }
        return Response.ok(researchers).build();
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
                    _resWrapper = researcherWrapper;
                    putDatainCache(researcherWrapper, queryWrapperObj.getKey());
                    response.setCacheKey(queryWrapperObj.getKey());
                    response.setRequestStatus(Boolean.TRUE);
                    response.setMessage("Request successfully completed. Data is cached.");
                    if (queryWrapperObj.getDataRequest().equals("yes")) {
                        response.setDataObject(_resWrapper);
                    }
                    return Response.ok(response).build();
                } else {
                    response.setRequestStatus(Boolean.FALSE);
                    response.setMessage("Request Failed. No, data and key found.");

                    return Response.ok(response).build();
                }
            } else {
                _resWrapper = cacheResWrapper;
                response.setCacheKey(queryWrapperObj.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Request successfully completed.");
                if (queryWrapperObj.getDataRequest().equals("yes")) {
                    response.setDataObject(cacheResWrapper);
                }
                return Response.ok(response).build();
            }
        }// if key is not provided then check researchers in the request
        else {
            response.setCacheKey("");
            response.setRequestStatus(Boolean.FALSE);
            response.setMessage("Request not completed. Key not found in the call.");
            return Response.ok(response).build();
        }

    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/getPublications2")
    public Response getPublications(QueryMapperWrapper queryWrapperObj) {
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

            if (isDataAvailbelinCache(queryWrapper)) { // if data is available in cache

                ResearchersWrapper resWrapper = getDatafromCache(queryWrapper);
                queryWrapper.setResearcherList(resWrapper.getResearchers());
                resWrapper = getDBPublications(queryWrapper);
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
                    ResearchersWrapper resWrapper = getDBPublications(queryWrapper);
                    putDatainCache(resWrapper, queryWrapper.getKey());
                    response.setRequestStatus(Boolean.TRUE);
                    response.setCacheKey(queryWrapper.getKey());
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
        resWrapper = _dblpEJBLocal.getPublications(resWrapper, queryWrapperObj.getMappersList());
        return resWrapper;
    }

    private List<Researcher> getDataFromLinkedHashMap(List<LinkedHashMap> hashMapList) {

        LinkedHashMap hashMap = new LinkedHashMap();
        List<Researcher> researcherList = new ArrayList<Researcher>();
        for (int i = 0; i < hashMapList.size(); i++) {
            hashMap = hashMapList.get(i);
            Researcher researcher = new Researcher();
            researcher.setId(((Integer) hashMap.get("researcherId")).longValue());
            researcher.setFullName((String) hashMap.get("researcherName"));
            researcher.setUniversityID(((Integer) hashMap.get("universityId")).longValue());
            researcher.setDblpID(((Integer) hashMap.get("DBLPId")).longValue());

            researcherList.add(researcher);
        }

        return researcherList;
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
    /*
    ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
    if (cacheResWrapper == null) {
    
    response.setRequestStatus(Boolean.FALSE);
    response.setMessage("Request is not completed. Researchers not found in cache.");
    return Response.ok(response).build();
    } else { // if researchers are in the cahce
    String queryCacheKey = queryWrapper.getKey() + StaticVars.CACHE_MasPub_QUERY_KEY;
    QueryMapperWrapper cachedQuery = CacheMemoryManager.getInstance().getQueryWrapper(queryCacheKey);
    if (cachedQuery == null) { //if there is no publication query in the cache
    ResearchersWrapper resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
    response.setCacheKey(queryWrapper.getKey());
    response.setRequestStatus(Boolean.TRUE);
    response.setMessage("Request has been completed successfully.");
    if (queryWrapper.getDataRequest().equals("yes")) {
    response.setDataObject(resWrapper);
    }
    return Response.ok(response).build();
    // if there is a publication query in cache then
    } else if (cachedQuery.getMappersList().equals(queryWrapperObj.getMappersList())) {
    ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
    if (cacheResWrapper.getResearchers().get(0).getPublications() == null) {
    resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
    response.setCacheKey(queryWrapper.getKey());
    response.setRequestStatus(Boolean.TRUE);
    response.setMessage("Request has been completed successfully.");
    if (queryWrapper.getDataRequest().equals("yes")) {
    response.setDataObject(resWrapper);
    }
    return Response.ok(response).build();
    } else {
    response.setCacheKey(queryWrapper.getKey());
    response.setRequestStatus(Boolean.TRUE);
    response.setMessage("Request has been completed successfully.");
    if (queryWrapper.getDataRequest().equals("yes")) {
    response.setDataObject(resWrapper);
    }
    return Response.ok(response).build();
    }
    
    
    } else { // if there is a key publication query in the cache but is not matched with new query then
    ResearchersWrapper resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
    response.setCacheKey(queryWrapper.getKey());
    response.setRequestStatus(Boolean.TRUE);
    response.setMessage("Request has been completed successfully.");
    if (queryWrapper.getDataRequest().equals("yes")) {
    response.setDataObject(resWrapper);
    }
    return Response.ok(response).build();
    }
    }
    } else { // if key is not provided and researchers are provided.
    
    if (!(queryWrapperObj.getMappersList().isEmpty())) {
    System.out.println("Inside if#########");
    if (queryWrapperObj.getMappersList().get(0).getParamName().equals("ResearchersList")) {
    List<Researcher> researchersList = (List<Researcher>) queryWrapperObj.getMappersList().get(0).getParamValue();
    ResearchersWrapper researcherWrapper = new ResearchersWrapper();
    researcherWrapper.setResearchers(researchersList);
    researcherWrapper = _dblpEJBLocal.getPublications(researcherWrapper, queryWrapper.getMappersList());
    response.setCacheKey(researcherWrapper.getKey());
    response.setRequestStatus(Boolean.TRUE);
    response.setMessage("Request has been completed successfully.");
    if (queryWrapper.getDataRequest().equals("yes")) {
    response.setDataObject(researcherWrapper);
    }
    return Response.ok(response).build();
    }
    }
    
    }
    return Response.ok(response).build();
     * 
     */
    //}
    //################## Backup of getPublication method
//     @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    @Path("/getPublications")
//    public Response getPublications(QueryMapperWrapper queryWrapperObj) {
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
//            } else { // if researchers are in the cahce
//                String queryCacheKey = queryWrapper.getKey() + StaticVars.CACHE_MasPub_QUERY_KEY;
//                QueryMapperWrapper cachedQuery = CacheMemoryManager.getInstance().getQueryWrapper(queryCacheKey);
//                if (cachedQuery == null) { //if there is no publication query in the cache
//                    ResearchersWrapper resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has been completed successfully.");
//                    if (queryWrapper.getDataRequest().equals("yes")) {
//                        response.setDataObject(resWrapper);
//                    }
//                    return Response.ok(response).build();
//                    // if there is a publication query in cache then
//                } else if (cachedQuery.getMappersList().equals(queryWrapperObj.getMappersList())) {
//                    ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//                    if (cacheResWrapper.getResearchers().get(0).getPublications() == null) {
//                        resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has been completed successfully.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    } else {
//                        response.setCacheKey(queryWrapper.getKey());
//                        response.setRequestStatus(Boolean.TRUE);
//                        response.setMessage("Request has been completed successfully.");
//                        if (queryWrapper.getDataRequest().equals("yes")) {
//                            response.setDataObject(resWrapper);
//                        }
//                        return Response.ok(response).build();
//                    }
//
//
//                } else { // if there is a key publication query in the cache but is not matched with new query then
//                    ResearchersWrapper resWrapper = _dblpEJBLocal.getPublications(cacheResWrapper, queryWrapper.getMappersList());
//                    CacheMemoryManager.getInstance().putQuery(queryCacheKey, queryWrapper);
//                    response.setCacheKey(queryWrapper.getKey());
//                    response.setRequestStatus(Boolean.TRUE);
//                    response.setMessage("Request has been completed successfully.");
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
    //#####################
}

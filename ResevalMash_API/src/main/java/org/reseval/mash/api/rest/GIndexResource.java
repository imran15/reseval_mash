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
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.GIndexLogicLocal;
import org.reseval.mash.api.facades.HIndexLogicLocal;
import org.reseval.mash.api.wrappers.QueryMapperWrapper;
import org.reseval.mash.api.wrappers.ResponseWrapper;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("GIndexSource")
@Stateless
public class GIndexResource {

    @Context
    private UriInfo context;
    @EJB
    private GIndexLogicLocal _gIndexLocal;
    private List<Researcher> _resList = null;
    private QueryMapperWrapper _queryWrapper=null;

    /** Creates a new instance of ItalianSourceResource */
    public GIndexResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/setPublications")
    public Response setPublications(QueryMapperWrapper queryWrapper) {
        _queryWrapper =  queryWrapper;
        ResponseWrapper response = new ResponseWrapper();
        if ((queryWrapper.getKey().equals(""))) { // if key is not provided
            if (isDataAvailableinRequest(queryWrapper)) { // if data is in the request
                _resList = queryWrapper.getResearcherList();
                response.setRequestStatus(Boolean.TRUE);
                response.setMessage("Key is not provided. Data is kept on server temporarily, without caching.");
                return Response.ok(response).build();
            } else {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("No data and key found in request.");
                return Response.ok(response).build();
            }

        } else {// key is provided in the request

            
            if (isDataAvailableinRequest(queryWrapper)) { // if data is available in request

                ResearchersWrapper resWrapper = getDataFromRequest(queryWrapper);
                putDatainCache(resWrapper, queryWrapper.getKey());
                response.setRequestStatus(Boolean.TRUE);
                response.setCacheKey(queryWrapper.getKey());
                response.setMessage("Key and data found in the request. Request has been completed successfully with caching.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(resWrapper);
                }
                return Response.ok(response).build();
            } else { // if data is not in cache, then check if data is provided in request
                ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
                if (cacheResWrapper == null)
                {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Key Found. Data does not exist. Provide data and key for caching.");
                return Response.ok(response).build();
                }else{
                response.setRequestStatus(Boolean.TRUE);
                response.setCacheKey(queryWrapper.getKey());
                response.setMessage("Key and Data found in the cache.");
                if (queryWrapper.getDataRequest().equals("yes")) {
                    response.setDataObject(cacheResWrapper);
                }
                return Response.ok(response).build();
                }
            }

        }

       
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/getGIndex")
    public Response getGIndex(QueryMapperWrapper queryWrapperObj) {

        ResponseWrapper response = new ResponseWrapper();
        QueryMapperWrapper queryWrapper = queryWrapperObj;

        if (!(queryWrapper.getKey().equals(""))) {

            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
            if (cacheResWrapper == null) {
                response.setRequestStatus(Boolean.FALSE);
                response.setMessage("Request is not completed. Researchers/Publications not found in the cache.");
                return Response.ok(response).build();
            } else { // if researcher are in the cahce
                ResearchersWrapper resWrapper = _gIndexLocal.getGIndex(cacheResWrapper);
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
        
        if (queryWrapperObj.getResearcherList() == null)
            return false;
        else
            return true;
    }

    private List<Researcher> getDataFromLinkedHashMap(List<LinkedHashMap> hashMapList) {

        LinkedHashMap hashMap = new LinkedHashMap();
        List<Researcher> researcherList = new ArrayList<Researcher>();
        for (int i = 0; i < hashMapList.size(); i++) {
            hashMap = hashMapList.get(i);
            Researcher researcher = new Researcher();
            researcher.setId(((Integer) hashMap.get("Id")).longValue());
            researcher.setFullName((String) hashMap.get("fullName"));
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

    private ResearchersWrapper getDataFromRequest(QueryMapperWrapper queryWrapperObj) {
        ResearchersWrapper resWrapper = new ResearchersWrapper();
        resWrapper.setResearchers(queryWrapperObj.getResearcherList());
        resWrapper.setKey(queryWrapperObj.getKey());
//        for (int i = 0; i < queryWrapperObj.getMappersList().size(); i++) {
//            if (queryWrapperObj.getMappersList().get(i).getParamName().equals("ResearchersList")) {
//                List<LinkedHashMap> hashMap = (List<LinkedHashMap>) queryWrapperObj.getMappersList().get(i).getParamValue();
//                resWrapper.setResearchers(getDataFromLinkedHashMap(hashMap));
//                break;
//            }
//        }
        return resWrapper;
    }

    private ResearchersWrapper getDatafromCache(QueryMapperWrapper queryWrapper) {
        return CacheMemoryManager.getInstance().getResearchers(queryWrapper.getKey());
    }

    private void putDatainCache(ResearchersWrapper resWrapper, String key) {
        CacheMemoryManager.getInstance().putItalianDSResearchers(key, resWrapper);
    }
//    @POST
//    @Produces("application/json")
//    @Consumes("application/json")
//    @Path("/setPublications")
//    public Response setPublications(QueryMapperWrapper queryWrapperObj) {
//
//        ResponseWrapper response = new ResponseWrapper();
//        //if key is provided in request call, then retrieve researchers from cache and proceed.
//        if (!(queryWrapperObj.getKey().isEmpty())) {
//            ResearchersWrapper cacheResWrapper = CacheMemoryManager.getInstance().getResearchers(queryWrapperObj.getKey());
//            if (cacheResWrapper == null) {
//                response.setRequestStatus(Boolean.FALSE);
//                response.setMessage("Request is not completed. Publications not found in the cache.");
//                return Response.ok(response).build();
//            } else {
//                _resWrapper = cacheResWrapper;
//                System.out.println("#####################"+_resWrapper.getResearchers().get(0).getFullName());
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
//        //if key is not provided in request call then check publications in mappersList
//        //if (!(queryWrapperObj.getMappersList().isEmpty())) {
//        if (!(queryWrapperObj.getResearcherList().isEmpty())) {
//
//            //if (queryWrapperObj.getMappersList().get(0).getParamName().equals("ResearchersList")) {
//          //  if (queryWrapperObj.getResearcherList().get(0).getParamName().equals("ResearchersList")) {
//                List<Researcher> researchersList = (List<Researcher>) queryWrapperObj.getResearcherList();//.get(0).getParamValue();
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
////            } else {
////
////                response.setCacheKey("");
////                response.setRequestStatus(Boolean.FALSE);
////                response.setMessage("Request not completed. Researchers/Publications are not found in POST call.");
////                return Response.ok(response).build();
////            }
//        } else {
//
//            response.setCacheKey("");
//            response.setRequestStatus(Boolean.FALSE);
//            response.setMessage("Request not completed. Researchers/Publications are not found in POST call.");
//            return Response.ok(response).build();
//        }
//
//    }
}
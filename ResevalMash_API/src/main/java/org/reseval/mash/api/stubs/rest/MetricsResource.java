/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.stubs.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.reseval.mash.api.stubs.beans.ImpactCompotations;
import org.reseval.mash.api.stubs.beans.Percentile;
import org.reseval.mash.api.stubs.beans.ResearcherLong;
import org.reseval.mash.facades.remote.ResearchersFacadeRemote;

/**
 * REST Web Service
 *
 * @author Muhammad Imran
 */
@Path("STUBS/metrics")
public class MetricsResource {

    @Context
    private UriInfo context;
    

    /** Creates a new instance of MetricsResource */
    public MetricsResource() {
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    @Path("veneuimpact")
    public Response getVeneusImpact(List<ResearcherLong> res) {
        ImpactCompotations impact1 = new ImpactCompotations("Veneu Impact", 4.99, "VLDB JOURNAL",0.0);
        ImpactCompotations impact2 = new ImpactCompotations("Veneu Impact", 5.9, "CELL STEM CELL",0.0);
        ImpactCompotations impact3 = new ImpactCompotations("Veneu Impact", 2.4, "BPM",0.0);
        ImpactCompotations impact4 = new ImpactCompotations("Veneu Impact", 7.66, "ACM COMPUTING SURVEYS",0.0);
        ImpactCompotations impact5 = new ImpactCompotations("Veneu Impact", 2.1, "ICSOC",0.0);
        ImpactCompotations impact6 = new ImpactCompotations("Veneu Impact", 3.0, "CIKM",0.0);
        ImpactCompotations impact7 = new ImpactCompotations("Veneu Impact", 1.9, "BDP",0.0);

        List<ImpactCompotations> impactList = new ArrayList<ImpactCompotations>();
        impactList.add(impact1);
        impactList.add(impact2);
        impactList.add(impact3);
        impactList.add(impact4);
        impactList.add(impact5);
        impactList.add(impact6);
        impactList.add(impact7);

        return Response.ok(impactList).build();
    }

//    @POST
//    @Consumes("application/json")
//    @Path("impactPercentile/setDistribution")
//    public void setDistribution(List<ImpactCompotations> impComp) {
//        this.impCom = impComp;
//    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("impactPercentile/getPercentiles")
    public Response getPercentiles(List<ImpactCompotations> impComp) {

        return Response.ok(getPercentile(impComp)).build();
    }

    public List<ImpactCompotations> getPercentile(List<ImpactCompotations> values) {

        int count = 0;
        int n = values.size();
        //Double[] a = values;
        //List<Percentile> percentiles = new ArrayList<Percentile>();
        for (int i = 0; i <= n - 1; i++) {
            count = 0;
            for (int j = 0; j <= n - 1; j++) {
                if (values.get(i).getValue() > values.get(j).getValue()) {

                    count = count + 1;
                }
            }
            values.get(i).setPercentile(new Double((count * 100) / (n - 1)));
        }
        return values;
    }
}

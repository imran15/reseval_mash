/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.test;

import java.util.ArrayList;
import java.util.List;
import org.reseval.mash.api.stubs.beans.DISIDistribution;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.beans.Venue;

/**
 *
 * @author admin
 */
public class Test {
    
    
    
    public static void main(String argus[])
    {
        Test test = new Test();
        DISIDistribution distribution = null;
         List<Researcher> resList = test.feedFakeResearchers();
          int researcherSize = resList.size();
        Integer[] catA = new Integer[researcherSize];
            Integer[] catB = new Integer[researcherSize];
            Integer[] catC = new Integer[researcherSize];
            Integer[] catD = new Integer[researcherSize];

            Integer[] AList = new Integer[researcherSize];
            Integer[] BList = new Integer[researcherSize];
            Integer[] CList = new Integer[researcherSize];
            Integer[] DList = new Integer[researcherSize];

        
        for (int i = 0; i < resList.size(); i++) {
                List<Publication> pubList = resList.get(i).getPublications();
                int publicationsSize = pubList.size();
                Integer A = new Integer(0);
                Integer B = new Integer(0);
                Integer C = new Integer(0);
                Integer D = new Integer(0);
                for (int j = 0; j < publicationsSize; j++) {
                    Venue venue = pubList.get(j).getVenue();
                    if ((venue.getvClass() != null)) {
                        if (venue.getvClass().equals("A")) {
                            A += 1;  // It increments A if a publication's venue is in category A
                        } else if (venue.getvClass().equals("B")) {
                            B += 1;
                        } else if (venue.getvClass().equals("C")) {
                            C += 1;
                        } else {
                            D += 1;
                        }
                    }else{
                        D += 1;  // if a publication does not have any class associated then it should be in cat D.
                    }
                }

                AList[i] = A;  // Storing # of A's publications for ith researcher
                BList[i] = B;  // Storing # of B's publications for ith researcher
                CList[i] = C;
                DList[i] = D;

            }
            for (int i = 0; i < catA.length; i++) {
                catA[i] = test.pubScoreSearch(AList, i);  // Checking how many researchers have i number of publications in category A.
                catB[i] = test.pubScoreSearch(BList, i);
                catC[i] = test.pubScoreSearch(CList, i);
                catD[i] = test.pubScoreSearch(DList, i);
            }

            distribution.setCatA(catA);
            distribution.setCatB(catB);
            distribution.setCatC(catC);
            distribution.setCatD(catD);
        
        
            for (int k=0;k<distribution.getCatA().length;k++){
                Integer [] A = distribution.getCatA();
                Integer [] B = distribution.getCatA();
                Integer [] C = distribution.getCatA();
                Integer [] D = distribution.getCatA();
                System.out.print(" , " + A[k]);
                
            }
            
            
    }
     private Integer pubScoreSearch(Integer[] list, int no) {
        Integer result = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == no) {
                result++;
            }
        }
        return result;
    }
   
        
        private List<Researcher> feedFakeResearchers() {

        List<Researcher> resList = new ArrayList<Researcher> ();
        Researcher r = new Researcher();

        Publication p1 = new Publication();
        Publication p2 = new Publication();
        Publication p3 = new Publication();
        Publication p4 = new Publication();
        Publication p5 = new Publication();
        Publication p6 = new Publication();

        Venue v1 = new Venue();
        Venue v2 = new Venue();
        Venue v3 = new Venue();
        Venue v4 = new Venue();
        Venue v5 = new Venue();
        Venue v6 = new Venue();

        v1.setName("VLDB");
        v1.setImpactFactor(1.5);
        v1.setvClass("A");
        v1.setId(100l);

        v2.setName("CIKM");
        v2.setImpactFactor(0.0);
        v1.setvClass("B");
        v2.setId(101l);

        v3.setName("ICSOC");
        v3.setImpactFactor(0.0);
        v1.setvClass("C");
        v3.setId(102l);

        v4.setName("BPM");
        v4.setImpactFactor(0.0);
          v1.setvClass("B");
        v4.setId(103l);

        v5.setName("WWW");
        v5.setImpactFactor(0.0);
          v1.setvClass("A");
        v5.setId(104l);

        v6.setName("IEEE INTERNET COMPUTING");
        v6.setImpactFactor(0.0);
          v1.setvClass("A");
        v6.setId(105l);

        p1.setId(2798822l);
        p1.setTitle("Privacy Preserving Event Driven Integration for Interoperating Social and Health Systems");
        p1.getCoAuthors().add(new Researcher(12l, "Giampaolo Armellin"));
        p1.getCoAuthors().add(new Researcher(13l, "Dario Betti"));
        p1.getCoAuthors().add(new Researcher(14l, "Annamaria Chiasera"));
        p1.getCoAuthors().add(new Researcher(15l, "Gloria MartAnez"));
        p1.getCoAuthors().add(new Researcher(16l, "Jovan Stevovic"));
        p1.setCitations(15);
        //p1.setDate(datatypeFactory.newXMLGregorianCalendar(calendar));
        p1.setYear(1957l);
        p1.setVenue(v1);
        p1.setUri("");

        p2.setId(2798823l);
        p2.setTitle("From People to Services to UI: Distributed Orchestration of User Interfaces");
        p2.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p2.getCoAuthors().add(new Researcher(18l, "Stefano Soi"));
        p2.getCoAuthors().add(new Researcher(19l, "Stefano Tranquillini"));
        p2.getCoAuthors().add(new Researcher(20l, "Chang Heng"));
        p2.getCoAuthors().add(new Researcher(21l, "Li Yan"));
        p2.setCitations(15);
        p2.setYear(1987l);
        p2.setVenue(v2);
        p2.setUri("");

        p3.setId(2798824l);
        p3.setTitle("Liquid journals: scientific journals in the Web 2.0 era");
        p3.getCoAuthors().add(new Researcher(22l, "Marcos Baez"));
        p3.getCoAuthors().add(new Researcher(23l, "Alejandro Mussi"));
        p3.getCoAuthors().add(new Researcher(24l, "Aliaksandr Birukou"));
        p3.getCoAuthors().add(new Researcher(25l, "Maurizio Marchese"));
        p3.setCitations(30);
        p3.setYear(1990l);
        p3.setVenue(v3);
        p3.setUri("");

        p4.setId(2798825l);
        p4.setTitle("Analysis and applications of timed service protocols");
        p4.getCoAuthors().add(new Researcher(26l, "Julien Ponge"));
        p4.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p4.getCoAuthors().add(new Researcher(28l, "Farouk Toumani"));
        p4.setCitations(30);
        p4.setYear(1999l);
        p4.setVenue(v4);
        p4.setUri("");

        p5.setId(2798826l);
        p5.setTitle("Toward Uncertain Business Intelligence: The Case of Key Indicators");
        p5.getCoAuthors().add(new Researcher(29l, "Carlos Rodrguez"));
        p5.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p5.getCoAuthors().add(new Researcher(30l, "Cinzia Cappiello"));
        p5.setCitations(15);
        p5.setYear(2001l);
        p5.setVenue(v5);
        p5.setUri("");

        p6.setId(2798827l);
        p6.setTitle("Message Correlation and Web Service Protocol Mining from Inaccurate Logs");
        p6.getCoAuthors().add(new Researcher(31l, "Kreshnik Musaraj"));
        p6.getCoAuthors().add(new Researcher(32l, "Tetsuya Yoshida"));
        p6.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p6.getCoAuthors().add(new Researcher(33l, "Mohand-Said Hacid"));
        p6.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p6.setCitations(15);
        p6.setYear(2010l);
        p6.setVenue(v6);
        p6.setUri("");

        r.setId(619l);
        r.setName("Fabio");
        r.setSurname("Casati");
        // r.getMetrics().getMetricsStandard().add(new Metric("H-index", 23));
        //r.getMetrics().getMetricsStandard().add(new Metric("G-index", 60));
        //r.getMetrics().getMetricsStandard().add(new Metric("C-index", 250));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("H-index", 18));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("G-index", 50));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("C-index", 200));
        r.setPubsNumber(120);
        r.getPublications().add(p1);
        r.getPublications().add(p2);
        r.getPublications().add(p3);
        r.getPublications().add(p4);
        r.getPublications().add(p5);
        r.getPublications().add(p6);


        resList.add(r);

        //########################## id":36756,"name":"John","surname":"MYLOPOULOS"  ###################

        Researcher r2 = new Researcher();

        p1.setId(3798822l);
        p1.setTitle("Privacy Preserving Event Driven Integration for Interoperating Social and Health Systems");
        p1.getCoAuthors().add(new Researcher(12l, "Giampaolo Armellin"));
        p1.getCoAuthors().add(new Researcher(13l, "Dario Betti"));
        p1.getCoAuthors().add(new Researcher(14l, "Annamaria Chiasera"));
        p1.getCoAuthors().add(new Researcher(15l, "Gloria MartAnez"));
        p1.getCoAuthors().add(new Researcher(16l, "Jovan Stevovic"));
        p1.setCitations(15);
        //p1.setDate(datatypeFactory.newXMLGregorianCalendar(calendar));
        p1.setYear(1957l);
        p1.setVenue(v1);
        p1.setUri("");

        p2.setId(3798823l);
        p2.setTitle("From People to Services to UI: Distributed Orchestration of User Interfaces");
        p2.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p2.getCoAuthors().add(new Researcher(18l, "Stefano Soi"));
        p2.getCoAuthors().add(new Researcher(19l, "Stefano Tranquillini"));
        p2.getCoAuthors().add(new Researcher(20l, "Chang Heng"));
        p2.getCoAuthors().add(new Researcher(21l, "Li Yan"));
        p2.setCitations(15);
        p2.setYear(1987l);
        p2.setVenue(v2);
        p2.setUri("");

        p3.setId(3798824l);
        p3.setTitle("Liquid journals: scientific journals in the Web 2.0 era");
        p3.getCoAuthors().add(new Researcher(22l, "Marcos Baez"));
        p3.getCoAuthors().add(new Researcher(23l, "Alejandro Mussi"));
        p3.getCoAuthors().add(new Researcher(24l, "Aliaksandr Birukou"));
        p3.getCoAuthors().add(new Researcher(25l, "Maurizio Marchese"));
        p3.setCitations(30);
        p3.setYear(1990l);
        p3.setVenue(v3);
        p3.setUri("");

        p4.setId(3798825l);
        p4.setTitle("Analysis and applications of timed service protocols");
        p4.getCoAuthors().add(new Researcher(26l, "Julien Ponge"));
        p4.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p4.getCoAuthors().add(new Researcher(28l, "Farouk Toumani"));
        p4.setCitations(30);
        p4.setYear(1999l);
        p4.setVenue(v4);
        p4.setUri("");

        p5.setId(3798826l);
        p5.setTitle("Toward Uncertain Business Intelligence: The Case of Key Indicators");
        p5.getCoAuthors().add(new Researcher(29l, "Carlos Rodrguez"));
        p5.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p5.getCoAuthors().add(new Researcher(30l, "Cinzia Cappiello"));
        p5.setCitations(15);
        p5.setYear(2001l);
        p5.setVenue(v5);
        p5.setUri("");

        p6.setId(3798827l);
        p6.setTitle("Message Correlation and Web Service Protocol Mining from Inaccurate Logs");
        p6.getCoAuthors().add(new Researcher(31l, "Kreshnik Musaraj"));
        p6.getCoAuthors().add(new Researcher(32l, "Tetsuya Yoshida"));
        p6.getCoAuthors().add(new Researcher(17l, "Florian Daniel"));
        p6.getCoAuthors().add(new Researcher(33l, "Mohand-Said Hacid"));
        p6.getCoAuthors().add(new Researcher(27l, "Boualem Benatallah"));
        p6.setCitations(15);
        p6.setYear(2010l);
        p6.setVenue(v6);
        p6.setUri("");

        r2.setId(36756l);
        r2.setName("John");
        r2.setSurname("MYLOPOULOS");
        // r.getMetrics().getMetricsStandard().add(new Metric("H-index", 23));
        //r.getMetrics().getMetricsStandard().add(new Metric("G-index", 60));
        //r.getMetrics().getMetricsStandard().add(new Metric("C-index", 250));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("H-index", 18));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("G-index", 50));
        //r.getMetrics().getMetricsWithoutSelfCitations().add(new Metric("C-index", 200));
        r2.setPubsNumber(100);
        r2.getPublications().add(p1);
        r2.getPublications().add(p2);
        r2.getPublications().add(p3);
        r2.getPublications().add(p4);
        r2.getPublications().add(p5);
        r2.getPublications().add(p6);



        resList.add(r2);

        return resList;
    }
    
}

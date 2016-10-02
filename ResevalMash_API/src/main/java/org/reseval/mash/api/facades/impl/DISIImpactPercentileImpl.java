/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.facades.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.reseval.mash.api.cache.CacheMemoryManager;
import org.reseval.mash.api.facades.DISIImpactPercentileLocal;
import org.reseval.mash.api.stubs.beans.DISIDistribution;
import org.reseval.mash.beans.Metric;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class DISIImpactPercentileImpl implements DISIImpactPercentileLocal {

    private DISIDistribution _distribution = null;
    private Integer _totalResearchers = 0;

    @Override
    public DISIDistribution setDistribution(String cacheKey) {
        // FileWriter writer = new FileWriter();
        String textCatA = "";
        String textCatB = "";
        String textCatC = "";
        String textCatD = "";
        ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);
        DISIDistribution distribution = null;
        if (resWrapper != null) {
            distribution = new DISIDistribution();
            int researcherSize = resWrapper.getResearchers().size();
            _totalResearchers = researcherSize;
            //  writer.writeToFile("Total Researcher Size (BIGDs): " + _totalResearchers+"\n");
            Integer[] AList = new Integer[researcherSize];
            Integer[] BList = new Integer[researcherSize];
            Integer[] CList = new Integer[researcherSize];
            Integer[] DList = new Integer[researcherSize];
            int maxPubsA = 0;
            int maxPubsB = 0;
            int maxPubsC = 0;
            int maxPubsD = 0;
            for (int i = 0; i < researcherSize; i++) {
                List<Publication> pubList = resWrapper.getResearchers().get(i).getPublications();
                int publicationsSize = 0;
                if (pubList == null) {
                    publicationsSize = 0;
                } else {
                    publicationsSize = pubList.size();
                }
                Integer A = new Integer(0);
                Integer B = new Integer(0);
                Integer C = new Integer(0);
                Integer D = new Integer(0);
                for (int j = 0; j < publicationsSize; j++) {
                    Venue venue = pubList.get(j).getVenue();
                   // if (venue != null)
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
                    } else {
                        D += 1;  // if a publication does not have any class associated then it should be in cat D.
                        //maxPubsD++;
                    }
                }

                if (A > maxPubsA) {
                    maxPubsA = A;
                }
                if (B > maxPubsB) {
                    maxPubsB = B;
                }
                if (C > maxPubsC) {
                    maxPubsC = C;
                }
                if (D > maxPubsD) {
                    maxPubsD = D;
                }

                AList[i] = A;  // Storing # of A's publications for ith researcher
                BList[i] = B;  // Storing # of B's publications for ith researcher
                CList[i] = C;
                DList[i] = D;

                textCatA += A + ",";
                textCatB += B + ",";
                textCatC += C + ",";
                textCatD += D + ",";
            }
            //  writer.writeToFile("Max of catA: " + maxPubsA + "\n Max catB: " + maxPubsB +"\n Max catC: " + maxPubsC + "\n Max catD: " +maxPubsD +"\n");
            //  writer.writeToFile("Big Dataset Distribution:\nCategory A: \n" + textCatA+"\nCategory B: \n"+textCatB+"\nCategory C: \n"+textCatC+"\nCategory D: \n"+textCatD+"\n\n");
            textCatA = "";
            textCatB = "";
            textCatC = "";
            textCatD = "";
            Integer[] catA = new Integer[maxPubsA + 1];
            Integer[] catB = new Integer[maxPubsB + 1];
            Integer[] catC = new Integer[maxPubsC + 1];
            Integer[] catD = new Integer[maxPubsD + 1];

            for (int i = 0; i < maxPubsA + 1; i++) {
                catA[i] = pubScoreSearch(AList, i);  // Checking how many researchers have i number of publications in category A.
                textCatA += catA[i] + ",";
            }

            for (int i = 0; i < maxPubsB + 1; i++) {
                catB[i] = pubScoreSearch(BList, i);  // Checking how many researchers have i number of publications in category B.
                textCatB += catB[i] + ",";
            }
            for (int i = 0; i < maxPubsC + 1; i++) {
                catC[i] = pubScoreSearch(CList, i);  // Checking how many researchers have i number of publications in category C.
                textCatC += catC[i] + ",";
            }
            for (int i = 0; i < maxPubsD + 1; i++) {
                catD[i] = pubScoreSearch(DList, i);  // Checking how many researchers have i number of publications in category D.
                textCatD += catD[i] + ",";
            }

            // writer.writeToFile("Distribution:\n\n Category A:\n"+ textCatA+
            //         "\nCategory B:\n" + textCatB+"\nCategory C:\n"+textCatC+"\n Category D:\n" + textCatD);
            distribution.setCatA(catA);
            distribution.setCatB(catB);
            distribution.setCatC(catC);
            distribution.setCatD(catD);

        }
        _distribution = distribution;
        return distribution;
    }

    @Override
    public ResearchersWrapper getPercentile(String cacheKey) {
        //   FileWriter writer= new FileWriter();
        String textCatA = "";
        String textCatB = "";
        String textCatC = "";
        String textCatD = "";
        Metric m;

        ResearchersWrapper resWrapper = CacheMemoryManager.getInstance().getResearchers(cacheKey);

        if (resWrapper != null) {
            int researcherSize = resWrapper.getResearchers().size();
            //distribution = new DISIDistribution();

            Integer[] AList = new Integer[researcherSize];
            Integer[] BList = new Integer[researcherSize];
            Integer[] CList = new Integer[researcherSize];
            Integer[] DList = new Integer[researcherSize];
            int maxPubsA = 0;
            int maxPubsB = 0;
            int maxPubsC = 0;
            int maxPubsD = 0;
            for (int i = 0; i < researcherSize; i++) {
                List<Publication> pubList = resWrapper.getResearchers().get(i).getPublications();
                int publicationsSize = 0;
                if (pubList == null) {
                    publicationsSize = 0;
                } else {
                    publicationsSize = pubList.size();
                }
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
                    } else {
                        D += 1;  // if a publication does not have any class associated then it should be in cat D.
                        //maxPubsD++;
                    }
                }
                if (A > maxPubsA) {
                    maxPubsA = A;
                }
                if (B > maxPubsB) {
                    maxPubsB = B;
                }
                if (C > maxPubsC) {
                    maxPubsC = C;
                }
                if (D > maxPubsD) {
                    maxPubsD = D;
                }
                AList[i] = A;  // Storing # of A's publications for ith researcher
                BList[i] = B;  // Storing # of B's publications for ith researcher
                CList[i] = C;
                DList[i] = D;
                textCatA += A + ",";
                textCatB += B + ",";
                textCatC += C + ",";
                textCatD += D + ",";

            }//end of for loop of researchers

            //   writer.writeToFile("\n\nCategory A: \n" + textCatA+"\n Category B: \n"+textCatB+"\n Category C: \n"+textCatC+"\nCategory D: \n"+textCatD+"\n\n");
            textCatA = "";
            Float percentileCatA[] = new Float[researcherSize]; //first it was AList
            Float percentileCatB[] = new Float[researcherSize];
            Float percentileCatC[] = new Float[researcherSize];
            Float percentileCatD[] = new Float[researcherSize];
            //   writer.writeToFile("Percentile of Category A");
            for (int i = 0; i < AList.length; i++) {
                Integer percentile = 0;
                for (int j = 0; j < _distribution.getCatA().length; j++) {
                    if (j < AList[i]) // I'm not satisfied with this condition
                    {
                        percentile += _distribution.getCatA()[j];
                    }
                }
                percentileCatA[i] = (float) percentile / _totalResearchers;
                percentileCatA[i] = (percentileCatA[i] - (_distribution.getCatA()[0] / _totalResearchers)) / (1 - (_distribution.getCatA()[0] / _totalResearchers));
                if (percentileCatA[i].isNaN() || percentileCatA[i].isInfinite()) {
                    percentileCatA[i] = 0f;
                }
                textCatA += percentileCatA[i] + ",";

            }
            // writer.writeToFile(textCatA);
            //writer.writeToFile("\nPercentile of Category B");
            textCatB = "";

            for (int i = 0; i < BList.length; i++) {
                Integer percentile = 0;
                for (int j = 0; j < _distribution.getCatB().length; j++) {
                    if (j < BList[i]) {
                        percentile += _distribution.getCatB()[j];
                    }
                }
                percentileCatB[i] = (float) percentile / _totalResearchers;
                percentileCatB[i] = (percentileCatB[i] - (_distribution.getCatB()[0] / _totalResearchers)) / (1 - (_distribution.getCatB()[0] / _totalResearchers));
                if (percentileCatB[i].isNaN() || percentileCatB[i].isInfinite()) {
                    percentileCatB[i] = 0f;
                }
                textCatB += percentileCatB[i] + ",";
            }
            //writer.writeToFile(textCatB);
            //writer.writeToFile("\n Percentile of Category C");
            textCatC = "";

            for (int i = 0; i < CList.length; i++) {
                Integer percentile = 0;
                for (int j = 0; j < _distribution.getCatC().length; j++) {
                    if (j < CList[i]) {
                        percentile += _distribution.getCatC()[j];
                    }
                }
                percentileCatC[i] = (float) percentile / _totalResearchers;
                percentileCatC[i] = (percentileCatC[i] - (_distribution.getCatC()[0] / _totalResearchers)) / (1 - (_distribution.getCatC()[0] / _totalResearchers));
                if (percentileCatC[i].isNaN() || percentileCatC[i].isInfinite()) {
                    percentileCatC[i] = 0f;
                }
                textCatC += percentileCatC[i] + ",";
            }
            // writer.writeToFile(textCatC);
            //writer.writeToFile("\n Percentile of Category D");
            textCatD = "";

            for (int i = 0; i < DList.length; i++) {
                Integer percentile = 0;
                for (int j = 0; j < _distribution.getCatD().length; j++) {
                    if (j < DList[i]) {
                        percentile += _distribution.getCatD()[j];
                    }
                }
                percentileCatD[i] = (float) percentile / _totalResearchers;
                percentileCatD[i] = (percentileCatD[i] - (_distribution.getCatD()[0] / _totalResearchers)) / (1 - (_distribution.getCatD()[0] / _totalResearchers));
                if (percentileCatD[i].isNaN() || percentileCatD[i].isInfinite()) {
                    percentileCatD[i] = 0f;
                }
                textCatD += percentileCatD[i] + ",";
            }
            //writer.writeToFile(textCatD);

            // writer.writeToFile("\nResearcher Percentile \n\n");

            BigDecimal percentileRes[] = new BigDecimal[researcherSize];

            for (int i = 0; i < researcherSize; i++) {
                Float result = (4.0f / 7.0f) * percentileCatA[i] + (2.0f / 7.0f) * percentileCatB[i] + (1.0f / 7.0f) * percentileCatC[i];
                // writer.writeToFile(resWrapper.getResearchers().get(i).getName() + "  =  " + result +"\n");
                percentileRes[i] = new BigDecimal(new Float(result));
                m = new Metric();
                m.setName("Percentile");
                m.setValue(percentileRes[i]);
                //if (!(resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard().equals(m)))
                //if (!(checkMetric(resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard(), m))){
                if (resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard().isEmpty()) {
                    resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard().add(m);
                } else {
                    resWrapper.getResearchers().get(i).getMetrics().setMetricsStandard(checkMetric(resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard(), m));
                }
                //resWrapper.getResearchers().get(i).getMetrics().getMetricsStandard().add(m);
                //}

                //  writer.writeToFile(resWrapper.getResearchers().get(i).getName() + "  =  " + result +  "  =  " + percentileRes[i].toString()+"\n");
            }

        }
        return resWrapper;
    }

    private ArrayList<Metric> checkMetric(ArrayList<Metric> metrics, Metric m) {
        if (metrics != null) {
            for (int i = 0; i < metrics.size(); i++) {
                if (metrics.get(i).getName().equals(m.getName())) {
                    metrics.remove(i);
                    metrics.add(m);
                    return metrics;
                }

            }
        }
        return metrics;

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
}

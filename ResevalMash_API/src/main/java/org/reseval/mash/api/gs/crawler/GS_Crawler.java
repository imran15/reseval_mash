/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.api.gs.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.PathParam;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.reseval.mash.beans.Publication;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.beans.Venue;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Kaiser
 */
public class GS_Crawler {

    List<Researcher> rlist;

    public ResearchersWrapper getPublications(String name) throws IOException, InterruptedException {
        ResearchersWrapper ret = new ResearchersWrapper();
        rlist = new ArrayList<Researcher>();
        Researcher r = new Researcher();
        r.setFullName(name);

        int totalNumber = 0;
        List<Publication> list = new ArrayList<Publication>();

        String patternName = "\"" + name + "\"";
        patternName = patternName.replace(" ", "%20");

        //Google Scholar parameters
        String as_q = "as_q=";
        String num = "num=100"; // 100 is the maximum. To make less requests as possible
        String btnG = "btnG=Search%20Scholar";
        String as_sdt = "as_sdt=0";
        String as_sauthors = "as_sauthors=" + patternName;

        String url = "http://scholar.google.it/scholar?" + as_q + "&" + num + "&" + btnG + "&" + as_sdt + "&" + as_sauthors;




// doc variable will get the page requested
        //I have to set fake userAgent otherwise it seems not to work
        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();
        //doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13").get();
        //doc = Jsoup.connect(url).get();


        //  Now I get the total number of results in order to cycle properly
        //search for particular tag and then the use of Pattern and Matcher is to get only the digit
        Elements number = doc.select("#gs_ab_md");
        LinkedList<String> numbers = new LinkedList<String>();

        Pattern pat = Pattern.compile("\\d+");
        Matcher m = pat.matcher(number.text());
        while (m.find()) {
            numbers.add(m.group());

        }


        totalNumber = Integer.parseInt(numbers.get(0));


        //Now i start getting all the results/entry in the page            
        Elements results = doc.select(".gs_r");

        //for each results I have to save publication title, number of citations, venue and year
        for (int i = 0; i < results.size(); i++) {
            Publication p = new Publication();

            //get the titles
            Elements titles = results.get(i).select(".gs_rt a");
            if (!titles.isEmpty()) {

                p.setTitle(titles.first().text());

                //get number of citations
                Elements citations = results.get(i).select(".gs_fl:not(.gs_ggs) a");
                if (!citations.isEmpty()) {

                    //same process as before to get only the digit
                    m = pat.matcher(citations.first().text());
                    numbers.clear();
                    while (m.find()) {
                        numbers.add(m.group());

                    }
                    if (!numbers.isEmpty()) {
                        p.setCitations(Integer.parseInt(numbers.getFirst()));
                    } else {
                        p.setCitations(0);
                    }

//get venue name and year
                    Venue v = new Venue();
                    Elements otherInfo = results.get(i).select(".gs_a");

                    String[] arr = otherInfo.first().text().split(" - ");

                    String[] myInfo = arr[1].split(",");


                    if (myInfo.length == 1) {
                        if (isInt(myInfo[0])) {
                            v.setName("No venue available");
                            p.setVenue(v);
                            p.setYear(new Long(this.getNumber(myInfo[0])));
                        } else {
                            v.setName(myInfo[0]);
                            p.setVenue(v);
                            p.setYear(new Long(0));
                        }

                    } else if (myInfo.length >= 2) {

                        v.setName(myInfo[0]);
                        p.setVenue(v);

                        p.setYear(new Long(this.getNumber(myInfo[1])));


                    }
                    //add publication found to the list that I will return
                    list.add(p);
                }
            }

        }

        if (totalNumber > 100) { //if I have to cicle also for other pages

            int number_of_iterations = totalNumber / 100;

            if ((totalNumber % 100) == 0) {
                number_of_iterations = number_of_iterations - 1;
            }

            for (int k = 1; k < (number_of_iterations + 1); k++) {

                //now I do the same process as before without calculating the number of results(which I have already)
                //and setting properly starting point for every request.
                String as_q2 = "as_q=";
                String num2 = "num=100";
                String btnG2 = "btnG=Search%20Scholar";
                String as_sdt2 = "as_sdt=0";
                String as_sauthors2 = "as_sauthors=" + patternName;
                String start = "start=" + (k * 100);

                String url2 = "http://scholar.google.it/scholar?" + start + "&" + as_q2 + "&" + num2 + "&" + btnG2 + "&" + as_sdt2 + "&" + as_sauthors2;




//I sleep 3000 milliseconds before every requests in order to query politely Google Scholar
                Thread.currentThread().sleep(3000);

                Document doc2;
                doc2 = Jsoup.connect(url).userAgent("Mozilla").get();
                //doc2 = Jsoup.connect(url2).userAgent("Mozilla/5.0 (X11; U; Linux x86_64; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/10.10 (maverick) Firefox/3.6.13").get();
                //doc2 = Jsoup.connect(url2).get();


                Elements results2 = doc2.select(".gs_r");



                for (int i = 0; i < results2.size(); i++) {
                    Publication p2 = new Publication();


                    Elements titles2 = results2.get(i).select(".gs_rt a");

                    if (!titles2.isEmpty()) {



                        p2.setTitle(titles2.first().text());

                        Elements citations2 = results2.get(i).select(".gs_fl:not(.gs_ggs) a");
                        if (!citations2.isEmpty()) {

                            m = pat.matcher(citations2.first().text());
                            numbers.clear();
                            while (m.find()) {
                                numbers.add(m.group());

                            }
                            if (!numbers.isEmpty()) {

                                p2.setCitations(Integer.parseInt(numbers.getFirst()));
                            } else {

                                p2.setCitations(0);
                            }

                            Elements otherInfo2 = results2.get(i).select(".gs_a");
                            String[] arr2 = otherInfo2.first().text().split(" - ");
                            String[] myInfo2 = arr2[1].split(",");

                            Venue v2 = new Venue();


                            if (myInfo2.length == 1) {
                                if (isInt(myInfo2[0])) {
                                    v2.setName("No venue available");
                                    p2.setVenue(v2);
                                    p2.setYear(new Long(this.getNumber(myInfo2[0])));
                                } else {
                                    v2.setName(myInfo2[0]);
                                    p2.setVenue(v2);
                                    p2.setYear(new Long(0));
                                }

                            } else if (myInfo2.length >= 2) {

                                v2.setName(myInfo2[0]);
                                p2.setVenue(v2);
                                p2.setYear(new Long(this.getNumber(myInfo2[1])));


                            }



                            list.add(p2);
                        }
                    }

                }






            }

        }

        r.setPublications(list);
        rlist.add(r);

        ret.setResearchers(rlist);
        return ret;
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getNumber(String s) {
        int ret;

        Pattern pat = Pattern.compile("\\d+");
        Matcher m = pat.matcher(s);
        LinkedList<String> numbers = new LinkedList<String>();
        while (m.find()) {
            numbers.add(m.group());

        }


        ret = Integer.parseInt(numbers.get(0));
        return ret;
    }
}

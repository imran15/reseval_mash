/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.facades.remote.impl;

import org.reseval.mash.beans.Rank;
import org.reseval.mash.beans.Researcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.reseval.mash.beans.Department;
import org.reseval.mash.beans.Faculty;
import org.reseval.mash.beans.Researcher;
import org.reseval.mash.facades.remote.ItalianSourceFacadeRemote;
import org.reseval.mash.beans.QueryMapper;
import org.reseval.mash.beans.Sector;
import org.reseval.mash.beans.University;
import org.reseval.mash.util.StaticVars;
import org.reseval.mash.wrappers.ResearchersWrapper;

/**
 *
 * @author Muhammad Imran
 */
@Stateless
public class ItalianSourceFacade implements ItalianSourceFacadeRemote {

    @PersistenceContext(unitName = "com.reseval_ResevalMash-EJBS_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    @PersistenceContext(unitName = "org.reseval_ResevalMash-ITResearchers-Test")
    private EntityManager emDEV;

    @Override
    public List<University> autoCompleteUni(String partialName) {
        List<University> res = new ArrayList<University>();
        Query q = emDEV.createNativeQuery("select u_id_pk, u_name"
                + " from " + StaticVars.ITResearchers_Universitites_TABLE
                + " where UPPER(u_name) like :partialName"
                + " order by u_name");
        q.setParameter("partialName", partialName.toUpperCase() + "%");
        q.setMaxResults(StaticVars.SUGGESTION_NR);
        List<Object[]> queryRes = q.getResultList();
        University university;
        for (Object[] row : queryRes) {
            university = new University();
            university.setId(((BigDecimal) row[0]).longValue());
            university.setName((String) row[1]);
            res.add(university);
        }
        //addResults(queryRes, res);
        return res;

    }

    @Override
    public List<Department> getAllDeptByUnivID(Long id) {
        List<Department> res = new ArrayList<Department>();
        Query q = emDEV.createNativeQuery("select d_id_pk, d_name"
                + " from " + StaticVars.ITResearchers_Departments_TABLE
                + " where d_u_id = :id");
        q.setParameter("id", id);
        List<Object[]> queryRes = q.getResultList();
        Department department;
        for (Object[] row : queryRes) {
            department = new Department();
            department.setId(((BigDecimal) row[0]).longValue());
            department.setName((String) row[1]);
            res.add(department);
        }
        //addResults(queryRes, res);
        return res;

    }

    @Override
    public String getUniNamebyID(Long id) {
        String res = new String();
        Query q = emDEV.createNativeQuery("select u_id_pk, u_name"
                + " from universities"
                + " where u_id_pk = :id");
        q.setParameter("id", id);
        
        List<Object[]> queryRes = q.getResultList();
        for (Object[] row : queryRes) {
            res =(String) row[1];
        }
        return res;

    }
    private void addResults(List<Object[]> queryRes, List<University> res) {
        University university;
        for (Object[] row : queryRes) {
            university = new University();
            university.setId(((BigDecimal) row[0]).longValue());
            university.setName((String) row[1]);
            university.setWebsite((String) row[2]);
            university.setStreet((String) row[3]);
            university.setZip(((BigDecimal) row[4]).longValue());
            university.setCity((String) row[5]);
            university.setRegion((String) row[6]);

            res.add(university);
        }

    }

    @Override
    public List<Faculty> getAllFacByUniID(Long id) {
        List<Faculty> res = new ArrayList<Faculty>();
        Query q = emDEV.createNativeQuery("select f_id_pk, f_name, f_street, f_zip, f_city"
                + " from " + StaticVars.ITResearchers_Faculties_TABLE
                + " where f_u_id = :id");
        q.setParameter("id", id);
        List<Object[]> queryRes = q.getResultList();
        Faculty faculty;
        for (Object[] row : queryRes) {
            faculty = new Faculty();
            faculty.setId(((BigDecimal) row[0]).longValue());
            faculty.setName((String) row[1]);
            faculty.setAddress((String) row[2]);
            faculty.setZip(((BigDecimal) row[3]).longValue());
            faculty.setCity((String) row[4]);
            res.add(faculty);
        }
        //addResults(queryRes, res);
        return res;
    }

    @Override
    public List<Researcher> getAllResearchers(Integer no_of_res) {
        List<Researcher> res = new ArrayList<Researcher>();
        Query q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
                + " from " + StaticVars.ITResearchers_Researchers_TABLE);
        q.setMaxResults(no_of_res);
        List<Object[]> queryRes = q.getResultList();
        Researcher researcher;
        for (Object[] row : queryRes) {
            researcher = new Researcher();
            researcher.setId(((BigDecimal) row[0]).longValue());
            researcher.setName((String) row[1]);
            researcher.setSurname((String) row[2]);
            res.add(researcher);
        }
        //addResults(queryRes, res);
        return res;

    }

    @Override
    public List<Researcher> getResearcherByUniId(Long uniID) {
        List<Researcher> res = new ArrayList<Researcher>();
        Query q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
                + " from " + StaticVars.ITResearchers_Researchers_TABLE
                + " where rsc_u_id :uniID");
        q.setParameter("uniID", uniID);
        List<Object[]> queryRes = q.getResultList();
        Researcher researcher;
        for (Object[] row : queryRes) {
            researcher = new Researcher();
            researcher.setId(((BigDecimal) row[0]).longValue());
            researcher.setName((String) row[1]);
            researcher.setSurname((String) row[2]);
            res.add(researcher);
        }
        //addResults(queryRes, res);
        return res;
    }

    @Override
    public ResearchersWrapper getResearchersByQuery(List<QueryMapper> queryList) {
        String whereClause = " where ";
        Integer fetch = -1;
        for (int i = 0; i < queryList.size(); i++) {

            if (queryList.get(i).getParamName().equals("fetch")) {
                fetch = Integer.parseInt(queryList.get(i).getParamValue().toString());
            } else {
                if ((queryList.get(i).getParamName().equals("uniID")) && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
                    {whereClause += "rsc_u_id = " + queryList.get(i).getParamValue();
                    whereClause += " and ";
                    }
                } else if ((queryList.get(i).getParamName().equals("depID")) && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
                    {whereClause += "rsc_d_id = " + queryList.get(i).getParamValue();
                    whereClause += " and ";
                    }
                } else if ((queryList.get(i).getParamName().equals("facID")) && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
                    {whereClause += "rsc_f_id = " + queryList.get(i).getParamValue();
                    whereClause += " and ";
                    }
                } else if ((queryList.get(i).getParamName().equals("rankID")) && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
                    {whereClause += "rsc_r_id = " + queryList.get(i).getParamValue();
                    whereClause += " and ";
                    }
                } else if ((queryList.get(i).getParamName().equals("secID")) && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
                    {whereClause += "rsc_s_id = " + queryList.get(i).getParamValue();
                    whereClause += " and ";
                    }
                }

              //  if (i <= (queryList.size() - 1)) { //this condition is just to avoid putting AND in the end of where clause
                //    whereClause += " and ";
                //}
            }

        }
        List<Researcher> res = new ArrayList<Researcher>();
        ResearchersWrapper resList = new ResearchersWrapper();
        Query q;
       // System.out.println("############ before" + whereClause);
        //Process queries with where clause
        if (whereClause.length() > 7) {
            whereClause = whereClause.substring(0, whereClause.lastIndexOf("and")); // This line is only removing last "and" at the end of where clause
           // System.out.println("############ after" + whereClause);
            q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname, rsc_r_id, rsc_u_id, rsc_f_id, rsc_s_id, rsc_d_id, mas_author_id, dblp_author_id"
                    + " from " + StaticVars.ITResearchers_Researchers_TABLE
                    + whereClause);
        } else {
            q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname, rsc_r_id, rsc_u_id, rsc_f_id, rsc_s_id, rsc_d_id, mas_author_id, dblp_author_id"
                    + " from " + StaticVars.ITResearchers_Researchers_TABLE);

        }
        if (fetch != -1) {
            q.setMaxResults(fetch);
        }
        List<Object[]> queryRes = q.getResultList();
        Researcher researcher;
        for (Object[] row : queryRes) {

                researcher = new Researcher();
                researcher.setId(((BigDecimal) row[0]).longValue());
                researcher.setName((String) row[1]+ " " + (String) row[2]);
                //researcher.setSurname((String) row[2]);
                researcher.setRankID(((BigDecimal) row[3]).longValue());
                researcher.setUniversityID(((BigDecimal) row[4]).longValue());
                researcher.setFacultyID(((BigDecimal) row[5]).longValue());
                researcher.setSectorID(((BigDecimal) row[6]).longValue());
                researcher.setDepartmentID(((BigDecimal) row[7]).longValue());
                if (((BigDecimal) row[8]) != null)
                researcher.setMasID(((BigDecimal) row[8]).longValue());
                if (((BigDecimal) row[9]) != null)
                researcher.setDblpID(((BigDecimal) row[9]).longValue());
                res.add(researcher);
        }
        resList.setKey("");
        resList.setResearchers(res);
        return resList;
    }
    
    
    
//     @Override
//    public ResearchersWrapper getResearchersByQuery(List<QueryMapper> queryList) {
//        String whereClause = " where ";
//        Integer fetch = -1;
//        for (int i = 0; i < queryList.size(); i++) {
//
//            if (queryList.get(i).getParamName().equals("fetch")) {
//                fetch = Integer.parseInt(queryList.get(i).getParamValue().toString());
//            } else {
//                if (queryList.get(i).getParamName().equals("uniID") && !(queryList.get(i).getParamValue().equals("") || queryList.get(i).getParamValue() == null)) {
//                    whereClause += "rsc_u_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("depID")) {
//                    whereClause += "rsc_d_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("facID")) {
//                    whereClause += "rsc_f_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("rankID")) {
//                    whereClause += "rsc_r_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("secID")) {
//                    whereClause += "rsc_s_id = " + queryList.get(i).getParamValue();
//                }
//
//                if (i <= (queryList.size() - 1)) { //this condition is just to avoid putting AND in the end of where clause
//                    whereClause += " and ";
//                }
//            }
//
//        }
//        List<Researcher> res = new ArrayList<Researcher>();
//        ResearchersWrapper resList = new ResearchersWrapper();
//        Query q;
//        //Process queries with where clause
//        if (whereClause.length() > 7) {
//            whereClause = whereClause.substring(0, whereClause.lastIndexOf("and")); // This line is only removing last "and" at the end of where clause
//            q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname, rsc_r_id, rsc_u_id, rsc_f_id, rsc_s_id, rsc_d_id, mas_author_id, dblp_author_id"
//                    + " from " + StaticVars.ITResearchers_Researchers_TABLE
//                    + whereClause);
//        } else {
//            q = emDEV.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname, rsc_r_id, rsc_u_id, rsc_f_id, rsc_s_id, rsc_d_id, mas_author_id, dblp_author_id"
//                    + " from " + StaticVars.ITResearchers_Researchers_TABLE);
//
//        }
//        if (fetch != -1) {
//            q.setMaxResults(fetch);
//        }
//        List<Object[]> queryRes = q.getResultList();
//        Researcher researcher;
//        for (Object[] row : queryRes) {
//
//                researcher = new Researcher();
//                researcher.setId(((BigDecimal) row[0]).longValue());
//                researcher.setName((String) row[1]);
//                researcher.setSurname((String) row[2]);
//                researcher.setRankID(((BigDecimal) row[3]).longValue());
//                researcher.setUniversityID(((BigDecimal) row[4]).longValue());
//                researcher.setFacultyID(((BigDecimal) row[5]).longValue());
//                researcher.setSectorID(((BigDecimal) row[6]).longValue());
//                researcher.setDepartmentID(((BigDecimal) row[7]).longValue());
//                if (((BigDecimal) row[8]) != null)
//                researcher.setMasID(((BigDecimal) row[8]).longValue());
//                if (((BigDecimal) row[9]) != null)
//                researcher.setDblpID(((BigDecimal) row[9]).longValue());
//                res.add(researcher);
//        }
//        resList.setKey("");
//        resList.setResearchers(res);
//        return resList;
//    }
    
    
    
    

    @Override
    public List<Rank> autoCompleteforRanks(String partialName) {
        List<Rank> res = new ArrayList<Rank>();
        Query q = emDEV.createNativeQuery("select r_id_pk, r_name"
                + " from " + StaticVars.ITResearchers_Ranks_TABLE
                + " where UPPER(r_name) like :partialName"
                + " order by r_name");
        q.setParameter("partialName", partialName.toUpperCase() + "%");
        q.setMaxResults(StaticVars.SUGGESTION_NR);
        List<Object[]> queryRes = q.getResultList();
        Rank rank;
        for (Object[] row : queryRes) {
            rank = new Rank();
            rank.setId(((BigDecimal) row[0]).longValue());
            rank.setName((String) row[1]);
            res.add(rank);
        }
        //addResults(queryRes, res);
        return res;
    }

    @Override
    public List<Sector> autoCompleteforSectorsName(String partialName) {
        List<Sector> res = new ArrayList<Sector>();
        Query q = emDEV.createNativeQuery("select s_id_pk, s_code, s_name"
                + " from " + StaticVars.ITResearchers_Sectors_TABLE
                + " where UPPER(s_name) like :partialName"
                + " order by s_name");
        q.setParameter("partialName", partialName.toUpperCase() + "%");
        q.setMaxResults(30);
        List<Object[]> queryRes = q.getResultList();
        Sector sector;
        for (Object[] row : queryRes) {
            sector = new Sector();
            sector.setId(((BigDecimal) row[0]).longValue());
            sector.setCode((String) row[1]);
            sector.setName((String) row[2]);
            res.add(sector);
        }
        //addResults(queryRes, res);
        return res;
    }

    @Override
    public List<Sector> autoCompleteforSectorsCode(String partialName) {
        List<Sector> res = new ArrayList<Sector>();
        Query q = emDEV.createNativeQuery("select s_id_pk, s_code, s_name"
                + " from " + StaticVars.ITResearchers_Sectors_TABLE
                + " where UPPER(s_code) like :partialName"
                + " order by s_code");
        q.setParameter("partialName", partialName.toUpperCase() + "%");
        q.setMaxResults(30);
        List<Object[]> queryRes = q.getResultList();
        Sector sector;
        for (Object[] row : queryRes) {
            sector = new Sector();
            sector.setId(((BigDecimal) row[0]).longValue());
            sector.setCode((String) row[1]);
            sector.setName((String) row[2]);
            res.add(sector);
        }
        //addResults(queryRes, res);
        return res;
    }
//    @Override
//    public List<Researcher> getResearchersByQuery(List<QueryMapper> queryList) {
//
//        String whereClause = " where ";
//        Integer fetch = -1;
//        for (int i = 0; i < queryList.size(); i++) {
//
//            if (queryList.get(i).getParamName().equals("fetch")) {
//                fetch = Integer.parseInt(queryList.get(i).getParamValue());
//            } else {
//                if (queryList.get(i).getParamName().equals("uniID")) {
//                    whereClause += "rsc_u_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("depID")) {
//                    whereClause += "rsc_d_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("facID")) {
//                    whereClause += "rsc_f_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("rankID")) {
//                    whereClause += "rsc_r_id = " + queryList.get(i).getParamValue();
//                } else if (queryList.get(i).getParamName().equals("secID")) {
//                    whereClause += "rsc_s_id = " + queryList.get(i).getParamValue();
//                }
//
//                if (i <= (queryList.size() - 1)) {
//                    whereClause += " and ";
//                }
//            }
//
//        }
//
//
//        List<Researcher> res = new ArrayList<Researcher>();
//        Query q;
//        //Process queries with where clause
//        if (whereClause.length() > 7) {
//            whereClause = whereClause.substring(0, whereClause.lastIndexOf("and")); // This line is only removing last "and" at the end of where clause
//            q = em.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
//                    + " from " + StaticVars.ITResearchers_Researchers
//                    + whereClause);
//        } else {
//            q = em.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
//                    + " from " + StaticVars.ITResearchers_Researchers);
//
//        }
//
//        if (fetch != -1) {
//            q.setMaxResults(fetch);
//        }
//        List<Object[]> queryRes = q.getResultList();
//        Researcher researcher;
//        for (Object[] row : queryRes) {
//            researcher = new Researcher();
//            researcher.setId(((BigDecimal) row[0]).longValue());
//            researcher.setName((String) row[1]);
//            researcher.setSurname((String) row[2]);
//            res.add(researcher);
//        }
//        return res;
//    }
////    @Override
////    public ResearcherList getResearchersByQuery2(List<QueryMapper> queryList) {
////        String whereClause = " where ";
////        Integer fetch = -1;
////        for (int i = 0; i < queryList.size(); i++) {
////
////            if (queryList.get(i).getParamName().equals("fetch")) {
////                fetch = Integer.parseInt(queryList.get(i).getParamValue());
////            } else {
////                if (queryList.get(i).getParamName().equals("uniID")) {
////                    whereClause += "rsc_u_id = " + queryList.get(i).getParamValue();
////                } else if (queryList.get(i).getParamName().equals("depID")) {
////                    whereClause += "rsc_d_id = " + queryList.get(i).getParamValue();
////                } else if (queryList.get(i).getParamName().equals("facID")) {
////                    whereClause += "rsc_f_id = " + queryList.get(i).getParamValue();
////                } else if (queryList.get(i).getParamName().equals("rankID")) {
////                    whereClause += "rsc_r_id = " + queryList.get(i).getParamValue();
////                } else if (queryList.get(i).getParamName().equals("secID")) {
////                    whereClause += "rsc_s_id = " + queryList.get(i).getParamValue();
////                }
////
////                if (i <= (queryList.size() - 1)) {
////                    whereClause += " and ";
////                }
////            }
////
////        }
//
//
//        List<Researcher> res = new ArrayList<Researcher>();
//        ResearcherList resList = new ResearcherList();
//        Query q;
//        //Process queries with where clause
//        if (whereClause.length() > 7) {
//            whereClause = whereClause.substring(0, whereClause.lastIndexOf("and")); // This line is only removing last "and" at the end of where clause
//            q = em.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
//                    + " from " + StaticVars.ITResearchers_Researchers
//                    + whereClause);
//        } else {
//            q = em.createNativeQuery("select rsc_id_pk, rsc_name, rsc_surname"
//                    + " from " + StaticVars.ITResearchers_Researchers);
//
//        }
//
//        if (fetch != -1) {
//            q.setMaxResults(fetch);
//        }
//        List<Object[]> queryRes = q.getResultList();
//        Researcher researcher;
//        for (Object[] row : queryRes) {
//            researcher = new Researcher();
//            researcher.setId(((BigDecimal) row[0]).longValue());
//            researcher.setName((String) row[1]);
//            researcher.setSurname((String) row[2]);
//            res.add(researcher);
//        }
//        resList.setId(12345l);
//        resList.setResList(res);
//        return resList;
//    }
}

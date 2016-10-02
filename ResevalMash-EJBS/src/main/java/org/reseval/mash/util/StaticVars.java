/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reseval.mash.util;

/**
 *
 * @author Muhammad Imran
 */
public class StaticVars {

    
    //SQL Related
    public static final String SQL_GreaterThan= " > ";
    public static final String SQL_Equal= " = ";
    public static final String SQL_GreaterThanEqualTo= " >= ";
    public static final String SQL_LessThan= " < ";
    public static final String SQL_LessThanEqualTo= " <= ";

    //IT Researchers Tables
    public static final String ITResearchers_Universitites_TABLE= "universities";
    public static final String ITResearchers_Departments_TABLE= "departments";
    public static final String ITResearchers_Researchers_TABLE= "researchers";
    public static final String ITResearchers_Faculties_TABLE= "faculties";
    public static final String ITResearchers_Ranks_TABLE= "ranks";
      public static final String ITResearchers_Sectors_TABLE= "sectors";

    public static final Integer ALL_RESEARCHERS_FETCH_NO= 10;

    
    
    public static final String CACHE_VENUE_KEY = "8888";
    public static final Long CACHE_RESEARCHERS_KEY = 12345l;
    public static final Long CACHE_ITDS_QUERY_KEY = 99l;
    public static final Long CACHE_MasPub_QUERY_KEY = 55l;
    
    public static final long ERROR_ID = -1l;
    public static final String USER_ID_STRING = "userId";
    public static final long GUEST_USER_ID = -1L;
    public static final Integer PUBLIC_VISIBILITY = 1;
    public static final Integer READABLE = 1;
    public static final Integer EDITABLE = 2;
    public static final Integer SUGGESTION_NR = 20;
    public static final Integer MAX_RESEARCHERS = 100;
    public static final Integer TOP_COAUTHORS = 2;
    public static final String TOP_VALUE = "5";
    public static final String PUBLICATION_TABLE = "target_db.mv_resource_citation_info";
    public static final String RESOURCE_NUMBER_TABLE = "target_db.mv_reseval_n_resources";
    public static final String CITER_TABLE = "target_db.res_citation_by_resource";
    public static final String COAUTHORS_TABLE = "target_db.mv_reseval_coauthors";
    public static final String KEYNOTE_TABLE = "target_db.author_keynote";
    public static final String GROUP_TABLE = "groups2";
    public static final String COMPARISON_TABLE = "comparisons2";
    public static final String GROUP_PERMISSION_TABLE = "user_group_permissions";
    public static final String COMPARISON_PERMISSION_TABLE = "user_comparison_permissions";
    public static final String COMPARISON_GROUPS_TABLE = "comparison_groups2";
    public static final String H_INDEX = " H-index";
    public static final String G_INDEX = " G-index";
    public static final String C_INDEX = " Citations";
    public static final String UCount_RV = " UCount-RV";
    public static final String UCount_SE = " UCount-SE";
    public static final Integer NORMAL_PUB = 0;
    public static final Integer EXCLUDED_PUB = 1;
    public static final String CACHE_NAME = "ResearcherDataCache";
    public static final String SERVER_NAME = "http://localhost:8081/ResEvalAPI/resources/";
    //Homophily
    public static final String HW_SERVER_PATH = "http://ats.cs.ut.ee:8080/HWMetrics/resources/hpwccAuthors";
    public static final String HOMOPHILY_TABLE = "hw_researcher_ids";
}

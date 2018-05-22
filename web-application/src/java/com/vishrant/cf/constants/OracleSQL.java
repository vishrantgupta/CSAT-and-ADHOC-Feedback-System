package com.vishrant.cf.constants;

/**
 *
 * @author VG00124233 vg00124233@vishrant.com
 */
public interface OracleSQL {
    
    String UPDATE_LAST_LOGIN = "UPDATE USERS SET LAST_LOGIN = ? WHERE USER_ID = ?";

    String FETCH_QUESTION_SQL = "SELECT question_txt               "
                            + "     , question_id                "
                            + "     , category                   "
                            + "     , type                       "
                            + "     , DISPLAY_ORDER              "
                            + "     , category_order             "
                            + "     , rating_max_value           "
                            + "     , count                      "
                            + "  FROM QUESTION                   "
                            + " ORDER BY category_order          "
                            + "     , DISPLAY_ORDER";

    String FETCH_ALL_APP_NAME_BY_ENGAGEMENT_SQL = "SELECT DISTINCT a.app_id as app_id" + 
                                                  "     , a.app_name as app_name " + 
                                                  "     , a.client_name as client_name " +
                                                  "  FROM app a"  + 
                                                  " WHERE a.engagement_id = ? "+ 
                                                  " ORDER BY upper(app_name)";

    String FETCH_ENGAGEMENT_SQL = "SELECT engagement_id as engagementId"
                                + "     , engagement_name as engagementName"
                                + "     , act_ind as actInd "
                                + "     , description as description"
                                + "  FROM engagement "
                                + " ORDER BY upper(engagement_name)";

    String ADD_ENGAGEMENT_SQL = "{call insertEngagementDetails(?, ?, ?)}";

    String ADD_APPLICATION_SQL = "{call insertAppDetails (?, ?, ?, ?)}";

    String FETCH_APPLICATION_DETAILS = "SELECT a.APP_ID    AS appId "
                                     + "     , a.APP_NAME      AS appName "
                                     + "     , (select NVL(rtrim (xmlagg (xmlelement (r, (clientName) || '#')).extract ('//text()'), '#'), 'NONE') clientDetails " 
                                     + "          FROM (SELECT distinct (first_name || '$' || last_name) as clientName " 
                                     + "  FROM client_details " 
                                     + " WHERE client_id IN " 
                                     + "  (SELECT client_id FROM app_client_mapping WHERE app_id = ?)) client) as clientName, " 
                                     + " a.ENGAGEMENT_ID AS engagementId " 
                                     + " FROM APP a "
                                     + " WHERE APP_ID = ?";
    
    
    
    String UPDATE_APPLICATION_DETAILS = "{call updateAppDetails (?, ?, ?, ?)}";

    String LOGIN_SQL =    "SELECT u.user_id as user_id"
                        + "     , u.user_name as user_name"
                        + "     , u.first_name as first_name"
                        + "     , u.last_name as last_name"
                        + "     , u.organization as organization"
                        + "     , u.last_login as last_login"
                        + "     , (select NVL(rtrim (xmlagg (xmlelement (r, (ROLE_NAME) || ',')).extract ('//text()'), ','), 'NONE') role_name "
                        + "            FROM ROLE "
                        + "           WHERE ROLE_ID IN ("
                        + "           SELECT rm.secondary_role_id"
                        + "             FROM role_mappings rm"
                        + "            WHERE rm.primary_role_id ="
                        + "                                      ("
                        + "                                        SELECT ur.role_id"
                        + "                                          FROM user_role ur"
                        + "                                         WHERE ur.user_id = (select user_id from users where UPPER(user_name) = UPPER(?))"
                        + "                                      )"
                        + "                            )) as role_name"
                        + "  FROM USERS u"
                        + " WHERE UPPER(USER_NAME) = UPPER(?)"
                        + "   AND PASSWORD = ?"
                        + "   AND upper(u.ACT_IND) = 'Y'"
                        + " group by u.user_id,"
                        + "  u.user_name,"
                        + "  u.first_name,"
                        + "  u.last_name,"
                        + "  u.organization,"
                        + "  u.last_login";

    String FETCH_ENGAGEMENT_FOR_USERNAME_SQL = "select e.engagement_name as engagement_name , e.engagement_id as engagement_id "
                                            + "  from engagement e"
                                            + "     , user_engagement ue"
                                            + "     , users u "
                                            + " where ue.user_id = (select iu.user_id from users iu where upper(iu.user_name) = upper(?))"
                                            + "   and u.user_id = ue.user_id "
                                            + "   and e.engagement_id = ue.engagement_id";
    
    String FETCH_APP_NAME_FOR_GIVEN_FEEDBACK_DATE_SQL = "SELECT a.app_id, a.APP_NAME " +
                                                        " FROM app a" +
                                                        "    , user_app_mapping uam " +
                                                        "WHERE a.app_id IN (" +
                                                        "            SELECT DISTINCT f.APP_ID" +
                                                        "             FROM feedback f" +
                                                        "            WHERE UPPER(f.FEEDBACK_TYPE) = UPPER(?)" +
                                                        "              AND f.APP_ID IS NOT NULL " +
                                                        "       AND ENGAGEMENT_ID = ? " +
                                                        "              AND TO_CHAR(f.response_date, 'mm/dd/yyyy') BETWEEN ? AND ?" +
                                                        ") " +
                                                        " AND a.app_id        = uam.app_id " +
                                                        " AND uam.user_id     = ? ";
    
    String FETCH_APP_NAME_FOR_ENGAGEMENT_ID = "SELECT a.app_id as app_id" + 
                                                "   , a.app_name as app_name " +
                                                "FROM app a"  + 
                                                "   , user_app_mapping uam " +
                                                "WHERE a.engagement_id = ? " +
                                                "  AND a.app_id        = uam.app_id " +
                                                "  AND uam.user_id     = ?";
    
    String APP_CLIENT_DETAILS = "select NVL(rtrim (xmlagg (xmlelement (r, (clientName) || '/')).extract ('//text()'), '/'), 'NONE') clientDetails  " +
                                "  FROM (SELECT distinct (first_name || ' ' || last_name) as clientName  " +
                                         " FROM client_details  " +
                                        " WHERE client_id IN  " +
                                        "( " +
                                            "SELECT client_id " + 
                                             " FROM app_client_mapping " + 
                                            " WHERE app_id = ? " + 
                                         ") " + 
                                       " ORDER BY upper(clientName)) client";
    
    String FETCH_INCIDENT_ID_BY_APP_ID = "SELECT distinct incident_id FROM feedback WHERE app_id = ? ORDER BY incident_id asc";

    String FETCH_AVAILABLE_APP = " SELECT A.APP_ID as appId"
                                + "     , A.APP_NAME as appName"
                                + "     , A.CLIENT_NAME as clientName"
                                + "     , A.ENGAGEMENT_ID as engagementId"
                                + "     , E.ENGAGEMENT_NAME engagementName"
                                + "  FROM APP A"
                                + "     , ENGAGEMENT E"
                                + " WHERE A.ENGAGEMENT_ID = E.ENGAGEMENT_ID"
                                + " ORDER BY upper(ENGAGEMENT_NAME)"
                                + "     , upper(APP_NAME)";
    
    String CHANGE_PASSWORD_SQL = "{call updatePassword (?, ?, ?, ?)}";
    
    String ADD_CLIENT_DETAILS = "{call insertClientDetails(?,?,?,?)}";
    
    String FETCH_CLIENTS_FOR_ENGAGEMENT = "SELECT CLIENT_ID AS CLIENT_ID"
                                        + "     , FIRST_NAME AS FIRST_NAME"
                                        + "     , LAST_NAME AS LAST_NAME "
                                        + "  FROM CLIENT_DETAILS "
                                        + " WHERE ENGAGEMENT_ID = ?"
                                        + " ORDER BY UPPER(FIRST_NAME)"
                                        + "     , UPPER(LAST_NAME)";
    
}

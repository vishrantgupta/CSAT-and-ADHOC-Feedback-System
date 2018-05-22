package com.vishrant.cf.services;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.bean.Feedback;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import com.vishrant.cf.utils.CommonUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class Service {

    final Logger log = Logger.getLogger(Service.class);

    public SortedSet<Entry<String,String>> getEnagegementList(String userName) {

        HashMap<String, String> engagementList = new HashMap<>();

        try (Connection connection = DBHelper.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_ENGAGEMENT_FOR_USERNAME_SQL);
            statement.setString(1, userName);

            ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    engagementList.put(rs.getString("engagement_id"), rs.getString("engagement_name"));
                }
            }
            
            return engagementList.size() > 0
                    ? CommonUtils.sortMapByValues(engagementList) : null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SortedSet<Entry<String, String>> getAppNameOnTypeEngagementIdNDate(String feedbackType, String engagementId, String fromDate, String toDate, String userId) {
        HashMap<String, String> appNameList = new HashMap<>();

        try (Connection connection = DBHelper.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_APP_NAME_FOR_GIVEN_FEEDBACK_DATE_SQL);
            statement.setString(1, feedbackType);
            statement.setInt(2, Integer.parseInt(engagementId));
            statement.setString(3, fromDate);
            statement.setString(4, toDate);
            statement.setInt(5, Integer.parseInt(userId));

            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    appNameList.put(rs.getString("app_id"), rs.getString("APP_NAME"));
                }
            }

            return appNameList.size() > 0
                    ? CommonUtils.sortMapByValues(appNameList) : null;

        } catch (Exception e) {
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public SortedSet<Map.Entry<String, String>> getAppNameForEngagementId(String engagementId, String userId) {
        HashMap<String, String> appNameList = new HashMap<>();

        try (Connection connection = DBHelper.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_APP_NAME_FOR_ENGAGEMENT_ID);
            statement.setInt(1, Integer.parseInt(engagementId));
            statement.setInt(2, Integer.parseInt(userId));
            
            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    appNameList.put(rs.getString("app_id"), rs.getString("app_name"));
                }
            }

            return appNameList.size() > 0
                    ? CommonUtils.sortMapByValues(appNameList) : null;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public ArrayList<ApplicationBean> getAllAppNamesForEngagementId(String engagementId) {
        // HashMap<String, String> appNameList = new HashMap<>();

        ArrayList<ApplicationBean> arrayList = new ArrayList<ApplicationBean>();
        
        try (Connection connection = DBHelper.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_ALL_APP_NAME_BY_ENGAGEMENT_SQL);
            statement.setString(1, engagementId);
            
            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    
                    ApplicationBean applicationBean = new ApplicationBean();
                    applicationBean.setAppId(Integer.parseInt(rs.getString("app_id")));
                    applicationBean.setAppName(rs.getString("APP_NAME"));
                    
                    // TODO
                    // applicationBean.setClientName(rs.getString("client_name"));
                    
                    arrayList.add(applicationBean);
                    // appNameList.put(rs.getString("app_id"), rs.getString("APP_NAME"));
                }
            }

            Collections.sort(arrayList, Collections.reverseOrder());
            
            return arrayList.size() > 0
                    ? arrayList : null;

        } catch (Exception e) {
            e.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public String getClientNameForSelectedAppId(String appId) {

        if (appId != null) {
            try (Connection connection = DBHelper.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(OracleSQL.APP_CLIENT_DETAILS);
                statement.setInt(1, Integer.parseInt(appId));
                
                ResultSet rs = statement.executeQuery();
                String clientName = "";
                if (rs != null) {
                    while (rs.next()) {
                        clientName = rs.getString("clientDetails");
                    }
                }
                return clientName.length() > 0 ? clientName : null;

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + " getClientNameForSelectedAppId " + e);
            }
        }

        return null;
    }

    public HashMap<String, String> getIncidentIdListOnEngagementIdAppId(String feedbackType, String engagementId, String appId, String fromDate, String toDate) {

        HashMap<String, String> incidentIdList = new HashMap<>();

        if (feedbackType != null && engagementId != null && fromDate != null && toDate != null) {
            try (Connection connection = DBHelper.getConnection()) {

                String FETCH_APP_NAME = "SELECT DISTINCT incident_id, brief_desc"
                                    + "  FROM FEEDBACK"
                                    + " WHERE UPPER(feedback_type) = upper('" + feedbackType + "')"
                                    + "   AND incident_id IS NOT NULL"
                                    + "   AND TO_CHAR(response_date, 'mm/dd/yyyy') BETWEEN '" + fromDate + "' AND '" + toDate + "'"
                                    + (appId != null ? "   AND app_id = " + appId : "")
                                    + "   AND engagement_id = " + engagementId;
                
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(FETCH_APP_NAME);

                if (rs != null) {
                    while (rs.next()) {
                        incidentIdList.put(rs.getString("incident_id"), rs.getString("brief_desc"));
                    }
                }
                return incidentIdList.size() > 0
                        ? incidentIdList : null;

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }
        }

        return null;

    }

    public ArrayList<String> getIncidentIdList(String appId) {

        ArrayList<String> incidentIdList = new ArrayList<>();

        if (appId != null) {
            try (Connection connection = DBHelper.getConnection()) {

                PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_INCIDENT_ID_BY_APP_ID);
                statement.setInt(1, Integer.parseInt(appId));
                
                ResultSet rs = statement.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        incidentIdList.add(rs.getString("incident_id"));
                    }
                }
                return incidentIdList.size() > 0
                        ? incidentIdList : null;

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }
        }

        return null;
    }

    public ArrayList<Feedback> getReport(String appId, String incidentId) {

        ArrayList<Feedback> feedbackList = new ArrayList<>();

        if (incidentId != null) {
            try (Connection connection = DBHelper.getConnection()) {

                Statement statement = connection.createStatement();

                String sql = "SELECT distinct q.CATEGORY_order"
                        + "      , q.display_order"
                        + "      , f.question_id as question_id"
                        + "      , q.question_txt as question_txt"
                        + "      , q.type as question_type"
                        + "      , f.RESPONSE_rating as rating"
                        + "      , f.response_TXT as response"
                        + "      , q.category as category"
                        + "      , DECODE(q.rating_max_value, NULL, 'N/A', q.rating_max_value) as rating_max_value"
                        + "   FROM FEEDBACK f, question q"
                        + "  WHERE f.question_id = q.question_id"
                        + (appId != null ? " AND app_id = " + appId : "")
                        + "    AND incident_id = '" + incidentId + "'"
                        + "  ORDER BY q.CATEGORY_order, q.display_order, f.RESPONSE_rating";

                ResultSet rs = statement.executeQuery(sql);

                if (rs != null) {
                    while (rs.next()) {
                        Feedback feedback = new Feedback();
                        feedback.setQuestionId(rs.getInt("question_id"));
                        feedback.setQuestionTxt(rs.getString("question_txt"));
                        feedback.setQuestionType(rs.getString("question_type"));
                        feedback.setRating(rs.getString("rating"));
                        feedback.setResponse(rs.getString("response"));
                        feedback.setCategory(rs.getString("category"));
                        feedback.setRatingMaxValue(rs.getString("rating_max_value"));

                        feedbackList.add(feedback);
                    }
                }

                return feedbackList.size() > 0
                        ? feedbackList : null;
            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }
        }

        return null;
    }
    
}

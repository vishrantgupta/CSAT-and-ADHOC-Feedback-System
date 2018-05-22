package com.vishrant.cf.services;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationOperationsService {

    final Logger log = Logger.getLogger(ApplicationOperationsService.class);

    public String saveApplications(ArrayList<ApplicationBean> appList) {

        String currentApp = "";

        Connection connection = null;
        PreparedStatement statement = null;
        
        try {

            connection = DBHelper.getConnection();
            statement = connection.prepareStatement(OracleSQL.ADD_APPLICATION_SQL);

            for (ApplicationBean application : appList) {

                currentApp = application.getAppName();

                List<String> clientList = application.getClientId();
                String clientStr = "";
                for (String clientId : clientList) {
                    clientStr += clientId + ",";
                }
                
                statement.setString(1, application.getAppName());
                statement.setInt(2, application.getEngagementId());
                statement.setInt(3, application.getCreatedBy());
                statement.setString(4, clientStr.substring(0, clientStr.length() - 1));
                
                statement.addBatch();
                // statement.executeUpdate();
            }

            statement.executeBatch();
            statement.close();
            connection.commit();

            for (ApplicationBean savedApp : appList) {
                log.debug("New applications saved " + savedApp.getAppName() + " engagement id " + savedApp.getEngagementId());
            }

            return "Applications are saved successfully.";

        } catch (BatchUpdateException e) {
            e.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException se) {
                    log.error(AppConstants.LOGGING_EXCEPTION + se);
                }
            }
            if (e.getMessage().contains("already exists")) {
                return "Application with name: " + currentApp + " already exist.";
            }
        } catch (SQLException se) {
            se.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + se);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException sex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + sex);
                }
            }
            if (se.getMessage().contains("already exists")) {
                return "Application with name: " + currentApp + " already exist.";
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException se) {
                    log.error(AppConstants.LOGGING_EXCEPTION + se);
                }
            }
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException se) {
                    log.error(AppConstants.LOGGING_EXCEPTION + se);
                }
            }
        }
        return null;
    }

    public ApplicationBean getApplicationDetails(Integer appId) {

        try (Connection connection = DBHelper.getConnection(); PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_APPLICATION_DETAILS);) {

            statement.setInt(1, appId);
            ResultSet rs = statement.executeQuery();
            
            ApplicationBean bean = new ApplicationBean();
            int count = 0;

            while (rs.next()) {
                bean.setAppId(rs.getInt("appId"));
                bean.setAppName(rs.getString("appName"));
                bean.setClientName(Arrays.asList(rs.getString("clientName").split("#")));
                bean.setEngagementId(rs.getInt("engagementId"));

                ++count;
            }

            return count == 1 ? bean : null;

        } catch (Exception e) {
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public String updateApplication(ApplicationBean bean) {

        try (Connection connection = DBHelper.getConnection(); PreparedStatement statement = connection.prepareStatement(OracleSQL.UPDATE_APPLICATION_DETAILS);) {
            
            statement.setInt(1, bean.getAppId());
            statement.setString(2, bean.getAppName());
            
            // TODO
            // statement.setString(3, bean.getClientName());
            statement.setInt(4, bean.getEngagementId());

            statement.executeUpdate();

            log.debug("Applicaiton " + bean.getAppName() + " is updated successfully by user id " + bean.getCreatedBy());
            return "Application updated successfully.";

        } catch (SQLException se) {
            se.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + se);

            if (se.getMessage().contains("already exists")) {
                return "Application with name: " + bean.getAppName() + " already exist.";
            }
        } catch (Exception e) {
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }
}

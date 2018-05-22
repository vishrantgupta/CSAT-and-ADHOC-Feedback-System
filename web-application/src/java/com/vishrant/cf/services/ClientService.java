/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.services;

import com.vishrant.cf.bean.ClientBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ClientService {

    final Logger log = Logger.getLogger(ClientService.class);
    
    public String saveClientDetails(ArrayList<ClientBean> list, String createdBy) {

        Connection connection = null;
        PreparedStatement statement = null;
        
        try {

            connection = DBHelper.getConnection();
            statement = connection.prepareStatement(OracleSQL.ADD_CLIENT_DETAILS);
            
            for (ClientBean clientBean : list) {
                statement.setString(1, clientBean.getFirstName().trim());
                statement.setString(2, clientBean.getLastName().trim());
                statement.setInt(3, clientBean.getEngagementId());
                statement.setInt(4, Integer.parseInt(createdBy));
                
                statement.addBatch();
            }

            synchronized (statement) {
                statement.executeBatch();
            }
            
            connection.commit();

            return "true";
        } catch (Exception e) {
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            
            log.error("Exception occured " + e);
            final String msg = "Error: Please check if client already exists for this engagement";
            if (e.getMessage().contains("already exists")) {
                return msg;
            }
            if (e.getMessage().toUpperCase().contains("unique constraint".toUpperCase())) {
                return msg;
            }
            
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
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
        }
        return "false";
    }
    
    public ArrayList<ClientBean> getClientListForEngagement(Integer engagementId) {
        
        ArrayList<ClientBean> arrayList = new ArrayList<ClientBean>();
        
        try (Connection connection = DBHelper.getConnection()) {

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_CLIENTS_FOR_ENGAGEMENT);
            statement.setInt(1, engagementId);
            
            ResultSet rs = statement.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    
                    ClientBean clientBean = new ClientBean();
                    
                    clientBean.setFirstName(rs.getString("FIRST_NAME"));
                    clientBean.setLastName(rs.getString("LAST_NAME"));
                    clientBean.setClientId(rs.getInt("CLIENT_ID"));
                    
                    arrayList.add(clientBean);
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
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.db;

import com.vishrant.cf.constants.AppConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.log4j.Logger;

/**
 *
 * @author VG00124233
 */
public class NativeDBConnection {

    private final static Logger LOG = Logger.getLogger(NativeDBConnection.class);
    
    public static Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            connection = DriverManager.getConnection(AppConstants.CONNECTION_URL, AppConstants.DB_USER_NAME, AppConstants.DB_PASSWORD);

            return connection;

        } catch (Exception e) {
            LOG.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }
}
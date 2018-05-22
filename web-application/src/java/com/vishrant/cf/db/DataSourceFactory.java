/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.db;

import com.vishrant.cf.constants.AppConstants;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class DataSourceFactory {

    final static Logger log = Logger.getLogger(DataSourceFactory.class);
    
    public static DataSource getOracleDataSource() {

        OracleDataSource oracleDS = null;
        try {

            oracleDS = new OracleDataSource();
            oracleDS.setURL(AppConstants.CONNECTION_URL);
            oracleDS.setUser(AppConstants.DB_USER_NAME);
            oracleDS.setPassword(AppConstants.DB_PASSWORD);
        } catch (SQLException e) {
            log.error(AppConstants.LOGGING_EXCEPTION + e);
            e.printStackTrace();
        }

        return oracleDS;
    }

    public static Connection getConnection() throws SQLException {
        return getOracleDataSource().getConnection();
    }
}
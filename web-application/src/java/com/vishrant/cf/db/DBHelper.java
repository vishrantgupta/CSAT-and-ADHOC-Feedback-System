/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author VG00124233
 */
public final class DBHelper {

    private final static Logger LOG = Logger.getLogger(DBHelper.class);

    private static Connection connection = null;

    private DBHelper() {
    }

    public static Connection getConnection() {
        
        Connection con = null;
        
        try {
            if (connection != null && !connection.isClosed()) {
                con = connection;
            } else {
                con = DataSourceFactory.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Exception in getConnection " + e);
        } catch (Exception e) {
            e.printStackTrace();;
            LOG.error("Exception in getConnection " + e);
        }
        
        // trying to get native db connection if null
        if (con == null) {
            LOG.error("ERROR: Application running with Native DB Connection.");
            con = NativeDBConnection.getConnection();
        }
        
        return con;
    }
    
}
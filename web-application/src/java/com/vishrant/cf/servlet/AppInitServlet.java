package com.vishrant.cf.servlet;

import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.db.DBHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class AppInitServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        initConfigConstants();
        initLogging();

        checkDBConnectivity();

    }

    final Logger log;
    // File path = null;

    public AppInitServlet() throws Exception {
        
        try {
            log = Logger.getLogger("com.vishrant.cf.Log4jInit:initLogging");
            
            // String configStream = getServletContext().getRealPath("/WEB-INF/log4jConfig.xml");
            
            // System.out.println("configStream >>>> " + configStream);
            
            // ServletContext context = getServletContext();
            // String fullPath = context.getRealPath("/WEB-INF");
            
            // System.out.println(">>>> " + fullPath);
            
            // path = new File(fullPath);
        } catch (Exception e) {
            System.out.println("ERROR: exception occured while deploying ICMF. " + e);
            throw e;
        }
        
    }

    private void initLogging() throws ServletException {

        try {

            String file = AppConstants.EXT_CONFIG_PATH + "/config/log4jConfig.xml";
            
            if (file != null) {
                DOMConfigurator.configure(file);
            } else {
                throw new ServletException("FATAL Error: log4j configuration file not found");
            }

            log.debug("Capture Feedback installed and Log4j configured.");

        } catch (Exception e) {
            throw new ServletException("FATAL Error: log4j configuration file not found");
        }

    }

    private void initConfigConstants() throws ServletException {

        Properties configProperties = new Properties();
        String configPropertyFilename = "/config/configuration.properties";

        FileInputStream in;
        File configPropertyFile = new File(AppConstants.EXT_CONFIG_PATH + configPropertyFilename);

        System.err.println("AppConstants.EXT_CONFIG_PATH >>>> " + AppConstants.EXT_CONFIG_PATH + configPropertyFilename);
        
        if ((!configPropertyFile.exists())) {
            System.err.println("FATAL ERROR: <" + configPropertyFilename + "> file not found. ");
            throw new ServletException("FATAL ERROR configuration file not found");
        } else {

            try {
                in = new FileInputStream(configPropertyFile);
                configProperties.load(in);
                in.close();

                initConfigFile(configProperties);
            } catch (ServletException se) {
                throw new ServletException(se.getMessage());
            } catch (Exception e) {
                System.err.println("Exception occured: " + e);
            }

        }

        return;
    }

    private void initConfigFile(Properties configFile) throws ServletException, Exception {

        AppConstants.EXT_CONFIG_FILE = configFile.getProperty("config_file_path").trim();

        File extConfigFile = new File(AppConstants.EXT_CONFIG_FILE);
        FileInputStream in;

        if ((!extConfigFile.exists())) {
            System.err.println("FARTAL ERROR: config file not found taking default values.");
            throw new ServletException("FATAL ERROR configuration file not found");
        } else {
            Properties configProperties = new Properties();

            in = new FileInputStream(extConfigFile);
            configProperties.load(in);
            in.close();

            AppConstants.CONNECTION_URL = configProperties.getProperty("connectionURL").trim();
        }

    }

    private void checkDBConnectivity() throws ServletException {
        Connection connection = DBHelper.getConnection();
        try {
            if (connection == null || connection.isClosed()) {
                throw new ServletException("CONNECTIVITY ISSUE: DB Connectivity failed.");
            }
        } catch (SQLException ex) {
            System.err.println("CONNECTIVITY ISSUE: DB Connectivity failed.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new ServletException("CONNECTIVITY ISSUE: DB closing issue.");
                }
            }
        }
        
    }
    
}
package com.vishrant.cf.services;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationService {

    public ArrayList<ApplicationBean> getAvailableApplications() {

        try (Connection connection = DBHelper.getConnection()) {

            ArrayList<ApplicationBean> applicationList = new ArrayList<ApplicationBean>();

            PreparedStatement statement = connection.prepareStatement(OracleSQL.FETCH_AVAILABLE_APP);

            ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    ApplicationBean bean = new ApplicationBean();

                    bean.setAppId(rs.getInt("appId"));
                    bean.setAppName(rs.getString("appName"));
                    
                    // TODO
                    // bean.setClientName(rs.getString("clientName"));
                    bean.setEngagementId(rs.getInt("engagementId"));
                    bean.setEngagementName(rs.getString("engagementName"));

                    applicationList.add(bean);
                }
            }

            // Collections.sort(applicationList);
            return applicationList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
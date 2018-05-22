/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.services;

import com.vishrant.cf.bean.EngagementBean;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124232@vishrant.com
 */
public class EngagementService {

    final Logger log = Logger.getLogger(EngagementService.class);

    public ArrayList<EngagementBean> getAvailableEngagements() {

        try (Connection connection = DBHelper.getConnection()) {

            ArrayList<EngagementBean> engagementList = new ArrayList<EngagementBean>();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(OracleSQL.FETCH_ENGAGEMENT_SQL);

            if (rs != null) {
                while (rs.next()) {

                    EngagementBean bean = new EngagementBean();
                    bean.setEngagementId(rs.getInt("engagementId"));
                    bean.setEngagementName(rs.getString("engagementName"));
                    bean.setActInd(("" + rs.getString("actInd")).charAt(0));
                    bean.setDescription(rs.getString("description"));

                    engagementList.add(bean);
                }
            }

            log.debug("Engagement list size " + engagementList.size());

            Collections.sort(engagementList, Collections.reverseOrder());

            return engagementList;
        } catch (Exception e) {
            log.error("Exception occured " + e);
            e.printStackTrace();
        }

        return null;
    }

    public String saveNewEngagement(EngagementBean bean) {

        try (Connection connection = DBHelper.getConnection(); PreparedStatement statement = connection.prepareStatement(OracleSQL.ADD_ENGAGEMENT_SQL);) {

            statement.setString(1, bean.getEngagementName());
            statement.setString(2, bean.getDescription());
            statement.setString(3, bean.getCreatedBy());

            statement.execute();

            connection.commit();

            return "true";
        } catch (Exception e) {
            log.error("Exception occured " + e);
            String msg = "Engagement with name: " + bean.getEngagementName() + " already exists.";
            if (e.getMessage().contains("already exists")) {
                return msg;
            }
            if (e.getMessage().toUpperCase().contains("unique constraint".toUpperCase())) {
                return msg;
            }
        }

        return "false";
    }
}
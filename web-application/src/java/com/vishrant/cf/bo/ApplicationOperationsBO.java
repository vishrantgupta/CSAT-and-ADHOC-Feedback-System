/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.bo;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.services.ApplicationOperationsService;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationOperationsBO {

    final Logger log = Logger.getLogger(ApplicationOperationsBO.class);

    public String saveNewApplications(String saveData, String userId) {

        try {

            String[] singleAppData = saveData.split("#");

            ArrayList<ApplicationBean> appList = new ArrayList<ApplicationBean>();

            for (String app : singleAppData) {
                String[] appDetails = app.split("\\|", -1);

                ApplicationBean bean = new ApplicationBean();
                bean.setEngagementId(Integer.parseInt(appDetails[0]));
                bean.setAppName(appDetails[1]);
                bean.setClientId(Arrays.asList(appDetails[2].split(",")));
                bean.setCreatedBy(Integer.parseInt(userId));
                
                appList.add(bean);
            }

            ApplicationOperationsService operationsService = new ApplicationOperationsService();
            return operationsService.saveApplications(appList);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public String getApplicationDetails(Integer appId) {

        try {

            ApplicationOperationsService service = new ApplicationOperationsService();
            ApplicationBean bean = service.getApplicationDetails(appId);

            if (bean != null) {
                StringBuilder sb = new StringBuilder();

                // TODO
                // String[] clientDetails = bean.getClientName().split("\\s+");

                /*String firstName = "";
                String lastName = "";
                if (clientDetails.length > 1) {
                    firstName = clientDetails[0];
                    lastName = clientDetails[1];
                }*/

                sb.append("<appId>" + bean.getAppId() + "</appId>");
                sb.append("<appName>" + StringEscapeUtils.escapeHtml(bean.getAppName()) + "</appName>");
                sb.append("<firstName>" + StringEscapeUtils.escapeHtml(bean.getAllClientName()) + "</firstName>");
                sb.append("<clientName>" + StringEscapeUtils.escapeHtml(bean.getAllClientName()) + "</clientName>");

                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public String updateApplication(Integer appId, String appName, String firstName, String lastName, Integer engagementId) {

        ApplicationOperationsService service = new ApplicationOperationsService();
        ApplicationBean bean = new ApplicationBean();

        bean.setAppId(appId);
        bean.setAppName(appName);
        
        // TODO
        // bean.setClientName(firstName + " " + lastName);
        bean.setEngagementId(engagementId);

        return service.updateApplication(bean);

    }

}

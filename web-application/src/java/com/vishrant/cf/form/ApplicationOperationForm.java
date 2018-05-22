package com.vishrant.cf.form;

import com.vishrant.cf.bean.ApplicationBean;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationOperationForm extends ActionForm {

    private ArrayList<ApplicationBean> availableApplications = new ArrayList<ApplicationBean>();

    private String engagement = null;
    private ArrayList<String> engagementValueList = null;
    private ArrayList<String> engagementNameList = null;

    private ArrayList<String> engagementName = new ArrayList<String>();
    private ArrayList<String> applicationName = new ArrayList<String>();
    private ArrayList<String> clientName = new ArrayList<String>();

    public ArrayList<String> getEngagementName() {
        return engagementName;
    }

    public void setEngagementName(int index, ArrayList<String> engagementName) {
        this.engagementName = engagementName;
    }

    public ArrayList<String> getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(int index, ArrayList<String> applicationName) {
        this.applicationName = applicationName;
    }

    public ArrayList<String> getClientName() {
        return clientName;
    }

    public void setClientName(int index, ArrayList<String> clientName) {
        this.clientName = clientName;
    }

    public String getEngagement() {
        return engagement;
    }

    public void setEngagement(String engagement) {
        this.engagement = engagement;
    }

    public ArrayList<String> getEngagementValueList() {
        return engagementValueList;
    }

    public void setEngagementValueList(ArrayList<String> engagementValueList) {
        this.engagementValueList = engagementValueList;
    }

    public ArrayList<String> getEngagementNameList() {
        return engagementNameList;
    }

    public void setEngagementNameList(ArrayList<String> engagementNameList) {
        this.engagementNameList = engagementNameList;
    }

    public ArrayList<ApplicationBean> getAvailableApplications() {
        return availableApplications;
    }

    public void setAvailableApplications(ArrayList<ApplicationBean> availableApplications) {
        this.availableApplications = availableApplications;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        availableApplications.clear();
    }
}
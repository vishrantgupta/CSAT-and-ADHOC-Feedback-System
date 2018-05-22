package com.vishrant.cf.form;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class CaptureFeedbackForm extends ActionForm {

    private String selectedAppName;
    private ArrayList<String> appNameList;
    private ArrayList<String> appValueList;

    public ArrayList<String> getAppValueList() {
        return appValueList;
    }

    public void setAppValueList(ArrayList<String> appValueList) {
        this.appValueList = appValueList;
    }
    private String clientName;

    private String incidentId;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSelectedAppName() {
        return selectedAppName;
    }

    public void setSelectedAppName(String selectedAppName) {
        this.selectedAppName = selectedAppName;
    }

    public ArrayList<String> getAppNameList() {
        return appNameList;
    }

    public void setAppNameList(ArrayList<String> appNameList) {
        this.appNameList = appNameList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    private String engagement = null;
    private ArrayList<String> engagementValueList = null;
    private ArrayList<String> engagementNameList = null;

    private String feedbackTypeRadio = null;

    public String getFeedbackTypeRadio() {
        return feedbackTypeRadio;
    }

    public void setFeedbackTypeRadio(String feedbackTypeRadio) {
        this.feedbackTypeRadio = feedbackTypeRadio;
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
}
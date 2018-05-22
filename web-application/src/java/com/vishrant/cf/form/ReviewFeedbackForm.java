package com.vishrant.cf.form;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ReviewFeedbackForm extends ActionForm {

    private String selectedAppName;
    private ArrayList<String> appNameList;
    private ArrayList<String> appValueList;

    private String clientName;

    private String selectedIncidentId;
    private ArrayList<String> selectedIncidentIdList;

    private String engagement = null;
    private ArrayList<String> engagementValueList = null;
    private ArrayList<String> engagementNameList = null;

    private String feedbackTypeRadio = null;

    private String description = null;
    private ArrayList<String> descriptionList = null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getDescriptionList() {
        return descriptionList;
    }

    public void setDescriptionList(ArrayList<String> descriptionList) {
        this.descriptionList = descriptionList;
    }

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

    public ArrayList<String> getAppValueList() {
        return appValueList;
    }

    public void setAppValueList(ArrayList<String> appValueList) {
        this.appValueList = appValueList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSelectedIncidentId() {
        return selectedIncidentId;
    }

    public void setSelectedIncidentId(String selectedIncidentId) {
        this.selectedIncidentId = selectedIncidentId;
    }

    public ArrayList<String> getSelectedIncidentIdList() {
        return selectedIncidentIdList;
    }

    public void setSelectedIncidentIdList(ArrayList<String> selectedIncidentIdList) {
        this.selectedIncidentIdList = selectedIncidentIdList;
    }

}

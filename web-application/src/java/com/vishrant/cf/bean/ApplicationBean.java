package com.vishrant.cf.bean;

import java.util.List;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationBean implements Comparable<ApplicationBean> {

    private Integer appId;
    private String appName;
    // private String clientName;
    private Integer engagementId;
    private String engagementName;
    private Integer createdBy;

    private List<String> clientName;

    public List<String> getClientName() {
        return clientName;
    }

    public void setClientName(List<String> clientName) {
        this.clientName = clientName;
    }
    
    private List<String> clientId;

    public List<String> getClientId() {
        return clientId;
    }

    public void setClientId(List<String> clientId) {
        this.clientId = clientId;
    }
    
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Integer engagementId) {
        this.engagementId = engagementId;
    }

    public String getEngagementName() {
        return engagementName;
    }

    public void setEngagementName(String engagementName) {
        this.engagementName = engagementName;
    }

    @Override
    public int compareTo(ApplicationBean obj) {
        if (obj instanceof ApplicationBean) {
            ApplicationBean bean = (ApplicationBean) obj;

            // bitwise OR
            return (bean.getAppName().toUpperCase().compareTo(this.getAppName().toUpperCase())); // | bean.getEngagementName().toUpperCase().compareTo(this.getEngagementName().toUpperCase()));
        }
        return -1;
    }

    public String getAllClientName() {
        List<String> list = this.getClientName();
        if(list != null && list.size() > 0) {
            String clientName = "";
            for (String string : list) {
                clientName += string;
            }
            return clientName;
        } else {
            return "N/A";
        }
    }
    
}

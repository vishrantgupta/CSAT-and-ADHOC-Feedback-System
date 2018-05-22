package com.vishrant.cf.bean;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class EngagementBean implements Comparable<EngagementBean> {

    private Integer engagementId;
    private String engagementName;
    private Character actInd;
    private String description;

    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Character getActInd() {
        return actInd;
    }

    public void setActInd(Character actInd) {
        this.actInd = actInd;
    }

    @Override
    public int compareTo(EngagementBean o) {
        if (o instanceof EngagementBean) {
            EngagementBean bean = (EngagementBean) o;
            return bean.getEngagementName().toUpperCase().compareTo(this.engagementName.toUpperCase());
        }
        return -1;
    }
}
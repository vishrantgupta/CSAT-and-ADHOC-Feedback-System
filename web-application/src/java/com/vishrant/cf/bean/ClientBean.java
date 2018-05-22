/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.bean;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ClientBean implements Comparable<ClientBean> {

    private String firstName = null;
    private String lastName = null;
    private Integer engagementId = null;

    private Integer clientId = null;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getEngagementId() {
        return engagementId;
    }

    public void setEngagementId(Integer engagementId) {
        this.engagementId = engagementId;
    }

    @Override
    public int compareTo(ClientBean o) {
        return o.getFirstName().toUpperCase().compareTo(this.firstName.toUpperCase()) | o.getLastName().toUpperCase().compareTo(this.lastName.toUpperCase());
    }
}
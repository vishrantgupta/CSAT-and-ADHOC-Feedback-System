/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.form;

import com.vishrant.cf.bean.EngagementBean;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author VG00124233
 */
public class EngagementForm extends ActionForm {

    private String engagementName;
    private String engagementDesc;

    private ArrayList<EngagementBean> availableEngagements = new ArrayList<EngagementBean>();

    public String getEngagementName() {
        return engagementName;
    }

    public void setEngagementName(String engagementName) {
        this.engagementName = engagementName;
    }

    public String getEngagementDesc() {
        return engagementDesc;
    }

    public void setEngagementDesc(String engagementDesc) {
        this.engagementDesc = engagementDesc;
    }

    public ArrayList<EngagementBean> getAvailableEngagements() {
        return availableEngagements;
    }

    public void setAvailableEngagements(ArrayList<EngagementBean> availableEngagements) {
        this.availableEngagements = availableEngagements;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        availableEngagements.clear();
    }
    
}

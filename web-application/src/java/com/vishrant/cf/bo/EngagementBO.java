/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.bo;

import com.vishrant.cf.bean.ClientBean;
import com.vishrant.cf.bean.EngagementBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.services.ClientService;
import com.vishrant.cf.services.EngagementService;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Vicky
 */
public class EngagementBO {

    final Logger log = Logger.getLogger(EngagementBO.class);

    public String saveEngagement(String engagementName, String engagementDesc, String createdBy) {

        log.debug("in saveEngagementk");

        EngagementService service = new EngagementService();

        EngagementBean bean = new EngagementBean();

        bean.setEngagementName(engagementName);
        bean.setDescription(engagementDesc);
        bean.setCreatedBy(createdBy);
        bean.setActInd(AppConstants.ACT_IND);

        log.debug("out saveEngagement");

        return service.saveNewEngagement(bean);
    }

    public String saveClientDetails(String data, String createdBy) {
       
        log.debug("in saveClientDetails");

        ClientService clientService = new ClientService();
        String[] clientDetails = data.trim().split("#");
        
        ArrayList<ClientBean> list = new ArrayList<ClientBean>();
        
        for (String client : clientDetails) {
            ClientBean clientBean = new ClientBean();
            
            String[] clientDetail = client.split("\\|");
            clientBean.setEngagementId(Integer.parseInt(clientDetail[0]));
            clientBean.setFirstName(clientDetail[1]);
            clientBean.setLastName(clientDetail[2]);
            
            list.add(clientBean);
        }
        
        log.debug("out saveClientDetails");

        if(list.size() > 0) {
            return clientService.saveClientDetails(list, createdBy);
        } else {
            return "false";
        }
        
    }

}

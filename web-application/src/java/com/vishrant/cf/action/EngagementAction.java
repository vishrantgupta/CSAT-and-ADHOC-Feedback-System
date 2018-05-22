/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.action;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.bean.ClientBean;
import com.vishrant.cf.bean.EngagementBean;
import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.bo.EngagementBO;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.exception.InvalidUserExpetion;
import com.vishrant.cf.form.EngagementForm;
import com.vishrant.cf.services.ClientService;
import com.vishrant.cf.services.EngagementService;
import com.vishrant.cf.services.Service;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author VG00124233
 */
public class EngagementAction extends DispatchAction {

    final Logger log = Logger.getLogger(EngagementAction.class);

    public ActionForward getDefaultEngagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("in getDefaultEngagement");

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        try {
            if (ub != null && ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS)) {

                EngagementForm feedbackForm = (EngagementForm) form;

                EngagementService service = new EngagementService();
                feedbackForm.setAvailableEngagements(service.getAvailableEngagements());

                return mapping.findForward("engagement");
            } else {
                throw new InvalidUserExpetion("Invalid user " + ub.getUserName() + " tried to access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapping.findForward("error");
    }

    public ActionForward saveNewEngagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");

                String engagementName = (String) request.getParameter("engagementName");
                String engagementDesc = (String) request.getParameter("engagementDesc");

                log.debug("engagementName " + engagementName);
                log.debug("engagementDesc " + engagementDesc);

                if (engagementName != null && engagementDesc != null && engagementName.length() > 0 && engagementDesc.length() > 0) {

                    EngagementBO engagementBO = new EngagementBO();
                    String status = engagementBO.saveEngagement(engagementName, engagementDesc, ub.getUserId());

                    if ("FALSE".equalsIgnoreCase(status)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else if (!"TRUE".equalsIgnoreCase(status)) {
                        out.write("<userMsg>" + status + "</userMsg>");
                        response.setStatus(HttpServletResponse.SC_OK);
                    }

                    out.flush();
                    out.close();

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Exception occured " + e);
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            new InvalidUserExpetion("Invalid user " + ub.getUserName() + " tried to access");
            return mapping.findForward("invalidUser");
        }
    }

    public ActionForward getAvailableEngagements(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && (ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS) || ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS) || ub.isRoleValidForUser(AppConstants.USER_OPERATIONS))) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");

                EngagementService engagementService = new EngagementService();
                ArrayList<EngagementBean> engagementList = engagementService.getAvailableEngagements();

                if (engagementList != null && engagementList.size() > 0) {
                    out.write("<engagementDetails>");
                    for (EngagementBean engagementBean : engagementList) {
                        System.err.println("engagementBean " + engagementBean.getEngagementName());
                        out.write("<engagement name='" + engagementBean.getEngagementName() + "' id='" + engagementBean.getEngagementId() + "' />");
                    }
                    out.write("</engagementDetails>");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Exception occured " + e);
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            new InvalidUserExpetion("Invalid user " + ub.getUserName() + " tried to access");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return mapping.findForward("invalidUser");
        }

    }

    public ActionForward saveClientDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");

                String data = request.getParameter("data");

                if (data != null && data.length() > 0) {

                    EngagementBO engagementBO = new EngagementBO();
                    String status = engagementBO.saveClientDetails(data, ub.getUserId());

                    System.err.println(status);
                    
                    if ("FALSE".equalsIgnoreCase(status)) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                        out.write("<userMsg>" + status + "</userMsg>");
                    }

                    out.flush();
                    out.close();
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Exception occured " + e);
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            new InvalidUserExpetion("Invalid user " + ub.getUserName() + " tried to access");
            return mapping.findForward("invalidUser");
        }
    }
    
    public ActionForward getClientListForEngagement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                Integer engagementId = Integer.parseInt(request.getParameter("engagementId"));

                ClientService clientService = new ClientService();
                ArrayList<ClientBean> clientList = clientService.getClientListForEngagement(engagementId);

                if (clientList != null && clientList.size() > 0) {
                    out.write("<start>");
                    for (ClientBean applicationBean : clientList) {
                        out.write("<clientDetails>"
                                    + "<clientId>" + applicationBean.getClientId() + "</clientId>"
                                    + "<firstName>" + StringEscapeUtils.escapeHtml(applicationBean.getFirstName()) + "</firstName>"
                                    + "<lastName>" + StringEscapeUtils.escapeHtml(applicationBean.getLastName()) + "</lastName>"
                                + "</clientDetails>"
                        );
                    }
                    out.write("</start>");
                } else {
                    out.write("<noDataFound>No client found.</noDataFound>");
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                e.printStackTrace();
            }
            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }

    }
    
}
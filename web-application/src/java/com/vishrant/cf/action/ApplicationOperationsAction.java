package com.vishrant.cf.action;

import com.vishrant.cf.bean.ApplicationBean;
import com.vishrant.cf.bean.EngagementBean;
import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.bo.ApplicationOperationsBO;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.exception.InvalidUserExpetion;
import com.vishrant.cf.form.ApplicationOperationForm;
import com.vishrant.cf.services.ApplicationService;
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
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ApplicationOperationsAction extends DispatchAction {

    final Logger log = Logger.getLogger(ApplicationOperationsAction.class);

    public ActionForward getDefaultApplication(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("in getDefaultEngagement");

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            ApplicationOperationForm feedbackForm = (ApplicationOperationForm) form;

            String defaultEngagementName = "Select Enagagement";
            ArrayList<String> engagementNameList = new ArrayList<>();
            engagementNameList.add(0, defaultEngagementName);
            ArrayList<String> engagementValueList = new ArrayList<>();
            engagementValueList.add(0, defaultEngagementName);

            EngagementService engagementService = new EngagementService();
            ArrayList<EngagementBean> engagementList = engagementService.getAvailableEngagements();

            for (EngagementBean engagement : engagementList) {
                engagementNameList.add(engagement.getEngagementName());
                engagementValueList.add(String.valueOf(engagement.getEngagementId()));
            }

            // ApplicationService applicationService = new ApplicationService();
            // feedbackForm.setAvailableApplications(applicationService.getAvailableApplications());

            feedbackForm.setEngagementValueList(engagementValueList);
            feedbackForm.setEngagementNameList(engagementNameList);

            return mapping.findForward("applicationDefaultPage");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidUserExpetion("Invalid user tried to access applicatoin " + ub);
        }

    }

    public ActionForward saveApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("in getDefaultEngagement");

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            try (PrintWriter out = response.getWriter()) {

                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String saveData = request.getParameter("saveData");

                if (saveData != null && saveData.trim().length() > 0) {
                    ApplicationOperationsBO operationsBO = new ApplicationOperationsBO();
                    String status = operationsBO.saveNewApplications(saveData, ub.getUserId());

                    if (status != null) {
                        out.write("<status>" + status + "</status>");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }

                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                out.flush();

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            return mapping.findForward(null);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidUserExpetion("Invalid user tried to access applicatoin " + ub);
        }

    }

    public ActionForward getApplicationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("in getApplicationDetails");

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            try (PrintWriter out = response.getWriter()) {

                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");

                Integer appId = Integer.parseInt(request.getParameter("appId"));

                if (appId != null) {
                    ApplicationOperationsBO operationsBO = new ApplicationOperationsBO();
                    String status = operationsBO.getApplicationDetails(appId);

                    if (status != null) {
                        out.write("<start>");
                        out.write(status);
                        out.write("</start>");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            return mapping.findForward(null);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidUserExpetion("Invalid user tried to access getApplicationDetails " + ub);
        }

    }

    public ActionForward updateApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.debug("in getApplicationDetails");

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            try (PrintWriter out = response.getWriter()) {

                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");

                Integer appId = Integer.parseInt(request.getParameter("appId").trim());
                String appName = request.getParameter("appName").trim();
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                Integer engagementId = Integer.parseInt(request.getParameter("engagementId").trim());

                if (appId != null && appName != null && firstName != null && lastName != null) {
                    ApplicationOperationsBO operationsBO = new ApplicationOperationsBO();
                    String status = operationsBO.updateApplication(appId, appName, firstName, lastName, engagementId);

                    if (status != null) {
                        out.write("<start>");
                        out.write("<status>" + status + "</status>");
                        out.write("</start>");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }

                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                out.flush();

            } catch (Exception e) {
                e.printStackTrace();
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            return mapping.findForward(null);

        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new InvalidUserExpetion("Invalid user tried to access getApplicationDetails " + ub);
        }

    }
    
    public ActionForward getAllAppName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String engagementId = request.getParameter("engagementId");

                System.err.println("engagementId " + engagementId);
                
                Service service = new Service();
                ArrayList<ApplicationBean> appList = service.getAllAppNamesForEngagementId(engagementId);

                if (appList != null) {
                    out.write("<start>");
                    for (ApplicationBean applicationBean : appList) {
                        out.write("<app>"
                                    + "<appId>" + applicationBean.getAppId() + "</appId>"
                                    + "<appName>" + StringEscapeUtils.escapeHtml(applicationBean.getAppName()) + "</appName>"
                                    + "<clientName>" + StringEscapeUtils.escapeHtml(applicationBean.getAllClientName()) + "</clientName>"
                                + "</app>"
                        );
                    }
                    out.write("</start>");
                } else {
                    out.write("<noDataFound>No application found.</noDataFound>");
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

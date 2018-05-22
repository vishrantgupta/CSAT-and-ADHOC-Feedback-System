package com.vishrant.cf.action;

import com.vishrant.cf.bean.Feedback;
import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.form.ReviewFeedbackForm;
import com.vishrant.cf.services.Service;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class ReviewFeedback extends DispatchAction {

    public ActionForward getDefaultReports(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {

            String userName = ub.getUserName();

            ReviewFeedbackForm reviewFeedbackForm = (ReviewFeedbackForm) form;

            String defaultAppName = "Select Application";
            ArrayList<String> appNameList = new ArrayList<>();
            ArrayList<String> appValueList = new ArrayList<>();

            appNameList.add(defaultAppName);
            appValueList.add(defaultAppName);

            Service service = new Service();

            reviewFeedbackForm.setAppNameList(appNameList);
            reviewFeedbackForm.setAppValueList(appValueList);

            reviewFeedbackForm.setSelectedAppName("Select Application");

            reviewFeedbackForm.setClientName(null);

            reviewFeedbackForm.setSelectedIncidentId("");
            reviewFeedbackForm.setSelectedIncidentIdList(new ArrayList<String>());

            String defaultEngagementName = "Select Enagagement";
            ArrayList<String> engagementNameList = new ArrayList<>();
            engagementNameList.add(0, defaultEngagementName);
            ArrayList<String> engagementValueList = new ArrayList<>();
            engagementValueList.add(0, defaultEngagementName);

            SortedSet<Entry<String,String>> engagementMap = service.getEnagegementList(userName);

            if (engagementMap != null && engagementMap.size() > 0) {

                for (Entry<String, String> entry : engagementMap) {
                    engagementValueList.add((String) entry.getKey());
                    engagementNameList.add((String) entry.getValue());
                }
                
                reviewFeedbackForm.setEngagement(defaultEngagementName);

                reviewFeedbackForm.setEngagementNameList(engagementNameList);
                reviewFeedbackForm.setEngagementValueList(engagementValueList);

                reviewFeedbackForm.setFeedbackTypeRadio(null);

                String defaultDesc = "Select description";
                reviewFeedbackForm.setDescription(defaultDesc);

                ArrayList<String> descList = new ArrayList<String>();
                reviewFeedbackForm.setDescriptionList(descList);

                return mapping.findForward("reviewFeedback");
            } else {
                return mapping.findForward("noMapping");
            }

        } else {
            return mapping.findForward("invalidUser");
        }
        
    }

    public ActionForward getAppName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String engagementId = request.getParameter("engagementId");
                String feedbackType = request.getParameter("feedbackType");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");

                Service service = new Service();
                SortedSet<Entry<String, String>> appList = service.getAppNameOnTypeEngagementIdNDate(feedbackType, engagementId, fromDate, toDate, ub.getUserId());

                if (appList != null) {
                    out.write("<start>");
                    for (Entry<String, String> entry : appList) {
                        out.write("<app>"
                                + "<appId>" + entry.getKey() + "</appId>"
                                + "<appName>" + entry.getValue() + "</appName>"
                                + "</app>"
                        );
                    }
                    out.write("</start>");
                } else {
                    out.write("<noDataFound>No feedback captured for selected date range.</noDataFound>");
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }
    }

    public ActionForward getClientName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String appId = request.getParameter("appId");

                // CaptureFeedbackBO captureFeedback = new CaptureFeedbackBO();
                Service service = new Service();
                String clientName = service.getClientNameForSelectedAppId(appId);

                if (clientName != null) {
                    out.write("<start>");
                    out.write("<clientName>" + clientName + "</clientName>");
                    out.write("</start>");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + " getClientName " + e);
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }
    }

    public ActionForward getIncidentList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String feedbackType = request.getParameter("feedbackType");
                String engagementId = request.getParameter("engagementId");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");

                String appId = (String) request.getParameter("appId");
                appId = appId.trim().isEmpty() ? null : appId;

                Service service = new Service();

                HashMap<String, String> incidentList = service.getIncidentIdListOnEngagementIdAppId(feedbackType, engagementId, appId, fromDate, toDate);

                out.write("<start>");
                if (incidentList != null) {

                    out.write("<incident>"
                            + "<incidentId>Select description</incidentId>"
                            + "<descTxt>Select description</descTxt>"
                            + "</incident>");

                    for (Map.Entry<String, String> entry : incidentList.entrySet()) {
                        String incidentId = entry.getKey();
                        String descTxt = entry.getValue();
                        out.write("<incident>"
                                + "<incidentId>" + incidentId + "</incidentId>"
                                + "<descTxt>" + descTxt + "</descTxt>"
                                + "</incident>");
                    }

                } else {
                    out.write("<noDataFound>No feedback captured for selected date range.</noDataFound>");
                }
                out.write("</start>");

                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }
    }

    public ActionForward getReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String appId = (String) request.getParameter("appId");
                appId = appId.trim().isEmpty() ? null : appId;

                String incidentId = request.getParameter("incidentId");

                Service service = new Service();
                ArrayList<Feedback> feedbackList = service.getReport(appId, incidentId);

                out.write("<start>");
                if (feedbackList != null) {

                    for (Feedback feedback : feedbackList) {

                        out.write("<feedback>"
                                + "<questionId>" + feedback.getQuestionId() + "</questionId>"
                                + "<type>" + StringEscapeUtils.escapeHtml(feedback.getQuestionType()) + "</type>"
                                // + "<rating>" + feedback.getRating() + "</rating>"
                                + "<responseTxt>" + StringEscapeUtils.escapeHtml((feedback.getResponse() != null ? (feedback.getResponse()) : (feedback.getRating() + " / " + feedback.getRatingMaxValue()))) + "</responseTxt>"
                                + "<category>" + StringEscapeUtils.escapeHtml(feedback.getCategory()) + "</category>"
                                + "<questionTxt>" + StringEscapeUtils.escapeHtml(feedback.getQuestionTxt()) + "</questionTxt>"
                                + "</feedback>");

                    }

                } else {
                    out.write("<noDataFound>No data found.</noDataFound>");
                }
                out.write("</start>");

                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }
    }
}

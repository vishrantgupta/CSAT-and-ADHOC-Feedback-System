package com.vishrant.cf.action;

import com.vishrant.cf.bean.Feedback;
import com.vishrant.cf.bean.Question;
import com.vishrant.cf.bean.QuestionDetail;
import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.bo.CaptureFeedbackBO;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.form.CaptureFeedbackForm;
import com.vishrant.cf.services.Service;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
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
public class CaptureFeedback extends DispatchAction {

    final Logger log = Logger.getLogger(CaptureFeedback.class);

    public ActionForward getDefaultFeedback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {

            CaptureFeedbackForm feedbackForm = (CaptureFeedbackForm) form;

            String userName = ub.getUserName();

            String defaultEngagementName = "Select Enagagement";
            ArrayList<String> engagementNameList = new ArrayList<>();
            engagementNameList.add(0, defaultEngagementName);
            ArrayList<String> engagementValueList = new ArrayList<>();
            engagementValueList.add(0, defaultEngagementName);

            Service service = new Service();
            SortedSet<Entry<String, String>> engagementMap = service.getEnagegementList(userName);

            if (engagementMap != null && engagementMap.size() > 0) {
                
                for (Entry<String, String> entry : engagementMap) {
                    engagementValueList.add((String) entry.getKey());
                    engagementNameList.add((String) entry.getValue());
                }
                
                feedbackForm.setEngagement(defaultEngagementName);

                feedbackForm.setEngagementNameList(engagementNameList);
                feedbackForm.setEngagementValueList(engagementValueList);

                String defaultAppName = "Select Application";
                ArrayList<String> appNameList = new ArrayList<>();
                ArrayList<String> appValueList = new ArrayList<>();

                appNameList.add(defaultAppName);
                appValueList.add(defaultAppName);

                feedbackForm.setFeedbackTypeRadio(null);

                feedbackForm.setAppNameList(appNameList);
                feedbackForm.setAppValueList(appValueList);
                feedbackForm.setSelectedAppName(defaultAppName);

                feedbackForm.setIncidentId(null);

                return mapping.findForward("captureFeedback");
            } else {
                return mapping.findForward("noMapping");
            }

        }

        return mapping.findForward("invalidUser");
    }

    public ActionForward getAppName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String engagementId = request.getParameter("engagementId");

                Service service = new Service();
                SortedSet<Map.Entry<String, String>> appList = service.getAppNameForEngagementId(engagementId, ub.getUserId());

                if (appList != null) {
                    out.write("<start>");
                    for (Map.Entry<String, String> entry : appList) {
                        out.write("<app>"
                                + "<appId>" + StringEscapeUtils.escapeHtml(entry.getKey()) + "</appId>"
                                + "<appName>" + StringEscapeUtils.escapeHtml(entry.getValue()) + "</appName>"
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
            }
            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }

    }

    public ActionForward getClientName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String appId = request.getParameter("appId");

                Service service = new Service();
                String clientName = service.getClientNameForSelectedAppId(appId);

                if (clientName != null) {
                    out.write("<start>");
                    out.write("<clientName>" + clientName + "</clientName>");
                    out.write("</start>");
                } else {
                    out.write("<noDataFound>No data found</noDataFound>");
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }

    }

    public ActionForward getFeedbackFormQuestions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                CaptureFeedbackBO captureFeedback = new CaptureFeedbackBO();
                LinkedHashMap<Question, ArrayList<QuestionDetail>> questionList = captureFeedback.fetchQuestions();

                if (questionList != null) {
                    out.write("<start>");

                    for (Map.Entry<Question, ArrayList<QuestionDetail>> entry : questionList.entrySet()) {
                        Question question = (Question) entry.getKey();
                        out.write("<category name='" + question.getCategory() + "'>");

                        for (QuestionDetail questionDetail : entry.getValue()) {
                            out.write("<question id='" + questionDetail.getQuestionId() + "' type='" + questionDetail.getType() + "' ratingMaxValue='" + questionDetail.getRatingMaxValue() + "' count='" + questionDetail.getCount() + "' >" + StringEscapeUtils.escapeHtml(questionDetail.getQuestionTxt()) + "</question>");
                        }

                        out.write("</category>");
                    }

                    out.write("</start>");
                } else {
                    out.write("<start><nodatafound>No questions found</nodatafound></start>");
                }

                out.flush();
                out.close();

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }

            return mapping.findForward(null);

        } else {
            return mapping.findForward("invalidUser");
        }

    }

    public ActionForward submitFeedback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null && ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String engagementId = (String) request.getParameter("engagementId");

                String appId = (String) request.getParameter("appId");
                appId = appId.trim().isEmpty() ? null : appId;

                String descriptionTxt = (String) request.getParameter("descriptionTxt");
                String incidentType = (String) request.getParameter("incidentType");
                String feedbackData = ((String) request.getParameter("feedbackData")).trim();

                ArrayList<Feedback> feedbackList = new ArrayList<>();
                if (engagementId != null && descriptionTxt != null && incidentType != null && feedbackData != null && feedbackData.length() > 0) {
                    String[] feedbackArray = feedbackData.split("#");
                    for (String feedback : feedbackArray) {
                        Feedback f = new Feedback();
                        String[] data = feedback.split(":");
                        f.setQuestionId(Integer.parseInt((data[0].trim())));
                        f.setQuestionType((data[1].trim()));
                        f.setResponse((data[2].trim()));

                        feedbackList.add(f);
                    }

                    String userId = ub.getUserId();

                    CaptureFeedbackBO captureFeedback = new CaptureFeedbackBO();
                    String status = captureFeedback.saveFeedback(engagementId, appId, descriptionTxt, incidentType, feedbackList, userId);

                    out.write("<start>");
                    out.write("<status>" + status + "</status>");
                    out.write("</start>");

                    out.flush();
                    out.close();

                }

            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }

            return mapping.findForward(null);
        } else {
            return mapping.findForward("invalidUser");
        }
        
    }
    
}
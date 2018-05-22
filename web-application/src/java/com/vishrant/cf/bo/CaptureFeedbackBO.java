package com.vishrant.cf.bo;

import com.vishrant.cf.bean.Feedback;
import com.vishrant.cf.bean.Question;
import com.vishrant.cf.bean.QuestionDetail;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class CaptureFeedbackBO {

    final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CaptureFeedbackBO.class);
    
    public LinkedHashMap<Question, ArrayList<QuestionDetail>> fetchQuestions() {

        LinkedHashMap<Question, ArrayList<QuestionDetail>> questions = null;

        try (Connection connection = DBHelper.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(OracleSQL.FETCH_QUESTION_SQL);

            questions = new LinkedHashMap<>();
            String previousCategory = null;

            while (rs.next()) {

                String category = rs.getString("category");
                Question question = new Question();

                question.setCategory(category);
                question.setCategoryOrder(rs.getInt("category_order"));

                ArrayList<QuestionDetail> questionDetailsList = null;
                if (previousCategory != null && previousCategory.equalsIgnoreCase(category)) {
                    if (questions.size() > 0 && questions.containsKey(question)) {
                        questionDetailsList = questions.get(question);
                    } else {
                        questionDetailsList = new ArrayList<>();
                    }
                } else {
                    questionDetailsList = new ArrayList<>();
                }

                QuestionDetail details = new QuestionDetail();

                details.setQuestionTxt(rs.getString("question_txt"));
                details.setQuestionId(rs.getInt("question_id"));
                details.setDisplayOrder(rs.getInt("DISPLAY_ORDER"));
                details.setType(rs.getString("type"));
                details.setRatingMaxValue(rs.getInt("rating_max_value"));
                details.setCount(rs.getString("count"));

                questionDetailsList.add(details);
                questions.put(question, questionDetailsList);

                previousCategory = category;

            }

            return questions != null && questions.size() > 0
                    ? questions : null;
        } catch (Exception e) {
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        }

        return null;
    }

    public String saveFeedback(String engagementId, String appId, String descriptionTxt, String incidentType, ArrayList<Feedback> feedbackList, String userId) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {

            connection = DBHelper.getConnection();
                     
            String incidentId = "select INCIDENT_ID_SEQ.nextval from dual";
            statement = connection.prepareStatement(incidentId);
            rs = statement.executeQuery();

            String incident = "";
            while (rs.next()) {
                incident = rs.getString(1);
            }

            String incidentColumnLnt = "SELECT data_length FROM user_tab_columns WHERE table_name = 'FEEDBACK' AND column_name  = 'INCIDENT_ID'";
            statement = connection.prepareStatement(incidentColumnLnt);
            ResultSet lengthRs = statement.executeQuery();

            int dataLength = 0;
            while (lengthRs.next()) {
                dataLength = lengthRs.getInt(1);
            }

            if (incidentType.equalsIgnoreCase("event")) {
                incidentId = "ECF";
            } else if (incidentType.equalsIgnoreCase("incident")) {
                incidentId = "ICF";
            }

            incidentId = StringUtils.rightPad(incidentId, (dataLength - incident.length()), "0") + incident;

            String sql = "INSERT INTO feedback (FEEDBACK_ID, INCIDENT_ID, engagement_id, app_id, brief_desc, RESPONSE_TXT, RESPONSE_RATING, user_id, QUESTION_ID, feedback_type) VALUES(FEEDBACK_ID_SEQ.NEXTVAL,? , ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            for (Feedback feedback : feedbackList) {

                statement.setString(1, incidentId);
                statement.setInt(2, Integer.parseInt(engagementId));
                statement.setString(3, appId);
                statement.setString(4, descriptionTxt);
                statement.setString(5, feedback.getQuestionType().equalsIgnoreCase("OPEN_TEXT") ? feedback.getResponse() : null);
                statement.setString(6, feedback.getQuestionType().equalsIgnoreCase("RATING") ? feedback.getResponse() : null);
                statement.setInt(7, Integer.parseInt(userId));
                statement.setInt(8, feedback.getQuestionId());
                statement.setString(9, incidentType);

                statement.addBatch();
            }
            statement.executeBatch();
            statement.close();
            connection.commit();

            return "success";
        } catch (BatchUpdateException e) {
            
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            log.error(AppConstants.LOGGING_EXCEPTION + e);
            return "Error occured, please check if you have submittied feedback earlier.";
        } catch (Exception e) {
            
            if(connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            
            log.error(AppConstants.LOGGING_EXCEPTION + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    log.error(AppConstants.LOGGING_EXCEPTION + ex);
                }
            }
        }
        
        return "fail";
    }
    
}
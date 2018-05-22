package com.vishrant.cf.bean;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class QuestionDetail {

    private String questionTxt = null;
    private String type = null;
    private Integer displayOrder = null;
    private Integer ratingMaxValue = null;
    private Integer questionId = null;
    private String count = null;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getRatingMaxValue() {
        return ratingMaxValue;
    }

    public void setRatingMaxValue(Integer ratingMaxValue) {
        if (ratingMaxValue != null) {
            this.ratingMaxValue = ratingMaxValue;
        } else {
            this.ratingMaxValue = 0;
        }
    }

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
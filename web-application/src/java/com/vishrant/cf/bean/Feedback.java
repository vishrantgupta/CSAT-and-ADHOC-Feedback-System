package com.vishrant.cf.bean;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class Feedback {

    private Integer questionId = null;
    private String questionType = null;
    private String response = null;
    private String questionTxt = null;
    private String ratingMaxValue = null;

    public String getRatingMaxValue() {
        return ratingMaxValue;
    }

    public void setRatingMaxValue(String ratingMaxValue) {
        this.ratingMaxValue = ratingMaxValue;
    }

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    private String rating = null;
    private String category = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {

        this.rating = rating;

        /*if (rating != null) {
         this.rating = rating;
         } else {
         this.rating = "N/A";
         }*/
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {

        this.response = response;

        /*if (response != null) {
         this.response = response;
         } else {
         this.response = "N/A";
         }*/
    }
}

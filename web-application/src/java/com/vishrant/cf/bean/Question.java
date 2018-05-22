package com.vishrant.cf.bean;

import java.util.Objects;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class Question { // implements Comparable<Question>

    private String category = null;
    private Integer categoryOrder = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Question) {
            Question question = (Question) obj;
            if (question.getCategory().equalsIgnoreCase(this.category)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.category);
        return hash;
    }

//    private ArrayList<QuestionDetail> questionList = null;
//
//    public ArrayList<QuestionDetail> getQuestionList() {
//        return questionList;
//    }
//
//    public void setQuestionList(ArrayList<QuestionDetail> questionList) {
//        this.questionList = questionList;
//    }
    public Integer getCategoryOrder() {
        return categoryOrder;
    }

    public void setCategoryOrder(Integer categoryOrder) {
        this.categoryOrder = categoryOrder;
    }

    /*@Override
     public int compareTo(Question o) {

     if (o instanceof Question) {
     return (((Question) o).getCategory().compareTo(this.category));
     }
     return -1;
     // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }*/
}

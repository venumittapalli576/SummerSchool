package com.developmentapps.summerschool.model;

import java.io.Serializable;

public class Answer implements Serializable {

    protected int questionId;
    protected int correctOption;
    protected int tickedOption;

    public Answer() {

    }

    //get and set methods for the property questionId
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    //get and set methods for the property correctOption
    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    //get and set methods for the property tickedOption
    public int getTickedOption() {
        return tickedOption;
    }

    public void setTickedOption(int tickedOption) {
        this.tickedOption = tickedOption;
    }
}
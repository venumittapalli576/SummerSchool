package com.developmentapps.summerschool.model;

import java.io.Serializable;

public class Question implements Serializable {
    protected int id;
    protected String title;
    protected String description;
    protected String optionOne;
    protected String optionTwo;
    protected String optionThree;
    protected String optionFour;

    public Question() {

    }

    //set and get methods for the property id
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //set and get methods for the property title
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    //get and set methods for the property description
    public void setDescription(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }


    //get and set methods for the property optionOne
    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }


    //get and set methods for the property optionTwo
    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }


    //get and set methods for property optionThree
    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }


    //get and set methods for property optionFour
    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }
}

package com.developmentapps.summerschool.service;


import com.developmentapps.summerschool.model.Answer;
import com.developmentapps.summerschool.model.Question;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * This service helps to get the data from cloud.
 * Author:venu
 * Since:2018
 */
public interface QuestionService {
    /**
     * This method will returns a Question object when invoked.
     * Parameters:None
     * Return:Question when called this method.
     */
    public Question get() throws MalformedURLException, ExecutionException, InterruptedException, JSONException;

    /**
     * This method will returns a Answer object when invoked.
     * Parameters:
     * questionid:This will be used to retrieve the answer of a required question.
     * Return:the Answer
     */
    public Answer getAnswer(int questionid) throws MalformedURLException, ExecutionException, InterruptedException, JSONException;

    /**
     * It returns the network status of the user.
     * Parameters:None
     * Return:returns true if user is having active connection else returns false
     */
    public boolean isConnected();
}


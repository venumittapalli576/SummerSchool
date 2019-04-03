package com.developmentapps.summerschool.service;


import com.developmentapps.summerschool.model.Answer;
import com.developmentapps.summerschool.model.Score;

public class SimpleScoringService implements ScoringService {
    Score currentScore = new Score();


    boolean status;

    @Override
    public boolean evaluate(Answer answer) {
        if (answer == null) {
            status = false;
        } else if (answer.getQuestionId() == 0) {
            status = false;
        } else if (answer.getCorrectOption() >= 1 && answer.getCorrectOption() <= 4 && answer.getTickedOption() >= 1 && answer.getTickedOption() <= 4) {
            if (answer.getTickedOption() == answer.getCorrectOption()) {
                //update score when ticked option is true
                currentScore.setNumberOfPoints(currentScore.getNumberOfPoints() + 1);
                currentScore.setNumberOfAttempts(currentScore.getNumberOfAttempts() + 1);
                status = true;
            } else {
                currentScore.setNumberOfPoints(currentScore.getNumberOfPoints());
                currentScore.setNumberOfAttempts(currentScore.getNumberOfAttempts() + 1);
                status = false;
            }
        }
        return status;
    }

    @Override
    public Score getCurrentScore() {
        //returns number of points and attempts
        return currentScore;
    }
}

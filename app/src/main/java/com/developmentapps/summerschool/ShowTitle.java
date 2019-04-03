package com.developmentapps.summerschool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.developmentapps.summerschool.model.Answer;
import com.developmentapps.summerschool.model.Question;
import com.developmentapps.summerschool.service.GlarimyQuestionService;
import com.developmentapps.summerschool.service.SimpleScoringService;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class ShowTitle extends Activity {


    static int c = 0;
    TextView titleView, questionView, optionsView;

    int textIds[] = new int[]{R.id.optionIdOne, R.id.optionIdTwo, R.id.optionIdThree, R.id.optionIdFour};
    int cardIds[] = new int[]{R.id.cardOne, R.id.cardTwo, R.id.cardThree, R.id.cardFour};
    TextView buttonViews[] = new TextView[textIds.length];
    CardView cardViews[] = new CardView[cardIds.length];

    String optionText[] = new String[4];
    int choosedOption;


    GlarimyQuestionService glarimyQuestionService = new GlarimyQuestionService(this);
    Question question = new Question();
    Answer answer = new Answer();
    SimpleScoringService simpleScoringService = new SimpleScoringService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (c < 8) {
        setContentView(R.layout.activity_show_title);

            titleView = findViewById(R.id.titleId);
            questionView = findViewById(R.id.questionId);
            questionView.setVisibility(View.INVISIBLE);
            optionsView = findViewById(R.id.optionsId);
            optionsView.setVisibility(View.INVISIBLE);



            try {
                question = glarimyQuestionService.get();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (question != null) {
                if (question.getDescription() != null) {
                    titleView.setText(question.getTitle());
                    questionView.setText(question.getDescription());

                    optionText[0] = question.getOptionOne();
                    optionText[1] = question.getOptionTwo();
                    optionText[2] = question.getOptionThree();
                    optionText[3] = question.getOptionFour();

                    for (int i = 0; i < 4; i++) {
                        buttonViews[i] = findViewById(textIds[i]);
                        buttonViews[i].setText(optionText[i]);
                        buttonViews[i].setVisibility(View.INVISIBLE);

                        cardViews[i] = findViewById(cardIds[i]);
                        cardViews[i].setVisibility(View.INVISIBLE);
                    }
                    Handler answerHandler = new Handler();
                    int time = 500;
                    int i1;
                    for (i1 = 0; i1 <= 4; i1++) {
                        final int finalI = i1;
                        answerHandler.postDelayed(new Runnable() {
                            public void run() {
                                if (finalI == 0) {
                                    optionsView.setVisibility(View.VISIBLE);
                                    buttonViews[finalI].setVisibility(View.VISIBLE);
                                    cardViews[finalI].setVisibility(View.VISIBLE);
                                } else if (finalI == 4) {
                                    optionsView.setVisibility(View.INVISIBLE);
                                    questionView.setVisibility(View.VISIBLE);
                                } else {
                                    buttonViews[finalI].setVisibility(View.VISIBLE);
                                    cardViews[finalI].setVisibility(View.VISIBLE);
                                }
                            }
                        }, time = time + 1000);
                    }
                } else {
                    finish();
                    startActivity(getIntent());
                }
            } else {
                Intent showErroMessage = new Intent(this, ShowErrorMessage.class);
                startActivity(showErroMessage);
                finish();
            }
            c = c + 1;
        } else {
            finish();
            Intent i = new Intent(this, ShowCurrentScore.class);
            startActivity(i);
        }
    }


    public void onSubmit(View v) throws InterruptedException, MalformedURLException, JSONException, ExecutionException {
        int choosedButton = v.getId();

        if (glarimyQuestionService.isConnected()) {
            for (int i = 0; i < 4; i++) {
                if (glarimyQuestionService.isConnected() && choosedButton == textIds[i]) {
                    choosedOption = i + 1;
                    buttonViews[i].setBackgroundColor(getResources().getColor(R.color.colorStrawberry));
                    buttonViews[i].setTextColor(getResources().getColor(R.color.colortext));
                    buttonViews[i].setTypeface(buttonViews[i].getTypeface(), Typeface.BOLD);
                } else {
                    Intent networkerror = new Intent(this, ShowErrorMessage.class);
                    buttonViews[i].setEnabled(false);
                    startActivity(networkerror);
                    finish();
                }

            }

            answer = glarimyQuestionService.getAnswer(question.getId());
            answer.setTickedOption(choosedOption);
            simpleScoringService.evaluate(answer);

            if (choosedOption == answer.getCorrectOption()) {
                Intent passIntent = new Intent(this, ShowPassMessage.class);
                startActivity(passIntent);
                finish();
            } else {
                Intent errorIntent = new Intent(this, ShowWrongMessage.class);
                startActivity(errorIntent);
                finish();
            }
        } else {
            Intent networkerror = new Intent(this, ShowErrorMessage.class);
            startActivity(networkerror);
            finish();
        }
    }
}
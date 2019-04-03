package com.developmentapps.summerschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.developmentapps.summerschool.model.Score;
import com.developmentapps.summerschool.service.GlarimyQuestionService;
import com.developmentapps.summerschool.service.SimpleScoringService;


public class ShowCurrentScore extends Activity {


    SimpleScoringService simpleScoringService = new SimpleScoringService();
    GlarimyQuestionService glarimyQuestionService = new GlarimyQuestionService(this);

    TextView scoreView;
    Score score = new Score();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_current_score);

        score = simpleScoringService.getCurrentScore();

        scoreView = findViewById(R.id.currentScoreId);
        scoreView.setText("" + score.getNumberOfPoints());
        /*if (glarimyQuestionService.isConnected()) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent showTitle = new Intent(ShowCurrentScore.this, ShowTitle.class);
                    startActivity(showTitle);
                    finish();
                }
            }, 2000);
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(ShowCurrentScore.this, ShowErrorMessage.class));
                    finish();
                }
            }, 2000);
        }*/
    }
}

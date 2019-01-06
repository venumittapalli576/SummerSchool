package com.developmentapps.summerschool.other;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developmentapps.summerschool.R;



public class Feedbackform extends AppCompatActivity {
    GMailSender sender;
    String string;
    TextView email;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    EditText feedname,Feedback;
    Button button;
    private String TAG = " ";
    private ProgressDialog pDialog;
    RatingBar ratingBar;
    String rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_feedbackform);
        feedname= (EditText) findViewById(R.id.feedname);
        Feedback = (EditText) findViewById(R.id.Feedback);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        button = (Button) findViewById(R.id.button);
        sender = new GMailSender("venumittapalli7@gmail.com", "9505135123");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.

                Builder().permitAll().build();


        StrictMode.setThreadPolicy(policy);


        button.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                if(feedname.getText().toString().equals("") || Feedback.getText().toString().equals("")){
                    if(feedname.getText().toString().equals("")){
                        feedname.requestFocus();
                        feedname.setError("Required");
                    }else if(Feedback.getText().toString().equals("")){
                        Feedback.requestFocus();
                        Feedback.setError("Required");
                    }
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        rating=String.valueOf(ratingBar.getRating());
                        Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                        new MyAsyncClass().execute();

                    } catch (Exception ex) {

                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

                    }
                }



            }

        });


    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Feedbackform.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                string = feedname.getText().toString();
                String feedback=Feedback.getText().toString();
                // Add subject, Body, your mail Id, and receiver mail Id.
                String body = "user name is"+string + " " +"Feedback is:"+feedback+" "+"Rating is:"+rating;
                String Email="venumittapalli7@gmail.com";
                sender.sendMail("FeedBack From User", body, "venumittapalli7@gmail.com", Email);
            } catch (Exception ex) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "Email send", Toast.LENGTH_LONG).show();
        }
    }
}
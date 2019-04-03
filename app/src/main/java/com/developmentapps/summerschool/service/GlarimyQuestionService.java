package com.developmentapps.summerschool.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import com.developmentapps.summerschool.ShowTitle;
import com.developmentapps.summerschool.model.Answer;
import com.developmentapps.summerschool.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GlarimyQuestionService implements QuestionService {
    Context context;
    Question question = new Question();
    Answer answer = new Answer();


    public GlarimyQuestionService(Context context) {
        this.context = context;
    }

    @Override
    public Question get() throws MalformedURLException, ExecutionException, InterruptedException, JSONException {

        if (isConnected()) {
            String stringUrl = "http://www.glarimy.com/q";
            URL uri = new URL(stringUrl);

            //creating object and calling the async task
            final CloudConnection cloudConnection = new CloudConnection((ShowTitle) context);
            cloudConnection.execute(uri);

            JSONObject JsonQuestionObject = new JSONObject(cloudConnection.get());
            if (JsonQuestionObject.getInt("id") != 0 ){
                question.setId(JsonQuestionObject.getInt("id"));
                question.setTitle(JsonQuestionObject.getString("title"));
                question.setDescription(JsonQuestionObject.getString("question"));
                question.setOptionOne(JsonQuestionObject.getString("a"));
                question.setOptionTwo(JsonQuestionObject.getString("b"));
                question.setOptionThree(JsonQuestionObject.getString("c"));
                question.setOptionFour(JsonQuestionObject.getString("d"));
            }else{
                question=null;
            }


        } else {
            //code to be executed when no internet connectivity
            question = null;
        }

        return question;
    }

    @Override
    public Answer getAnswer(final int questionId) throws MalformedURLException, ExecutionException, InterruptedException, JSONException {
        if (isConnected()) {
            String stringUrl = "http://www.glarimy.com/q?id=" + questionId;
            URL uri = new URL(stringUrl);

            final CloudConnection cloudConnection = new CloudConnection((ShowTitle) context);
            cloudConnection.execute(uri);

            JSONObject JsonQuestionObject = new JSONObject(cloudConnection.get());

            switch (JsonQuestionObject.getString("key")) {
                case "a":
                    answer.setCorrectOption(1);
                    break;
                case "b":
                    answer.setCorrectOption(2);
                    break;
                case "c":
                    answer.setCorrectOption(3);
                    break;
                case "d":
                    answer.setCorrectOption(4);
                    break;
            }

            answer.setQuestionId(JsonQuestionObject.getInt("id"));


            if (JsonQuestionObject.getInt("id") < 0)
                answer = null;
            if (JsonQuestionObject.getInt("id") != questionId)
                answer = null;
        } else {
            answer = null;
        }
        return answer;
    }


    /**
     * method to check the device is connected to a network or not
     *
     * @return true if connected to network else false
     */
    public boolean isConnected() {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if ((connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected())
                || (connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * AsyncTask for connecting to the required URL
     * <p>
     * this AsyncTask was called by two methods in this class
     * get() and getAnswer()
     */
    class CloudConnection extends AsyncTask<URL, Void, String> {
        private HttpURLConnection urlConnection;
        private ProgressDialog progress;

        public CloudConnection(ShowTitle context) {
            progress = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(context);
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected String doInBackground(URL... params) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = params[0];
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(10 * 1000);
                urlConnection.setReadTimeout(10 * 1000);

                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null)
                        result.append(line);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);
            progress.dismiss();
        }
    }
}

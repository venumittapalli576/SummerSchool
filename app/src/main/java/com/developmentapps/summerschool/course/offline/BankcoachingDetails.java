package com.developmentapps.summerschool.course.offline;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.course.CustomAdapter;
import com.developmentapps.summerschool.other.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BankcoachingDetails extends AppCompatActivity {

    private String TAG = BankcoachingDetails.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    CustomAdapter adapter;


    private static String url = "http://192.168.43.240/summerportal/viewdetails/bankcouchingDetails.php";

    ArrayList<HashMap<String, String>> BankcoachingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankcoaching_details);

        BankcoachingList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetDetails().execute();
    }

    protected class GetDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BankcoachingDetails.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("user_id");
                        String name = c.getString("username");
                        String email = c.getString("Email");
                        String phonenumber = c.getString("Phonenumber");
                        String Course=c.getString("Course");
                        String Institutionname=c.getString("Institutionname");
                        String Experiecnce=c.getString("Experiecnce");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("user_id", id);
                        contact.put("name", name);
                        contact.put("Course",Course);
                        contact.put("email", email);
                        contact.put("Phonenumber", phonenumber);
                        contact.put("Institutionname",Institutionname);
                        contact.put("Experiecnce",Experiecnce);
                        // adding contact to contact list
                        BankcoachingList.add(contact);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();


                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            adapter = new CustomAdapter(getApplicationContext(),BankcoachingList, R.layout.activity_list_item,
                    new String[]{"Institutionname","name", "email", "mobile","course","Experiecnce"}, new int[]{R.id.Institution_name,R.id.Instructor_name, R.id.email, R.id.mobile,R.id.Course,R.id.Experience});

            lv.setAdapter(adapter);
        }


    }

}

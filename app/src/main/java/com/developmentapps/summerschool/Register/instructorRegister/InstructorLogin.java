package com.developmentapps.summerschool.Register.instructorRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developmentapps.summerschool.Connection.ConnectionCheck;
import com.developmentapps.summerschool.Connection.NoConnectionPage;
import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.memberRegister.LoginActivity;
import com.developmentapps.summerschool.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class InstructorLogin extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] Login = {"Instructor", "Student"};


    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PHONENUMBER = "Phonenumber";
    private static final String KEY_COURSE="Course";
    private static final String KEY_ADDRESS="Address";
    private static final String KEY_Location="Location";
    private static final String KEY_EXPERIENCE="Experience";
    private static final String KEY_INSTITUTION="InstitutionName";
    private static final String KEY_EMPTY = "";
    private EditText inUsername;
    private EditText inPassword;
    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://192.168.43.240/instructor/login.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_instructor_login);


        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Login);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        inUsername = findViewById(R.id.etLoginUsername);
        inPassword = findViewById(R.id.etLoginPassword);

        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);

        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructorLogin.this, InstructorRegister.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionCheck.connection) {
                    //Retrieve the data entered in the edit texts
                    username = inUsername.getText().toString().toLowerCase().trim();
                    password = inPassword.getText().toString().trim();
                    if (validateInputs()) {
                        login();
                    }
                }else {
                    Intent i=new Intent(InstructorLogin.this, NoConnectionPage.class);
                    startActivity(i);
                }
            }
        });
    }

    /**
     * Launch Dashboard Activity on Successful Login
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(InstructorLogin.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username,response.getString(KEY_FULL_NAME),response.getString(KEY_EMAIL),response.getString(KEY_PHONENUMBER),response.getString(KEY_COURSE),response.getString(KEY_ADDRESS),response.getString(KEY_Location),response.getString(KEY_EXPERIENCE),response.getString(KEY_INSTITUTION));
                                loadDashboard();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            inUsername.setError("Username cannot be empty");
            inUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            inPassword.setError("Password cannot be empty");
            inPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), Login[position], Toast.LENGTH_LONG).show();
        if(position == 0)
        {

        }else
        {
            Intent i=new Intent(InstructorLogin.this, LoginActivity.class);
            startActivity(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
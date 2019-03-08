package com.developmentapps.summerschool.Register.instructorRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class InstructorRegister extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_PHONENUMBER = "Phonenumber";
    private static final String KEY_COURSE = "Course";
    private static final String KEY_Location="Location";
    private static final String KEY_EXPERIENCE="Experience";
    private static final String KEY_INSTITUTION="InstitutionName";
    private static final String KEY_EMPTY = "";
    private EditText inUsername;
    private EditText inPassword;
    private EditText inConfirmPassword;
    private EditText inFullName;
    private EditText inEmail;
    private EditText inAddress;
    private EditText inPhonenumber;
    private EditText inCourse;
    private EditText inLocation;
    private EditText inExperience;
    private EditText inInstitution;
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String Email;
    private String Address;
    private String Phonenumber;
    private String course;
    private String Location;
    private String Experience;
    private String InstitutionName;
    private ProgressDialog pDialog;
    private String register_url = "http://192.168.43.240/instructor/register.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_instructor_register);
        inUsername = findViewById(R.id.inUsername);
        inPassword = findViewById(R.id.inPassword);
        inConfirmPassword = findViewById(R.id.inConfirmPassword);
        inFullName = findViewById(R.id.inFullName);
        inEmail = findViewById(R.id.inEmail);
        inPhonenumber=findViewById(R.id.inPhonenumber);
        inCourse=findViewById(R.id.Course);
        inAddress=findViewById(R.id.inaddress);
        inLocation=findViewById(R.id.inlocation);
        inInstitution=findViewById(R.id.ininstitution);
        inExperience=findViewById(R.id.inexperience);


        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InstructorRegister.this, InstructorLogin.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = inUsername.getText().toString().toLowerCase().trim();
                password = inPassword.getText().toString().trim();
                confirmPassword = inConfirmPassword.getText().toString().trim();
                fullName = inFullName.getText().toString().trim();
                Email=inEmail.getText().toString().trim();
                Phonenumber=inPhonenumber.getText().toString().trim();
                course=inCourse.getText().toString().trim();
                Address=inAddress.getText().toString().trim();
                Location=inLocation.getText().toString().trim();
                Experience=inExperience.getText().toString().trim();
                InstitutionName=inInstitution.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(InstructorRegister.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), Detailspage.class);
        startActivity(i);
        finish();

    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_EMAIL, Email);
            request.put(KEY_PHONENUMBER, Phonenumber);
            request.put(KEY_COURSE, course);
            request.put(KEY_ADDRESS, Address);
            request.put(KEY_Location, Location);
            request.put(KEY_EXPERIENCE,Experience);
            request.put(KEY_INSTITUTION, InstitutionName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginUser(username,fullName,Email,Phonenumber,course,Address,Location,Experience,InstitutionName);
                                loadDashboard();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                inUsername.setError("Username already taken!");
                                inUsername.requestFocus();

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
        if (KEY_EMPTY.equals(fullName)) {
            inFullName.setError("Full Name cannot be empty");
            inFullName.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            inUsername.setError("Username cannot be empty");
            inUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(course)) {
            inCourse.setError("Course cannot be empty");
            inCourse.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Email)) {
            inEmail.setError("Email cannot be empty");
            inEmail.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Phonenumber)) {
            inPhonenumber.setError("Phonenumber cannot be empty");
            inPhonenumber.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            inPassword.setError("Password cannot be empty");
            inPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            inConfirmPassword.setError("Confirm Password cannot be empty");
            inConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            inConfirmPassword.setError("Password and Confirm Password does not match");
            inConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}

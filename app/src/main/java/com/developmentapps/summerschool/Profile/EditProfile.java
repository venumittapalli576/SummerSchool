package com.developmentapps.summerschool.Profile;

import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.developmentapps.summerschool.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developmentapps.summerschool.Register.memberRegister.MySingleton;
import com.developmentapps.summerschool.Register.memberRegister.SessionHandler;
import com.developmentapps.summerschool.Register.memberRegister.User;
import com.developmentapps.summerschool.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_FATHERNAME = "Fathername";
    private static final String KEY_AGE = "Age";
    private static final String KEY_INTERESTEDCOURSE = "Interestedcourse";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_LOCATION = "Location";
    private static final String KEY_PHONENUMBER = "Phonenumber";
    private static final String KEY_EMPTY = "";
    private TextView etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private EditText etEmail;
    private EditText etPhonenumber;
    private EditText etFathername;
    private EditText etAge;
    private EditText etIntesteredcourse;
    private EditText etAddress;
    private EditText etLocation;
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String Email;
    private String Phonenumber;
    private String Fathername;
    private String Age;
    private String Intesteredcourse;
    private String Address;
    private String Location;
    private ProgressDialog pDialog;
    Button update;
    //private String register_url = "http://172.168.2.78/summerportal/register.php";
    private String register_url = "http://192.168.43.240/summerportal/update.php";
    private SessionHandler session;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_edit_profile);

        user=session.getUserDetails();

        etUsername = findViewById(R.id.etusername);
        etUsername.setText(user.getUsername());
        etEmail = findViewById(R.id.etmail);

       /* etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);*/

       /* etPhonenumber=findViewById(R.id.etphone);
        etFathername=findViewById(R.id.etFathername);
        etAge=findViewById(R.id.etAge);
        etIntesteredcourse=findViewById(R.id.etSelectedcourse);
        etAddress=findViewById(R.id.etAddress);
        etLocation=findViewById(R.id.etLocation);*/

       update = findViewById(R.id.btnUpdate);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                username = etUsername.getText().toString().toLowerCase().trim();
               /* password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                fullName = etFullName.getText().toString().trim();*/
                Email=etEmail.getText().toString().trim();
               /* Phonenumber=etPhonenumber.getText().toString().trim();
                Fathername=etFathername.getText().toString().trim();
                Age=etAge.getText().toString().trim();
                Intesteredcourse=etIntesteredcourse.getText().toString().trim();
                Address=etAddress.getText().toString().trim();
                Location=etLocation.getText().toString().trim();*/
                if (validateInputs()) {
                    UpdateUser();
                }

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(EditProfile.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
       Toast.makeText(getApplicationContext(),"updted Successfully",Toast.LENGTH_LONG).show();

    }

    private void UpdateUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
           // request.put(KEY_PASSWORD, password);
           // request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_EMAIL, Email);
           /* request.put(KEY_PHONENUMBER, Phonenumber);
            request.put(KEY_FATHERNAME, Fathername);
            request.put(KEY_AGE, Age);
            request.put(KEY_INTERESTEDCOURSE, Intesteredcourse);
            request.put(KEY_ADDRESS, Address);
            request.put(KEY_LOCATION, Location);*/

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
                            if (response.getInt(KEY_STATUS) != 0) {
                                //Set the user session
                                session.loginUser(username, response.getString(KEY_FULL_NAME), Email, response.getString(KEY_PHONENUMBER), response.getString(KEY_INTERESTEDCOURSE),response.getString(KEY_FATHERNAME),response.getString(KEY_LOCATION),response.getString(KEY_ADDRESS),response.getString(KEY_AGE));
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

        if (KEY_EMPTY.equals(username)) {
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }


        return true;
    }
}

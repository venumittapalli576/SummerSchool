package com.developmentapps.summerschool.Register.memberRegister;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.developmentapps.summerschool.Connection.ConnectionCheck;
import com.developmentapps.summerschool.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener , LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    LocationManager locationManager;


    String[] SGender = {"Male", "Female", "Other"};


    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_FATHERNAME = "Fathername";
    private static final String KEY_AGE = "Age";
    private static final String KEY_SELECTEDCOURSE = "Selectedcourse";
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_LOCATION = "Location";
    private static final String KEY_PHONENUMBER = "Phonenumber";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFullName;
    private EditText etEmail;
    private EditText etPhonenumber;
    private EditText etFathername;
    private EditText etAge;
    private EditText etSelectedcourse;
    private EditText etAddress;
    private EditText etLocation;
    private Spinner etGender;
    private String username;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String Email;
    private String Phonenumber;
    private String Fathername;
    private String Age;
    private String Selectedcourse;
    private String Address;
    private String Location;
    private String Gender;
    private ProgressDialog pDialog;
    //private String register_url = "http://172.168.2.78/summerportal/register.php";
    private String register_url = "http://192.168.43.240/summerportal/register.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }



        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        etGender = (Spinner) findViewById(R.id.etGender);
        etGender.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SGender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        etGender.setAdapter(aa);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhonenumber = findViewById(R.id.etPhonenumber);
        etFathername = findViewById(R.id.etFathername);
        etAge = findViewById(R.id.etAge);
        etSelectedcourse = findViewById(R.id.etSelectedcourse);
        etAddress = findViewById(R.id.etAddress);
        etLocation = findViewById(R.id.etLocation);

        Button login = findViewById(R.id.btnRegisterLogin);
        Button register = findViewById(R.id.btnRegister);

        getLocation();

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (ConnectionCheck.connection) {
                    //Retrieve the data entered in the edit texts
                    username = etUsername.getText().toString().toLowerCase().trim();
                    password = etPassword.getText().toString().trim();
                    confirmPassword = etConfirmPassword.getText().toString().trim();
                    fullName = etFullName.getText().toString().trim();
                    Email = etEmail.getText().toString().trim();
                    Phonenumber = etPhonenumber.getText().toString().trim();
                    Fathername = etFathername.getText().toString().trim();
                    Age = etAge.getText().toString().trim();
                    Selectedcourse = etSelectedcourse.getText().toString().trim();
                    Address = etAddress.getText().toString().trim();
                    Location = etLocation.getText().toString().trim();
                    if (validateInputs()) {
                        registerUser();
                    }
               /* } else {
                    Snackbar snackbar = Snackbar
                            .make(v, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            });

                    // Changing message text color
                    snackbar.setActionTextColor(Color.RED);

                    // Changing action button text color
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);

                    snackbar.show();
                }*/

            }
        });

    }

    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadDashboard() {
        //generating otp
        int otp = generateRandomNumber();

        //sending otp
        sendMessage(otp);

        Intent i = new Intent(getApplicationContext(), OTPactivity.class);
        i.putExtra("otp", otp);
        startActivity(i);
        finish();

    }


    public int generateRandomNumber() {
        int randomNumber;
        int range = 9;  // to generate a single number with this range, by default its 0..9
        int length = 4; // by default length is 4

        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < length; i++) {
            int number = secureRandom.nextInt(range);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s = s + number;
        }
        randomNumber = Integer.parseInt(s);
        return randomNumber;
    }


    public void sendMessage(int otp) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Phonenumber, null, ""+otp, null, null);
            Toast.makeText(this,"Message sent...",Toast.LENGTH_LONG).show();


       } else {
           Toast.makeText(this, "enable all permissions", Toast.LENGTH_LONG).show();
        }

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
            request.put(KEY_FATHERNAME, Fathername);
            request.put(KEY_AGE, Age);
            request.put(KEY_SELECTEDCOURSE, Selectedcourse);
            request.put(KEY_ADDRESS, Address);
            request.put(KEY_LOCATION, Location);

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
                                session.loginUser(username, fullName, Email, Phonenumber, Selectedcourse, Fathername, Location, Address, Age);
                                loadDashboard();

                            } else if (response.getInt(KEY_STATUS) == 1) {
                                //Display error message if username is already existsing
                                etUsername.setError("Username already taken!");
                                etUsername.requestFocus();

                            } else {
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
     *
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;

        }
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
        if (KEY_EMPTY.equals(Address)) {
            etAddress.setError("Address cannot be empty");
            etAddress.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Age)) {
            etAge.setError("Age cannot be empty");
            etAge.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Fathername)) {
            etFathername.setError("Fathername cannot be empty");
            etFathername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Selectedcourse)) {
            etSelectedcourse.setError("Intesteredcourse cannot be empty");
            etSelectedcourse.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(Location)) {
            etLocation.setError("Location cannot be empty");
            etLocation.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(Phonenumber)) {
            etPhonenumber.setError("Phonenumber cannot be empty");
            etPhonenumber.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), SGender[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<android.location.Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            etAddress.setText(etAddress.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+ addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
            etLocation.setText(etLocation.getText()+"\n"+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(RegisterActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

package com.developmentapps.summerschool.course;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.memberRegister.SessionHandler;
import com.developmentapps.summerschool.Register.memberRegister.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class coursePage extends AppCompatActivity {


    EditText institutionname, instructorname, email, mobile, course, experience;
    EditText username;
    String InstructorName;
    String Email;
    String Institutionname;
    String Mobile;
    String Experience;
    String Course;
    Button enroll;
    private ProgressDialog pDialog;
    private String ROOT_URL = "http://192.168.43.240/summerportal/";
    private SessionHandler session;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView( R.layout.activity_course_page);
        user = session.getUserDetails();

        username = findViewById(R.id.UserName);
        instructorname =  findViewById(R.id.Name);
        email =  findViewById(R.id.Email);
        institutionname = findViewById(R.id.Institution);
        mobile = findViewById(R.id.Mobile);
        experience = findViewById(R.id.Experience);
        course = findViewById(R.id.course);


        Bundle bundle = getIntent().getExtras();
        InstructorName = bundle.getString("InstructorName");
        Email = bundle.getString("email");
        Institutionname = bundle.getString("institution");
        Mobile = bundle.getString("mobile");
        Experience = bundle.getString("experience");
        Course = bundle.getString("course");

        username.setText(user.getUsername());
        instructorname.setText(InstructorName);
        email.setText(Email);
        institutionname.setText(Institutionname);
        mobile.setText(Mobile);
        experience.setText(Experience);
        course.setText(Course);


        enroll = findViewById(R.id.enroll);
        //Adding listener to button

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                insertUser();

            }
        });
    }

    private void insertUser() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);

        //Defining the method insertuser of our interface
        api.insertUser(


                //Passing the values by getting it from editTexts
                username.getText().toString(),
                course.getText().toString(),
                instructorname.getText().toString(),
                institutionname.getText().toString(),


                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                         BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(coursePage.this, output+ "venu", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(coursePage.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
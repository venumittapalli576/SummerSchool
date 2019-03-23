package com.developmentapps.summerschool.course;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.memberRegister.SessionHandler;
import com.developmentapps.summerschool.Register.memberRegister.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setContentView(R.layout.activity_course_page);
        user = session.getUserDetails();

        username = findViewById(R.id.UserName);
        instructorname = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
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
        RegisterAPI api = RetrofitServer.getRetrofit().create(RegisterAPI.class);

        //Passing the values by getting it from editTexts
        Call<String> call = api.putUser(username.getText().toString(), course.getText().toString(), instructorname.getText().toString(), institutionname.getText().toString());


        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                Log.d("coursePage", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("coursePage", t.getMessage());
            }
        });

    }


}
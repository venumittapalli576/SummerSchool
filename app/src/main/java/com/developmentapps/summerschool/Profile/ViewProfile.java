package com.developmentapps.summerschool.Profile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.SessionHandler;
import com.developmentapps.summerschool.Register.User;

public class ViewProfile extends AppCompatActivity {
    TextView username,mail,phone;
    private SessionHandler session;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        username=findViewById(R.id.username);
        mail=findViewById(R.id.mail);
        //phone=findViewById(R.id.phone);
        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ViewProfile.this,EditProfile.class);
                startActivity(i);
            }
        });
        loadDetails();
    }

    private void loadDetails() {
        username.setText(" "+user.getFullName());
        mail.setText(""+user.getEmail());
        //phone.setText(""+user.getPhonenumber());

    }
}

package com.developmentapps.summerschool.Profile;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.memberRegister.SessionHandler;
import com.developmentapps.summerschool.Register.memberRegister.User;

public class ViewProfile extends AppCompatActivity {
    TextView username,mail,phone;
    private SessionHandler session;
    User user;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();
        edit=findViewById(R.id.edit);
        username=findViewById(R.id.username);
        mail=findViewById(R.id.mail);
        //phone=findViewById(R.id.phone);

        edit.setOnClickListener(new View.OnClickListener() {
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

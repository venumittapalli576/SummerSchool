package com.developmentapps.summerschool.Connection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.Register.memberRegister.LoginActivity;

public class NoConnectionPage extends AppCompatActivity {
    Button retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection_page);

        retry=findViewById(R.id.retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionCheck.connection)
                {
                    Intent i=new Intent(NoConnectionPage.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });


       /*else
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }*/
    }
}

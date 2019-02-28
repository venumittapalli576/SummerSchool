package com.developmentapps.summerschool.Register.memberRegister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.developmentapps.summerschool.R;
import com.developmentapps.summerschool.activity.MainActivity;

public class OTPactivity extends AppCompatActivity {

    EditText otpView;
    int otp,generatedOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        otpView=findViewById(R.id.otp_text);
        Intent intent=getIntent();
        generatedOTP=intent.getIntExtra("otp",0);

    }

    public void submitOtp(View view)
    {
        otp=Integer.parseInt(otpView.getText().toString().trim());
        if(otpView.getText().toString().equals("")){
            otpView.setError("Empty...");
        }else{
              if(otp==generatedOTP)
              {
                startActivity(new Intent(this, MainActivity.class));
                finish();
              }else{
                  otpView.setError("OTP mismatch");
              }
        }
    }
}

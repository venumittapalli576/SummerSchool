package com.developmentapps.summerschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class ShowDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_details);
    }
}

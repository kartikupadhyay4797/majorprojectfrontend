package com.e.majorprojectfrontend;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    Session session;
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        session=new Session(getApplicationContext());

        final int SPLASH_DISPLAY_LENGTH = 3000; //splash screen will be shown for 2 seconds


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(session.isLoggedIn()) {
                    if(session.getType().equals("dealer")) {
                        mainIntent = new Intent(SplashScreenActivity.this, OrderList.class);
                        startActivity(mainIntent);
                        finish();
                    }
                    else{
                        mainIntent = new Intent(SplashScreenActivity.this, DealerList.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                else session.checkLogin();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

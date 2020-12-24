package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivityScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        // Hide status bar

        navigateOnBoardPage();
    }

    public void navigateOnBoardPage() {
        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                PrefManager prefManager = new PrefManager(SplashActivity.this);
//                Log.d("PREF", "Firsttimelaunch"+String.valueOf(prefManager.isFirstTimeLaunch()));
//                Log.d("PREF", "DATA"+String.valueOf(prefManager.getUserToken()+" <--id :::: mob-->"+prefManager.getUserMobile()));
//                if (prefManager.isUserFirstTimeLaunch())
//                {
//                    Intent onBoard = new Intent(SplashActivity.this, OnBoardingActvity.class);
//                    SplashActivity.this.startActivity(onBoard);
//                    SplashActivity.this.finish();
//                }
//                else
//                {
//                    AppConstants.userId=prefManager.getUserToken();
//                    AppConstants.userMobile = prefManager.getUserMobile();
//                    Intent dashBoardScreen = new Intent(SplashActivity.this, DashBoardActvity.class);
//                    SplashActivity.this.startActivity(dashBoardScreen);
//                    SplashActivity.this.finish();
//                }
                PrefManager prefManager = new PrefManager(SplashActivityScreen.this);
                if (prefManager.isUserFirstTimeLaunch())
                {
                    Intent dashBoardScreen = new Intent(SplashActivityScreen.this, LoginActivity.class);
                    SplashActivityScreen.this.startActivity(dashBoardScreen);
                    SplashActivityScreen.this.finish();
                }
                else
                {
                    Intent dashBoardScreen = new Intent(SplashActivityScreen.this, LoginActivity.class);
                    SplashActivityScreen.this.startActivity(dashBoardScreen);
                    SplashActivityScreen.this.finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {

    }

}

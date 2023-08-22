package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * 欢迎页
 */
public class WelcomeActivity extends Activity {

    //欢迎页延时的时间
    private static int DELAY_TIME = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(DELAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        }).start();

    }
}

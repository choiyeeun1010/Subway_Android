package org.techtown.testsubway;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
                public void run(){
                    startActivity(new Intent(Intro.this, MainActivity.class));
                    finish();
                }
            }
            , 3000);
    }
}
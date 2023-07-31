package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Loading_Activity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
    }// onCreate()..

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);  //Loagin화면을 띄운다.
                finish();   //현재 액티비티 종료
            }
        }, 1200); // 화면에 Logo 2초간 보이기
    }// startLoading Method..
}
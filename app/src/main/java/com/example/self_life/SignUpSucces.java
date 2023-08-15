package com.example.self_life;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpSucces extends AppCompatActivity {

    private Button goMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcheck);
        goMainBtn = findViewById(R.id.GoMainBtn1);

        goMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpSucces.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.self_life;

import android.os.Bundle;
import android.text.Layout;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Input_Fund_Data extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;             //파이어베이스 인증
    private DatabaseReference mDatabaseRef;         //실시간 데이터 베이스
    private Layout[] incomeList = new Layout[6];
    private Layout[] expenseList = new Layout[11];
    private Button incomeSelectBtn, expenseSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputfund);

    }
}

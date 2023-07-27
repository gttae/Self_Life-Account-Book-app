package com.example.self_life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.self_life.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

//사용자 계정 정보 모델 클래스
public class User_Account{

    public User_Account(){

    }
    public String getIdToken(){return idToken;}

    public void setIdToken(String idToken){
        this.idToken=idToken;
    }

    private String idToken;

    public String getEmailId(){return emailId;}

    public void setEmailId(String emailId){this.emailId=emailId;}

    private String emailId;

    public String getPassword(){return password;}

    public void setPassword(String password){this.password=password;}

    private String password;
}
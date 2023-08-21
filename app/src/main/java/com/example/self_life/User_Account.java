package com.example.self_life;

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

    public String getCreationDate() { return creationDate; }

    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    private String creationDate; // 계정 생성일자 정보 추가

}
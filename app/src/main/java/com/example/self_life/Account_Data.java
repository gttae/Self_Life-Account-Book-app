package com.example.self_life;

import java.util.Date;

public class Account_Data {
    public Account_Data(){

    }
    private Integer price; //금액

    public Integer getPrice(){return price;}

    public void setPrice(Integer price){
        this.price=price;
    }

    private String fundDivision; //자금분류

    public String getFundDivision(){return fundDivision;}

    public void setFundDivision(String fundDivision){this.fundDivision=fundDivision;}

    private String category; //카테고리

    public String getCategory(){return category;}

    public void setCategory(String category){this.category=category;}

    private Date date; //날짜

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date;}

    private String description; //지출 내역명

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}
}

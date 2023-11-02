package com.example.self_life;

public class Fund_List {
    private String fundId;
    private String fundKind;
    private String fundDivision;
    private String fundMemo;
    private String fundValue;
    private String fundDay;

    public Fund_List(String fundId, String fundKind, String fundDivision, String fundMemo, String fundValue, String fundDay) {
        this.fundId = fundId;
        this.fundKind = fundKind;
        this.fundDivision = fundDivision;
        this.fundMemo = fundMemo;
        this.fundValue = fundValue;
        this.fundDay = fundDay;
    }

    public String getFundId() { return fundId; }
    public String getFundKind() {
        return fundKind;
    }
    public String getFundDivision() {
        return fundDivision;
    }
    public String getFundMemo() {
        return fundMemo;
    }
    public String getFundValue() {
        return fundValue;
    }
    public String getFundDay() {
        return fundDay;
    }
}

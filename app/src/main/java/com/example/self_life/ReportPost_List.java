package com.example.self_life;

public class ReportPost_List {
    private String reportId;
    private String postId;
    private String writer;
    private String category;
    private String title;
    private String Date;
    private String Content;

    public ReportPost_List(String reportId,String postId, String writer, String category, String title, String date, String content) {
        this.reportId =reportId;
        this.postId = postId;
        this.writer = writer;
        this.category = category;
        this.title = title;
        this.Date = date;
        this.Content = content;
    }
    public String getReportId() {
        return reportId;
    }
    public String getPostId() {
        return postId;
    }
    public String getWriter() { return writer; }
    public String getCategory() {
        return category;
    }
    public String getTitle() { return title; }
    public String getDate() { return Date; }
    public String getContent() { return Content; }
}

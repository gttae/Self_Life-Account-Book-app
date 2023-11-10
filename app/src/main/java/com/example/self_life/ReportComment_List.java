package com.example.self_life;

public class ReportComment_List {
    private String reportId;
    private String postId;
    private String nickcname;
    private String Date;
    private String Content;
    private String CommentId;

    public ReportComment_List(String reportId,String postId, String nickcname, String date, String content,String CommentId) {
        this.reportId =reportId;
        this.postId = postId;
        this.nickcname = nickcname;
        this.Date = date;
        this.Content = content;
        this.CommentId = CommentId;
    }
    public String getReportId() {
        return reportId;
    }
    public String getPostId() {
        return postId;
    }
    public String getNickcname() { return nickcname; }
    public String getDate() { return Date; }
    public String getContent() { return Content; }
    public String getCommentId() { return CommentId; }
}

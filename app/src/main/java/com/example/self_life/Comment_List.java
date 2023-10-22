package com.example.self_life;

public class Comment_List {

    private String date;
    private String name;
    private String content;
    private String commentId;

    public Comment_List(String date, String name, String content, String commentId) {
        this.date = date;
        this.name = name;
        this.content = content;
        this.commentId = commentId;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getContent() {return content; }

    public String getCommentId() {
        return commentId;
    }

}

package com.example.self_life;

import java.util.List;

public class Board_Data {

    public Board_Data(String postId, String title, String category, String content, String time, String writer, String comment_id, List<String> image){
        this.postId = postId;
        this.title =title;
        this.category =category;
        this.content =content;
        this.time =time;
        this.writer =writer;
        this.comment_id =comment_id;
        this.image = image;
    }

    public Board_Data(){}

    private String postId;
    private String title;
    private String category;
    private String content;
    private String time;
    private String writer;
    private String comment_id;
    private List<String> image;

    public void setPostId(String postId){this.postId = postId;}
    public String getPostId() {return postId;}
    public void setTitle(String title){this.title = title;}
    public String getTitle() {return title;}
    public void setCategory(String category){this.category = category;}
    public String getCategory() {return category;}
    public void setContent(String content){this.content = content;}
    public String getContent() {return content;}
    public void setTime(String time){this.time = time;}
    public String getTime() {return time;}
    public void setWriter(String writer){this.writer = writer;}
    public String getWriter() {return writer;}
    public void setComment_id(String comment_id){this.comment_id = comment_id;}
    public String getComment_id() {return comment_id;}
    public void setImage(List<String> image){this.image = image;}
    public List<String> getImage() {return image;}

}



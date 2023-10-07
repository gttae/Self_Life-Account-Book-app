package com.example.self_life;

public class Board_List {
    private String category;
    private String title;
    private String postId;

    public Board_List(String category, String title, String postId) {
        this.category = category;
        this.title = title;
        this.postId = postId;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getPostId() {
        return postId;
    }

}

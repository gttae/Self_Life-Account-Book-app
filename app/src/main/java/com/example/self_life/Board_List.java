package com.example.self_life;

public class Board_List {
    private String category;
    private String title;

    public Board_List(String category, String title) {
        this.category = category;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }
}

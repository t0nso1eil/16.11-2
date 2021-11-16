package com.example.demo;

import java.util.ArrayList;

public class Theme {
    String themeName;
    private ArrayList<Comment> comments=new ArrayList<>();
    public Theme(String themeName){

        this.themeName=themeName;
    }
    public String getThemeName() {
        return themeName;
    }
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
    public ArrayList<Comment> getComments() {
        return comments;
    }
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}

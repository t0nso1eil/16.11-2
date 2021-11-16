package com.example.demo;

import java.util.ArrayList;

public class User {
    public String name;
    public int age;
    public String password;
    public ArrayList<Comment> comments;
    public User(String name, String password, int age){
        this.password =password;
        this.name=name;
        this.age=age;
    }
    public int getAge() {
        return age;
    }
    public String getPassword() {
        return password;
    }
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setAge(int age) {
        this.age = age;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", comments=" + comments +
                '}';
    }
}

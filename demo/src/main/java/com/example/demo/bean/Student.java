package com.example.demo.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private String name;
    private int age;
    private int score;

    @Override
    public String toString(){
        return "Student: name=" + this.name + ",age=" + this.age + ",score=" + this.score + ".";
    }
}

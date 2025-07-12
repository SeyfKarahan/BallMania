package com.example.ballmania.models;

public class League {
    private int id;
    private String name;
    private String code;
    private String emblem;
    public League(int id, String name, String code, String emblem) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.emblem = emblem;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getEmblem() {return emblem;}
}
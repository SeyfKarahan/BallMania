package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Team implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("crest")
    private String crest;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCrest() {
        return crest;
    }
}
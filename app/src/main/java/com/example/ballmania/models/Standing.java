package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Standing {
    @SerializedName("stage")
    private String stage;

    @SerializedName("type")
    private String type;

    @SerializedName("group")
    private String group;

    @SerializedName("table")
    private List<Table> table;

    public String getStage() {
        return stage;
    }

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public List<Table> getTable() {
        return table;
    }
}
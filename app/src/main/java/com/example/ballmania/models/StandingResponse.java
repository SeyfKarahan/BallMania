package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingResponse {
    @SerializedName("standings")
    private List<Standing> standings;

    public List<Standing> getStandings() {
        return standings;
    }
}
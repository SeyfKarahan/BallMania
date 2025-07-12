package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ScoreDetail implements Serializable {

    @SerializedName("home")
    private Integer homeTeam;

    @SerializedName("away")
    private Integer awayTeam;

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public Integer getAwayTeam() {
        return awayTeam;
    }
}
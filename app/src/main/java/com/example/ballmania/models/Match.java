package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Match implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("utcDate")
    private String utcDate;

    @SerializedName("status")
    private String status;

    @SerializedName("matchday")
    private int matchday;

    @SerializedName("homeTeam")
    private Team homeTeam;

    @SerializedName("awayTeam")
    private Team awayTeam;

    @SerializedName("score")
    private Score score;

    // Getters

    public int getId() {
        return id;
    }

    public String getUtcDate() {
        return utcDate;
    }

    public String getStatus() {
        return status;
    }

    public int getMatchday() {
        return matchday;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Score getScore() {
        return score;
    }
}
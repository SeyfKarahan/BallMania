package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Score implements Serializable {

    @SerializedName("winner")
    private String winner;

    @SerializedName("duration")
    private String duration;

    @SerializedName("fullTime")
    private ScoreDetail fullTime;

    @SerializedName("halfTime")
    private ScoreDetail halfTime;

    @SerializedName("extraTime")
    private ScoreDetail extraTime;

    @SerializedName("penalties")
    private ScoreDetail penalties;

    // Getters

    public String getWinner() {
        return winner;
    }

    public String getDuration() {
        return duration;
    }

    public ScoreDetail getFullTime() {
        return fullTime;
    }

    public ScoreDetail getHalfTime() {
        return halfTime;
    }

    public ScoreDetail getExtraTime() {
        return extraTime;
    }

    public ScoreDetail getPenalties() {
        return penalties;
    }
}
package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Table implements Serializable {

    @SerializedName("position")
    private int position;

    @SerializedName("team")
    private Team team;

    @SerializedName("playedGames")
    private int playedGames;

    @SerializedName("form")
    private String form;

    @SerializedName("won")
    private int won;

    @SerializedName("draw")
    private int draw;

    @SerializedName("lost")
    private int lost;

    @SerializedName("points")
    private int points;

    @SerializedName("goalsFor")
    private int goalsFor;

    @SerializedName("goalsAgainst")
    private int goalsAgainst;

    @SerializedName("goalDifference")
    private int goalDifference;

    public int getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public String getForm() {
        return form;
    }

    public int getWon() {
        return won;
    }

    public int getDraw() {
        return draw;
    }

    public int getLost() {
        return lost;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }
}
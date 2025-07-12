package com.example.ballmania.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchesResponse {

    @SerializedName("matches")
    private List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }
}
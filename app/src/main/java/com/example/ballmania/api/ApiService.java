package com.example.ballmania.api;

import com.example.ballmania.models.MatchesResponse;
import com.example.ballmania.models.StandingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("competitions/{id}/standings")
    Call<StandingResponse> getStandings(@Path("id") int leagueId);

    @GET("teams/{id}/matches")
    Call<MatchesResponse> getTeamMatches(
            @Path("id") int teamId,
            @Query("status") String status,
            @Query("dateFrom") String dateFrom,
            @Query("dateTo") String dateTo
    );

    @GET("competitions/{id}/matches")
    Call<MatchesResponse> getMatchesByLeague(
            @Path("id") int leagueId,
            @Query("dateFrom") String dateFrom,
            @Query("dateTo") String dateTo
    );
}

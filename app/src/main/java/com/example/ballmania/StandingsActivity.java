package com.example.ballmania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button; 
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballmania.adapters.StandingAdapter;
import com.example.ballmania.api.ApiClient;
import com.example.ballmania.api.ApiService;
import com.example.ballmania.models.StandingResponse;
import com.example.ballmania.models.Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandingsActivity extends AppCompatActivity {

    private RecyclerView standingsRecyclerView;
    private StandingAdapter standingAdapter;
    private ProgressBar progressBar;
    private Button matchResultsButton;
    private Button previousWeekButton;

    private ApiService apiService;

    private int leagueId;
    private String leagueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        setSupportActionBar(findViewById(R.id.standings_toolbar));

        standingsRecyclerView = findViewById(R.id.standingsRecyclerView);
        standingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBar);

        // Defines the buttons for match results and upcoming matches
        matchResultsButton = findViewById(R.id.matchResultsButton);
        previousWeekButton = findViewById(R.id.previousWeekButton);

        // Checks the data retrieved using intent
        leagueId = getIntent().getIntExtra("LEAGUE_ID", -1);
        leagueName = getIntent().getStringExtra("LEAGUE_NAME");

        if (leagueId == -1 || leagueName == null) {
            Toast.makeText(this, "League information could not be found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (getSupportActionBar() != null) { // Creates the page header
            getSupportActionBar().setTitle(leagueName);
        }

        apiService = ApiClient.getClient().create(ApiService.class);

        fetchStandings(leagueId);

        // "Upcoming Matches" event on click
        matchResultsButton.setOnClickListener(v -> {
            Intent intent = new Intent(StandingsActivity.this, MatchResultsActivity.class);
            intent.putExtra("LEAGUE_ID", leagueId);
            intent.putExtra("LEAGUE_NAME", leagueName);
            intent.putExtra("PREVIOUS_WEEK", false); // Upcoming matches from next week
            startActivity(intent);
        });

        // "Match Results" event on click
        previousWeekButton.setOnClickListener(v -> {
            Intent intent = new Intent(StandingsActivity.this, PreviousWeekMatchResultsActivity.class);
            intent.putExtra("LEAGUE_ID", leagueId);
            intent.putExtra("LEAGUE_NAME", leagueName);
            intent.putExtra("PREVIOUS_WEEK", true); // Previous matches from last week
            startActivity(intent);
        });
    }

    private void fetchStandings(int leagueId) {
        progressBar.setVisibility(View.VISIBLE);

        // Sends a call to API for the current league standings
        Call<StandingResponse> call = apiService.getStandings(leagueId);
        call.enqueue(new Callback<StandingResponse>() {
            @Override
            public void onResponse(Call<StandingResponse> call, Response<StandingResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    StandingResponse standingResponse = response.body();
                    // Lists the standings accordingly
                    if (standingResponse != null && standingResponse.getStandings() != null && !standingResponse.getStandings().isEmpty()) {
                        List<Table> tableList = standingResponse.getStandings().get(0).getTable();
                        standingAdapter = new StandingAdapter(tableList);
                        standingsRecyclerView.setAdapter(standingAdapter);
                    } else { // When the response is empty
                        Toast.makeText(StandingsActivity.this, "League standings could not be found", Toast.LENGTH_SHORT).show();
                    }
                } else { // When response fails to be fetched
                    Toast.makeText(StandingsActivity.this, "Data could not be retrieved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            // Displays the error message in an instance of failure
            public void onFailure(Call<StandingResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StandingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.ballmania;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballmania.adapters.MatchResultAdapter;
import com.example.ballmania.api.ApiClient;
import com.example.ballmania.api.ApiService;
import com.example.ballmania.models.Match;
import com.example.ballmania.models.MatchesResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchResultsActivity extends AppCompatActivity {

    private RecyclerView matchResultsRecyclerView;

    private ProgressBar progressBar;
    private MatchResultAdapter adapter;
    private List<Match> matchResults = new ArrayList<>();

    private ApiService apiService;

    private int leagueId;
    private String leagueName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_results);
        setSupportActionBar(findViewById(R.id.thisWeek_toolbar));

        matchResultsRecyclerView = findViewById(R.id.matchResultsRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Sets RecyclerView and adapter
        matchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatchResultAdapter(matchResults);
        matchResultsRecyclerView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        // Checks the data retrieved using intent
        leagueId = getIntent().getIntExtra("LEAGUE_ID", -1);
        leagueName = getIntent().getStringExtra("LEAGUE_NAME");

        if (getSupportActionBar() != null) { // Creates the page header
            getSupportActionBar().setTitle(leagueName + " - Upcoming Matches");
        }

        fetchMatchResults(leagueId);
    }

    private void fetchMatchResults(int leagueId) {
        progressBar.setVisibility(View.VISIBLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Sets the start of the week
        String dateFrom = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 6); // Sets the end of the week
        String dateTo = dateFormat.format(calendar.getTime());

        // Sends a call to API for upcoming matches during the next week from Monday to Sunday
        Call<MatchesResponse> call = apiService.getMatchesByLeague(
                leagueId,
                dateFrom,
                dateTo
        );

        call.enqueue(new Callback<MatchesResponse>() {
            @Override
            public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) { // Lists the matches accordingly
                    List<Match> matches = response.body().getMatches();
                    if (matches != null && !matches.isEmpty()) { // Clears the previous list and updates it
                        matchResults.clear();
                        matchResults.addAll(matches);
                        adapter.notifyDataSetChanged();
                    } else { // When the response is empty
                        Toast.makeText(MatchResultsActivity.this, "No upcoming matches found for the week", Toast.LENGTH_SHORT).show();
                    }
                } else { // When response fails to be fetched
                    Toast.makeText(MatchResultsActivity.this, "Data could not be retrieved: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            // Displays the error message in an instance of failure
            public void onFailure(Call<MatchesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MatchResultsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
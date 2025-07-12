package com.example.ballmania;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ballmania.adapters.MatchAdapter;
import com.example.ballmania.api.ApiClient;
import com.example.ballmania.api.ApiService;
import com.example.ballmania.models.Match;
import com.example.ballmania.models.MatchesResponse;
import com.example.ballmania.models.Table;
import com.example.ballmania.models.Team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Collections;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamDetailActivity extends AppCompatActivity {

    private ImageView teamLogoImageView;
    private TextView teamNameTextView;
    private TextView teamPositionTextView;
    private TextView teamPointsTextView;
    private TextView teamWinsTextView;
    private TextView teamDrawsTextView;
    private TextView teamLossesTextView;
    private TextView teamGoalsForTextView;
    private TextView teamGoalsAgainstTextView;
    private TextView teamGoalDifferenceTextView;

    private RecyclerView upcomingMatchesRecyclerView;

    private MatchAdapter upcomingMatchesAdapter;

    private List<Match> upcomingMatches = new ArrayList<>();

    private ApiService apiService;
    private int teamId;

    private Table table;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detail);

        teamLogoImageView = findViewById(R.id.teamLogoImageView);
        teamNameTextView = findViewById(R.id.teamNameTextView);
        teamPositionTextView = findViewById(R.id.teamPositionTextView);
        teamPointsTextView = findViewById(R.id.teamPointsTextView);
        teamWinsTextView = findViewById(R.id.teamWinsTextView);
        teamDrawsTextView = findViewById(R.id.teamDrawsTextView);
        teamLossesTextView = findViewById(R.id.teamLossesTextView);
        teamGoalsForTextView = findViewById(R.id.teamGoalsForTextView);
        teamGoalsAgainstTextView = findViewById(R.id.teamGoalsAgainstTextView);
        teamGoalDifferenceTextView = findViewById(R.id.teamGoalDifferenceTextView);

        upcomingMatchesRecyclerView = findViewById(R.id.upcomingMatchesRecyclerView);

        upcomingMatchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sets the adapter
        upcomingMatchesAdapter = new MatchAdapter(upcomingMatches);

        upcomingMatchesRecyclerView.setAdapter(upcomingMatchesAdapter);

        // Boots the API service
        apiService = ApiClient.getClient().create(ApiService.class);

        Intent intent = getIntent();
        table = (Table) intent.getSerializableExtra("table");

        // Displays the team crest
        Glide.with(teamLogoImageView.getContext())
                .load(table.getTeam().getCrest())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter())
                .into(teamLogoImageView);

        if (table != null) {
            Team team = table.getTeam();
            teamId = team.getId();

            // Displays the team information
            teamNameTextView.setText(team.getName());
            teamPositionTextView.setText("Position: " + table.getPosition());
            teamPointsTextView.setText("Points: " + table.getPoints());
            teamWinsTextView.setText("" + table.getWon());
            teamDrawsTextView.setText(""+ table.getDraw());
            teamLossesTextView.setText(""+table.getLost());
            teamGoalsForTextView.setText("Goals Scored: " + table.getGoalsFor());
            teamGoalsAgainstTextView.setText("Goals Conceded: " + table.getGoalsAgainst());
            teamGoalDifferenceTextView.setText("Average: " + table.getGoalDifference());


            fetchUpcomingMatches();
        }
    }

    private void fetchUpcomingMatches() {
        // Sets the date format
        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        apiDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Starting date is set as today
        Calendar calendar = Calendar.getInstance();
        String dateFrom = apiDateFormat.format(calendar.getTime());

        // End date is set as 2 months later
        calendar.add(Calendar.MONTH, 2);
        String dateTo = apiDateFormat.format(calendar.getTime());

        // Sends a call to API for the upcoming matches of the team in next 2 months
        Call<MatchesResponse> call = apiService.getTeamMatches(
                teamId,
                "SCHEDULED",
                dateFrom,
                dateTo
        );

        call.enqueue(new Callback<MatchesResponse>() {
            @Override
            public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Match> matches = response.body().getMatches();
                    if (matches != null && !matches.isEmpty()) {
                        // Lists the matches in chronological order and gets the first 5 matches
                        SimpleDateFormat dateFormatFull = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
                        dateFormatFull.setTimeZone(TimeZone.getTimeZone("UTC"));

                        Collections.sort(matches, (m1, m2) -> {
                            try {
                                Date date1 = dateFormatFull.parse(m1.getUtcDate());
                                Date date2 = dateFormatFull.parse(m2.getUtcDate());
                                return date1.compareTo(date2);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                return 0;
                            }
                        });

                        upcomingMatches.clear();
                        int limit = Math.min(5, matches.size());
                        for (int i = 0; i < limit; i++) {
                            upcomingMatches.add(matches.get(i));
                        }
                        upcomingMatchesAdapter.notifyDataSetChanged();
                    } else { // When the response is empty
                        Log.d("UpcomingMatch", "No upcoming matches found");
                    }
                } else { // When response fails to be fetched
                    Log.e("API_ERROR", "Response is not successful: " + response.message());
                }
            }

            @Override
            // Displays the error message in an instance of failure
            public void onFailure(Call<MatchesResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch upcoming matches: " + t.getMessage());
            }
        });
    }
}
package com.example.ballmania.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballmania.R;
import com.example.ballmania.models.Match;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Match> matchList;

    public MatchAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.homeTeamTextView.setText(match.getHomeTeam().getName());
        holder.awayTeamTextView.setText(match.getAwayTeam().getName());

        // Date format
        String dateString = match.getUtcDate();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/YY HH:mm", Locale.getDefault());
            String outputDate = outputFormat.format(date);
            holder.dateTextView.setText(outputDate);
        } catch (Exception e) {
            holder.dateTextView.setText(dateString);
        }

        // Shows the match score
        if (match.getStatus().equals("FINISHED") && match.getScore() != null && match.getScore().getFullTime() != null) {
            Integer homeScore = match.getScore().getFullTime().getHomeTeam();
            Integer awayScore = match.getScore().getFullTime().getAwayTeam();

            String scoreText;
            if (homeScore != null && awayScore != null) {
                scoreText = homeScore + " - " + awayScore;
            } else {
                scoreText = "N/A"; // If there is no match score value
            }
            holder.scoreTextView.setText(scoreText);
        } else {
            holder.scoreTextView.setText("vs");
        }
    }

    @Override
    public int getItemCount() {
        return matchList != null ? matchList.size() : 0;
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {

        TextView homeTeamTextView;
        TextView awayTeamTextView;
        TextView scoreTextView;
        TextView dateTextView;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            homeTeamTextView = itemView.findViewById(R.id.homeTeamTextView);
            awayTeamTextView = itemView.findViewById(R.id.awayTeamTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
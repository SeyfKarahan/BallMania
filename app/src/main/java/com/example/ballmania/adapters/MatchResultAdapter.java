package com.example.ballmania.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ballmania.R;
import com.example.ballmania.models.Match;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MatchResultAdapter extends RecyclerView.Adapter<MatchResultAdapter.MatchResultViewHolder> {

    private List<Match> matchList;

    public MatchResultAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_result, parent, false);
        return new MatchResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchResultViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.homeTeamTextView.setText(match.getHomeTeam().getName());
        holder.awayTeamTextView.setText(match.getAwayTeam().getName());

        Glide.with(holder.itemView.getContext())
                .load(match.getHomeTeam().getCrest())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.homeTeamLogoImageView);

        Glide.with(holder.itemView.getContext())
                .load(match.getAwayTeam().getCrest())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.awayTeamLogoImageView);

        String status = match.getStatus();

        if (status.equals("FINISHED")) {
            // Shows the score for previous matches
            if (match.getScore() != null && match.getScore().getFullTime() != null) {
                Integer homeScore = match.getScore().getFullTime().getHomeTeam();
                Integer awayScore = match.getScore().getFullTime().getAwayTeam();

                String scoreText;
                if (homeScore != null && awayScore != null) {
                    scoreText = homeScore + " - " + awayScore;
                } else {
                    scoreText = "N/A";
                }
                holder.scoreTextView.setText(scoreText);
            } else {
                holder.scoreTextView.setText("N/A");
            }
        } else {
            // Status for upcoming matches
            holder.scoreTextView.setText("-");
        }

        // Match date
        String dateString = match.getUtcDate();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
            String outputDate = outputFormat.format(date);
            holder.dateTextView.setText(outputDate);
        } catch (Exception e) {
            holder.dateTextView.setText(dateString);
        }
    }

    @Override
    public int getItemCount() {
        return matchList != null ? matchList.size() : 0;
    }

    public static class MatchResultViewHolder extends RecyclerView.ViewHolder {

        TextView homeTeamTextView;
        ImageView homeTeamLogoImageView;
        TextView awayTeamTextView;
        ImageView awayTeamLogoImageView;
        TextView scoreTextView;
        TextView dateTextView;

        public MatchResultViewHolder(@NonNull View itemView) {
            super(itemView);

            homeTeamTextView = itemView.findViewById(R.id.homeTeamTextView);
            homeTeamLogoImageView = itemView.findViewById(R.id.homeTeamLogoImageView);
            awayTeamTextView = itemView.findViewById(R.id.awayTeamTextView);
            awayTeamLogoImageView = itemView.findViewById(R.id.awayTeamLogoImageView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}
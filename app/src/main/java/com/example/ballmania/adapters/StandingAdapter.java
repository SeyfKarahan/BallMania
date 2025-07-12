package com.example.ballmania.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ballmania.R;
import com.example.ballmania.TeamDetailActivity;
import com.example.ballmania.models.Table;

import java.util.List;

public class StandingAdapter extends RecyclerView.Adapter<StandingAdapter.StandingViewHolder> {

    private List<Table> tableList;

    public StandingAdapter(List<Table> tableList) {
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public StandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_standing, parent, false);
        return new StandingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingViewHolder holder, int position) {
        Table table = tableList.get(position);

        holder.teamPositionTextView.setText(String.valueOf(table.getPosition()));
        holder.teamNameTextView.setText(table.getTeam().getName());
        holder.teamWinTextView.setText(String.valueOf(table.getWon()));
        holder.teamDrawTextView.setText(String.valueOf(table.getDraw()));
        holder.teamLoseTextView.setText(String.valueOf(table.getLost()));
        holder.teamPointsTextView.setText(String.valueOf(table.getPoints()));

        Glide.with(holder.itemView.getContext())
                .load(table.getTeam().getCrest())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.teamLogoImageView);


        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, TeamDetailActivity.class);
            intent.putExtra("table", table);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }

    public static class StandingViewHolder extends RecyclerView.ViewHolder {

        TextView teamPositionTextView;

        ImageView teamLogoImageView;
        TextView teamNameTextView;
        TextView teamWinTextView;
        TextView teamDrawTextView;
        TextView teamLoseTextView;
        TextView teamPointsTextView;

        public StandingViewHolder(@NonNull View itemView) {
            super(itemView);

            teamPositionTextView = itemView.findViewById(R.id.teamPositionTextView);
            teamLogoImageView = itemView.findViewById(R.id.teamLogoImageView);
            teamNameTextView = itemView.findViewById(R.id.teamNameTextView);
            teamWinTextView = itemView.findViewById(R.id.teamWinTextView);
            teamDrawTextView = itemView.findViewById(R.id.teamDrawTextView);
            teamLoseTextView = itemView.findViewById(R.id.teamLoseTextView);
            teamPointsTextView = itemView.findViewById(R.id.teamPointsTextView);
        }
    }
}
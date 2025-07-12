// com/example/ballmania/adapters/LeagueAdapter.java
package com.example.ballmania.adapters;

import android.content.Context;
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
import com.example.ballmania.models.League;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private List<League> leagues;
    private OnItemClickListener listener;
    private Context context; // Context needed for Glide

    // Interface for click handling
    public interface OnItemClickListener {
        void onItemClick(League league);
    }

    public LeagueAdapter(List<League> leagues, OnItemClickListener listener) {
        this.context = context;
        this.leagues = leagues;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league, parent, false);
        return new LeagueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        final League league = leagues.get(position);
        holder.leagueNameTextView.setText(league.getName());

        // Load image using Glide without needing the context field
        Glide.with(holder.itemView.getContext())
                .load(league.getEmblem())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .fitCenter())
                .into(holder.imageViewLeague);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(league);
            }
        });
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    // ViewHolder class
    public static class LeagueViewHolder extends RecyclerView.ViewHolder {
        TextView leagueNameTextView;
        ImageView imageViewLeague;

        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueNameTextView = itemView.findViewById(R.id.leagueNameTextView);
            imageViewLeague = itemView.findViewById(R.id.imageViewLeague);
        }
    }

    // Updates the list when needed
    public void updateList(List<League> newLeagues) {
        leagues = newLeagues;
        notifyDataSetChanged();
    }
}
package com.example.ballmania;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ballmania.adapters.LeagueAdapter;
import com.example.ballmania.models.League;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.main_toolbar));
        
        RecyclerView leaguesRecyclerView = findViewById(R.id.leaguesRecyclerView); //RecyclerView to list the items
        leaguesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<League> leagues = new ArrayList<>(); //Retrieves the league data and puts them in an array
        leagues.add(new League(2021, "Premier League", "PL", "https://crests.football-data.org/PL.png"));
        leagues.add(new League(2002, "Bundesliga", "BL1", "https://crests.football-data.org/BL1.png"));
        leagues.add(new League(2015, "Ligue 1", "FL1", "https://crests.football-data.org/FL1.png"));
        leagues.add(new League(2019, "Serie A", "SA", "https://crests.football-data.org/SA.png"));

        LeagueAdapter leagueAdapter = new LeagueAdapter(leagues, new LeagueAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(League league) { //When the button is pressed, the corresponding league standings will show
                Intent intent = new Intent(MainActivity.this, StandingsActivity.class);
                intent.putExtra("LEAGUE_ID", league.getId());
                intent.putExtra("LEAGUE_NAME", league.getName());
                startActivity(intent);
            }
        });

        leaguesRecyclerView.setAdapter(leagueAdapter);
    }
}
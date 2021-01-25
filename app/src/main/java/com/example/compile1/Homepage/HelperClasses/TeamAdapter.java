package com.example.compile1.Homepage.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.R;
import com.example.compile1.Team.CreateTeamActivity;
import com.example.compile1.Team.TeamPageActivity;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder>  {

    static ArrayList<TeamHelperClass> teamLocations = new ArrayList<>();
    Context context;
    //collect arraylist of user db
    public TeamAdapter(ArrayList<TeamHelperClass> teamLocations,Context context) {
        this.teamLocations = teamLocations;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamAdapter.TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_team_list,parent,false);
        TeamViewHolder teamViewHolder = new TeamViewHolder(view);

        return teamViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.TeamViewHolder holder, int position) {
        TeamHelperClass teamHelperClass = teamLocations.get(position);
        holder.title.setText(teamHelperClass.getTitle());
        holder.desc.setText(teamHelperClass.getDescription());
        holder.teamid.setText(teamHelperClass.getID());
    }

    @Override
    public int getItemCount() {
        return teamLocations.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder{
        TextView title,desc,teamid;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.team_title);
            desc = itemView.findViewById(R.id.team_desc);
            teamid = itemView.findViewById(R.id.team_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, TeamPageActivity.class);
                    intent.putExtra("id",teamid.getText().toString());
                    intent.putExtra("teamName", title.getText().toString());
                    context.startActivity(intent);
                }
            });
        }



    }
}

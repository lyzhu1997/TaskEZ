package com.example.compile1.Homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.compile1.Login.UserDetail;
import com.example.compile1.Note.NoteMainActivity;
import com.example.compile1.R;
import com.example.compile1.Homepage.HelperClasses.TeamAdapter;
import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.Homepage.HelperClasses.UserTaskAdapter;
import com.example.compile1.Homepage.HelperClasses.UserTaskHelperClass;
import com.example.compile1.Team.CreateTeamActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    //database
    private FirebaseDatabase db;
    private UserDetail user;
    private DatabaseReference ref;

    private Button noteBtn;

    ArrayList<String> key = new ArrayList<>();
    ArrayList<TeamHelperClass>teams = new ArrayList<>();

    private String email;

    //Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    RecyclerView userTeamRecycler,userTaskRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();

        db = FirebaseDatabase.getInstance();

        try{
            readUserData();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //Hooks for user in team
        userTeamRecycler = findViewById(R.id.userteam_recycler);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TextView nickN = (TextView)findViewById(R.id.tv_username);
                nickN.setText(user.getNickname());
            }
        },2000);

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    readTeamsData();
                } catch (Exception e) {
                    Toast.makeText(HomepageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },2000);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    userTeamRecycler();
                } catch (Exception e) {
                    Toast.makeText(HomepageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },4000);
        //Hooks for user task assigned
        userTaskRecycler = findViewById(R.id.usertask_recycler);
        userTaskRecycler();

//        noteBtn = (Button)findViewById(R.id.notePage);
//        noteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                directToNotePage();
//            }
//        });

        System.out.println("Meowwwwww");
        //hooks for side drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        //navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noteIntent = new Intent(HomepageActivity.this,NoteMainActivity.class);
                startActivity(noteIntent);
            }
        });
    }

    //Function for when side drawer is pressed back
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }



    //user's task recyclerview
    private void userTaskRecycler() {
        userTaskRecycler.setHasFixedSize(true);
        userTaskRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //Project view
        ArrayList<UserTaskHelperClass> taskLocations = new ArrayList<>();
//        if(user.taskTitle!=null){
//            for(int i =0; i<user.taskTitle.size();i++){
//
//                taskLocations.add(new UserTaskHelperClass(user.taskTitle.get(i),"In progress"));
//            }
//        }
        UserTaskAdapter adapter = new UserTaskAdapter(taskLocations,getApplicationContext());
        userTaskRecycler.setAdapter(adapter);
    }

    //user's team recyclerview
    private void userTeamRecycler() {
        userTeamRecycler.setHasFixedSize(true);
        userTeamRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        //Project view
        ArrayList<TeamHelperClass> teamLocations = new ArrayList<>();
        for(int i = 0; i < teams.size(); i++){
            teamLocations.add(new TeamHelperClass(teams.get(i).getTitle(),teams.get(i).getDescription(), teams.get(i).getID()));
        }
        TeamAdapter adapter = new TeamAdapter(teamLocations,getApplicationContext());
        userTeamRecycler.setAdapter(adapter);
    }

    //create team
    public void addTeamOnCliked(View view){
        //click to team activity
        Intent intent = new Intent(this, CreateTeamActivity.class);
        startActivity(intent);
    }

    private void readUserData() throws Exception {
        int check = 0;
        boolean checkUsername = false;
        String[] split = email.split("@");
        DatabaseReference Ref = db.getReference("user/"+split[0]);
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    user = snapshot.getValue(UserDetail.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readTeamsData() throws Exception {
        int check = 0;
        boolean checkUsername = false;
        String[] split = email.split("@");
        DatabaseReference readRef = db.getReference("teams");
        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot teamSnapshot: snapshot.getChildren()){
                    String key = teamSnapshot.getKey();
                    boolean check = false;
                    try{
                        TeamHelperClass team = teamSnapshot.getValue(TeamHelperClass.class);
                        for(int i = 0; i < user.teamID.size();i++){
                            if(teamSnapshot.getKey().equals(user.teamID.get(i))){
                                check = true;
                            }
                        }
                        if(check){
                            teams.add(team);
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(HomepageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void directToNotePage(){
        Intent intent = new Intent(this, NoteMainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}
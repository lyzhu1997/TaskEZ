package com.example.compile1.Team;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.Collaboration.ChatActivity;
import com.example.compile1.Collaboration.ChatMessage;
import com.example.compile1.HelperClasses.MemberAdapter;
import com.example.compile1.HelperClasses.SwipeController;
import com.example.compile1.HelperClasses.SwipeControllerActions;
import com.example.compile1.HelperClasses.Task;
import com.example.compile1.HelperClasses.TaskAdapter;
import com.example.compile1.Homepage.HelperClasses.TeamAdapter;
import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamPageActivity extends AppCompatActivity {
    RecyclerView taskRecycler, memberRecycler;
    TextView teamNameTv;
    String teamName;
    Button chat_button;
    private ImageView addTask;
    private String teamID;
    //database
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private TeamHelperClass team = new TeamHelperClass();

    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_page);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("teams");
        teamID = getIntent().getStringExtra("id");

        teamName = getIntent().getStringExtra("teamName");
        teamNameTv = findViewById(R.id.team_name);
        teamNameTv.setText(teamName);
        chat_button = findViewById(R.id.chat_button);
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chat);
            }
        });

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(teamName);

        taskRecycler = findViewById(R.id.task_recycler);
        memberRecycler = findViewById(R.id.member_recycler);
        try {
            readTeamsData();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                taskRecycler();
                memberRecycler();
            }
        },3000);

        addTask = (ImageView) findViewById(R.id.etAddTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskOnCliked(v);
            }
        });
        //back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void memberRecycler() {
        memberRecycler.setHasFixedSize(true);
        memberRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //Project view
        ArrayList<String> teamLocations = new ArrayList<>();
        for(int i = 0; i < team.getUsers().size(); i ++){
            teamLocations.add(team.getUsers().get(i).getNickname());
        }
        MemberAdapter adapter = new MemberAdapter(teamLocations,getApplicationContext());
        memberRecycler.setAdapter(adapter);

    }

    private void taskRecycler() {
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ArrayList<Task> taskLocations = new ArrayList<>();
        if(team.getTasks()!=null){
            for(int i = 0; i < team.getTasks().size(); i++){
                taskLocations.add(new Task(team.getTasks().get(i).getTaskName(),team.getTasks().get(i).getTask_due(),team.getTasks().get(i).getTask_desc(),teamID,String.valueOf(i)));
            }
        }
        TaskAdapter adapter = new TaskAdapter(taskLocations,getApplicationContext());
        taskRecycler.setAdapter(adapter);

        //swipe goes swoosh
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //edit task
                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("taskName",taskLocations.get(position).getTaskName());
                intent.putExtra("taskDesc",taskLocations.get(position).getTaskDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
            //Enable left side to delete works
            //to use this please uncomment below and in swipe controller.java on Draw child
            @Override
            public void onLeftClicked(int position) {
                //delete item in recycleview task
                taskLocations.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(taskRecycler);

        taskRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

    }

    public void addTaskOnCliked(View view){
        Intent addtask = new Intent(this, CreateTaskActivity.class);
        addtask.putExtra("teamID",teamID);
        startActivity(addtask);
    }

    public void addMemberOnClicked(View view) {
        Intent addtask = new Intent(this, AddMemberActivity.class);
        startActivity(addtask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readTeamsData() throws Exception {
        int check = 0;
        boolean checkUsername = false;
        DatabaseReference readRef = db.getReference("teams/"+teamID);
        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team = snapshot.getValue(TeamHelperClass.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
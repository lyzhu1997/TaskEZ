package com.example.compile1.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compile1.HelperClasses.TaskItem;
import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.Homepage.HelperClasses.UserTaskHelperClass;
import com.example.compile1.Login.UserDetail;
import com.example.compile1.R;
import com.example.compile1.HelperClasses.SwipeController;
import com.example.compile1.HelperClasses.SwipeControllerActions;
import com.example.compile1.HelperClasses.TaskItem;
import com.example.compile1.HelperClasses.TaskItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private String teamID = "nothing";
    private String taskID = "nothing";
    //database
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private UserDetail user;
    private TeamHelperClass team;

    private Spinner assignToUser;

    //Add database recyclerview taskItemRecycler
    RecyclerView taskItemRecycler;
    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("teams");

        Intent intent = getIntent();
        TextView taskName = findViewById(R.id.task_name);
        TextView taskDesc = findViewById(R.id.task_desc);
        TextView taskDue = findViewById(R.id.taskDue);
        taskName.setText(intent.getStringExtra("taskName"));
        taskDesc.setText(intent.getStringExtra("taskDesc"));
        taskDue.setText(intent.getStringExtra("taskDue"));
        teamID = intent.getStringExtra("teamID");
        taskID = intent.getStringExtra("taskID");
        readData();

//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<TaskItem> taskItems = (ArrayList<TaskItem>) args.getSerializable("taskItems");
        taskItemRecycler = findViewById(R.id.taskitem_recycler);

        taskItemRecycler();

        Button assignUser = (Button)findViewById(R.id.assignedUserBttn);
        assignUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignTask();
            }
        });
    }

    private void addItemOnSpinner(){
        assignToUser = (Spinner)findViewById(R.id.spinnerAssigned);
        List<String> list = new ArrayList<String>();
        if(team!=null){
            for(int i = 0; i < team.getUsers().size();i++){
                list.add(team.getUsers().get(i).getEmail());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignToUser.setAdapter(dataAdapter);
    }

    private void taskItemRecycler() {
        //add recycle task item
        taskItemRecycler.setHasFixedSize(true);
        taskItemRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<TaskItem>  taskItem = new ArrayList<TaskItem>();
        taskItem.add(new TaskItem("Meow",null,false));
        taskItem.add(new TaskItem("Bark",null,false));
        taskItem.add(new TaskItem("Chrip",null,false));
        TaskItemAdapter adapter = new TaskItemAdapter(taskItem,getApplicationContext());
        taskItemRecycler.setAdapter(adapter);

        //item goes swoosh

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //edit item
                editItemDialog();
            }
            //Enable left side to delete works
            //to use this please uncomment below and in swipe controller.java on Draw child
            @Override
            public void onLeftClicked(int position) {
                //delete item
                taskItem.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(taskItemRecycler);

        taskItemRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void editItemDialog() {
        EditTaskItemDialog taskItemDialog = new EditTaskItemDialog();
        taskItemDialog.show(getSupportFragmentManager(),"taskItemEdit");
    }


    public void addTaskOnCliked(View view){
        createItemDialog();
    }
    private void createItemDialog() {
        CreateTaskItemDialog taskItemDialog = new CreateTaskItemDialog();
        taskItemDialog.show(getSupportFragmentManager(),"taskItemCreate");
    }

    private void assignTask(){
        assignToUser = (Spinner)findViewById(R.id.spinnerAssigned);
        TextView title = (TextView)findViewById(R.id.task_name);
        String titleName = title.getText().toString();

        String userAssigned = String.valueOf(assignToUser.getSelectedItem());
        String[] split = userAssigned.split("@");
        DatabaseReference refUser = db.getReference("user/"+split[0]);
//        refUser.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserDetail temp = snapshot.getValue(UserDetail.class);
//                if(snapshot.exists()){
//                    user = temp;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        ArrayList<String>taskTitles = new ArrayList<>();
//        if(user.taskTitle!=null){
//            for(int i = 0; i < user.taskTitle.size();i++){
//                taskTitles.add(user.taskTitle.get(i));
//            }
//        }
//        taskTitles.add(titleName);
//        user.setTaskTitle(taskTitles);
//        refUser.child("taskTitles").setValue(taskTitles);

//        ArrayList<UserTaskHelperClass>tasks = new ArrayList<>();
//        if(team!=null){
//
//            for(int i =0; i<team.getTasks().size();i++){
//                UserTaskHelperClass temp = team.getTasks().get(i);
//                if(String.valueOf(i).equals(taskID)){
//                    temp.setAssignedUser(userAssigned);
//                }
//                tasks.add(team.getTasks().get(i));
//            }
//            team.setTasks(tasks);
//            ref.child(teamID).setValue(team);
//        }

        Intent intent = new Intent(this,TeamPageActivity.class);
        startActivity(intent);


    }

    private void readData() {
        int check = 0;
        boolean checkUsername = false;
        String name = "";
        DatabaseReference readRef = db.getReference("teams/"+teamID);
        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeamHelperClass temp = snapshot.getValue(TeamHelperClass.class);
                if(snapshot.exists()){
                    team = temp;
                    addItemOnSpinner();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
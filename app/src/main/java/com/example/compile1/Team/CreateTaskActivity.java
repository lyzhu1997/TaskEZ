package com.example.compile1.Team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.Homepage.HelperClasses.UserTaskHelperClass;
import com.example.compile1.Login.UserDetail;
import com.example.compile1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {
    TextView taskDue;
    public final String TAG = "CreateTaskActivity";
    DatePickerDialog.OnDateSetListener taskDateSetListener;


    private UserTaskHelperClass task;

    private String teamID ="nothing";
    private String dateDue = "";
    private Button createTask, Cancel;
    //database
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private TeamHelperClass team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        db = FirebaseDatabase.getInstance();
        ref = db.getReference("teams");
        teamID = getIntent().getStringExtra("teamID");
        readData();

        //Date time picker
        taskDue = findViewById(R.id.task_due);
        taskDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        taskDateSetListener,year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        taskDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet date"+ dayOfMonth+"-"+month+"-"+year);
                String date = dayOfMonth +"-"+month+"-"+year;
                dateDue = date;
                taskDue.setText(date);

            }
        };
        EditText taskName,taskDesc;
        Button btnAddTask,btnCancel;

        taskName = findViewById(R.id.task_name);
        taskDesc = findViewById(R.id.task_desc);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTask(v);
            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClicked();
            }
        });
        //firebase



    }

    private void CreateTask(View view){
        EditText taskName, taskDesc;
        taskName = (EditText)findViewById(R.id.task_name);
        taskDesc = (EditText)findViewById(R.id.task_desc);

        String taskname = taskName.getText().toString();
        String desc = taskDesc.getText().toString();
        if(taskname.isEmpty() || desc.isEmpty() || dateDue.isEmpty()){
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            task = new UserTaskHelperClass(taskname,desc,dateDue,teamID);
            ArrayList<UserTaskHelperClass>temp = new ArrayList<>();
            if(team.getTasks()!=null){
                temp = team.getTasks();
            }
            temp.add(task);

            team.setTasks(temp);
            ref.child(teamID).setValue(team);
            Toast.makeText(this, "Task created", Toast.LENGTH_SHORT).show();
            directToTeamPage();
        }

    }

    private void cancelClicked(){
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }

    private void directToTeamPage(){
        Intent toTeamPage = new Intent(this, TeamPageActivity.class);
        startActivity(toTeamPage);
        finish();
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.compile1.Team;

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

import com.example.compile1.R;

import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {
    TextView taskDue;
    public final String TAG = "CreateTaskActivity";
    DatePickerDialog.OnDateSetListener taskDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        Button addTask = (Button) findViewById(R.id.btnAddTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Button backBtn = (Button) findViewById(R.id.btnTaskCancel);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

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
                taskDue.setText(date);
            }
        };

        EditText taskName,taskDesc;
        Button btnAddTask,btnCancel;
        taskName = findViewById(R.id.task_name);
        taskDesc = findViewById(R.id.task_desc);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnUpdateCancel);

        //firebase



    }
}
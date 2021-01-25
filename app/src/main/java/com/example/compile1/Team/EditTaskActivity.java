package com.example.compile1.Team;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.compile1.R;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {
    TextView taskDue;
    public final String TAG = "EditTaskActivity";
    DatePickerDialog.OnDateSetListener taskDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskDue = findViewById(R.id.task_due);
        taskDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditTaskActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
    }
}
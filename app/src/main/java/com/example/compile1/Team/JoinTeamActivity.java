package com.example.compile1.Team;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compile1.R;

import javax.xml.validation.Validator;

public class JoinTeamActivity extends AppCompatActivity {


    private Validator nonempty_validate;
    private EditText editTextName, editTextLeaderName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team);

        Toolbar toolbar = (Toolbar) findViewById(R.id.joinTeamToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Join Team");

    }

    public void joinRecord(View view) {
        editTextName = (EditText) findViewById(R.id.editTextName);

        String name, leaderName;

        name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Please enter name");
            return;
        }

        leaderName = editTextLeaderName.getText().toString();
        if (leaderName.isEmpty()) {
            editTextLeaderName.setError("Please enter leader name");
            return;
        }

        // find in database if team available
        // if available, join team
        // else, show toast message



        boolean teamAvailable = false;
        boolean leaderNameAvailable = false;
        if (!teamAvailable & !leaderNameAvailable) {
            Toast.makeText(getApplicationContext(), "No record", Toast.LENGTH_SHORT).show();
        }

        this.finish();
    }

    public void joinTeamBackOnClicked(View view) {
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }

}
package com.example.compile1.Team;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.compile1.R;

import javax.xml.validation.Validator;

public class AddMemberActivity extends AppCompatActivity {

    private Validator nonempty_validate;
    private EditText editTextName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
    }

    public void saveRecord(View view) {
        editTextName = (EditText) findViewById(R.id.editTextName);

        String name;

        name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Please enter name");
            return;
        }

        // add member name to database


        this.finish();
    }

    public void addMemberBackOnClicked(View view) {
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }

}
package com.example.compile1.Note;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    SimpleDatabase simpleDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity_main_note);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Cached Notes");
        noItemText = findViewById(R.id.noItemText);

        // database get all notes
        simpleDatabase = new SimpleDatabase(this);
        List<Note> allNotes = simpleDatabase.getAllNotes();
        // end database
        recyclerView = findViewById(R.id.allNotesList);

        if(allNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(allNotes);
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.noteFloatBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoteMainActivity.this, "Add New Note", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NoteMainActivity.this,AddNote.class);
                startActivity(i);
            }
        });

    }

    private void displayList(List<Note> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,allNotes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.note_main_menu,menu);
//        return true;
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.add){
//            Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show();
//            Intent i = new Intent(this,AddNote.class);
//            startActivity(i);
//        }
//        return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // database get all note
        List<Note> getAllNotes = simpleDatabase.getAllNotes();
        // database end
        if(getAllNotes.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllNotes);
        }


    }

}

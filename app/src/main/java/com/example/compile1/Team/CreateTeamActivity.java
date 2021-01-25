package com.example.compile1.Team;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.compile1.HelperClasses.AddMemberAdapter;
import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.Login.UserDetail;
import com.example.compile1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class CreateTeamActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText desc;
    AddMemberAdapter memberNames;
    ListView listViewRecords;
    ArrayList<String> values;
    ArrayList<String> key = new ArrayList<>();
    private int userCounter;
    private ArrayList<UserDetail> users;
    private Button addMember;
    private boolean check = false;

    private TeamHelperClass team;

    //database
    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        users = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("teams");
        readData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.creatTeamToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Team");

        values = new ArrayList<String>();
        listViewRecords = (ListView) findViewById(R.id.addMemberLV);

        addMember = (Button) findViewById(R.id.teamAddMemberBttn);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemberOnCliked(v);
            }
        });
    }

    public void addMemberOnCliked(View view) {
        EditText etAdd = (EditText) findViewById(R.id.ETaddMember);
        String email = etAdd.getText().toString();
        if (isValidEmail(email)) {
            boolean check = checkUser(email);
            if (check == true) {
                if (!values.contains(email)) {
                    values.add(email);
                    memberNames = new AddMemberAdapter(this, R.layout.addmember_list_item, values);
                    listViewRecords.setAdapter(memberNames);
                } else {
                    Toast.makeText(getApplicationContext(), "Existing Email", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No Such User", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void saveRecord(View view) {
        editTextName = (EditText) findViewById(R.id.editTextName);
        desc = (EditText) findViewById(R.id.editTextDescription);
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        String name, description;

        name = editTextName.getText().toString();
        description = desc.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Please enter team name");
            return;
        }

        team = new TeamHelperClass(id,name,description,users);
        ref.child(id).setValue(team);

        // add new team name to database
        try{

            for(int i = 0; i < users.size(); i++){
                boolean checkEmail = false;
                for(int j = 0; j < values.size(); j++){
                    if(values.get(j).equals(users.get(i).getEmail())){
                        checkEmail = true;
                    }
                }

                if(checkEmail==true){
                    UserDetail temp = users.get(i);
                    temp.teamID.add(id);
                    users.set(i,temp);
                }
            }

            for(int i = 0; i < users.size(); i++){
                String[] split = users.get(i).getEmail().split("@");
                db.getReference("user").child(split[0]).setValue(users.get(i));
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        this.finish();
    }

    public void createTeamBackOnClicked(View view) {
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }

    private void readData() {
        int check = 0;
        boolean checkUsername = false;
        String name = "";
        DatabaseReference readRef = db.getReference("user");
        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapShot : snapshot.getChildren()) {
                    key.add(childSnapShot.getKey());
                    UserDetail user = childSnapShot.getValue(UserDetail.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkUser(String email) {
        try {
            boolean checkUsername = false;
            String[] splitStr = email.split("@");
            for (int i = 0; i < key.size(); i++) {
                if (key.get(0).equals(splitStr[0])) {
                    checkUsername = true;
                }
            }
            return checkUsername;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}

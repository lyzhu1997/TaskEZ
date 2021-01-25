package com.example.compile1.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.compile1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailID,password,confirmedPassword, nickName, contactNumber;
    private Button signUpBttn;
    private ImageButton backBttn;


    //database
    private FirebaseDatabase db;
    private UserDetail user;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();
        ref =db.getReference("user");

        emailID = (EditText)findViewById(R.id.SignUpEmail);
        password = (EditText)findViewById(R.id.SignUpPassword);
        confirmedPassword = (EditText)findViewById(R.id.SignUpConfirmPassword);
        nickName = (EditText)findViewById(R.id.signUpNickname);
        contactNumber = (EditText)findViewById(R.id.signUpContactNumber);

        backBttn = (ImageButton)findViewById(R.id.signUpBackBttn);
        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToLogin();
            }
        });


        signUpBttn = (Button)findViewById(R.id.registerBttn);
        signUpBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpAcc(emailID,password,confirmedPassword,nickName, contactNumber, mAuth);
            }
        });
    }

    private void signUpAcc(EditText Email, EditText Password, EditText ConfirmedPassword, EditText nickName, EditText contactNumber, FirebaseAuth auth){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirmedPassword = ConfirmedPassword.getText().toString();
        String nickname = nickName.getText().toString();
        String contactnum = contactNumber.getText().toString();
        if(email.isEmpty() || password.isEmpty() || confirmedPassword.isEmpty() || nickname.isEmpty() || contactnum.isEmpty())
        {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            boolean check_Password_Similarity = checkPasswordSimilarity(password,confirmedPassword);
            boolean check_Email_Format = checkEmail(email);
            if(check_Email_Format)
            {
                if(check_Password_Similarity)
                {
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                user = new UserDetail(nickname,contactnum,email);
                                String[] splitStr = email.split("@");
                                ref.child(splitStr[0]).setValue(user);
                                directToLogin();
                            }
                            else{
                                Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(this, "The passwords didn't match. Please enter again.", Toast.LENGTH_SHORT).show();
                    Password.setText("");
                    ConfirmedPassword.setText("");
                }
            }
            else
            {
                Toast.makeText(this, "Please enter a proper email", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean checkPasswordSimilarity(String password, String confirmPassword){
        boolean check = false;
        if(password.equals(confirmPassword)){
            check = true;
        }
        return check;
    }

    private boolean checkEmail(String userEmail){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(userEmail).matches();
    }

    private void directToLogin(){
        Intent backIntent = new Intent();
        setResult(RESULT_OK, backIntent);
        finish();
    }
}
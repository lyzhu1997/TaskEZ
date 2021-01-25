package com.example.compile1.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.compile1.Homepage.HelperClasses.TeamHelperClass;
import com.example.compile1.Homepage.HomepageActivity;
import com.example.compile1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText userEmail, userPassword;
    private Button loginBttn, signUpBttn, forgotPasswordBttn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();


        auth = FirebaseAuth.getInstance();

        userEmail = (EditText)findViewById(R.id.LoginUserEmail);
        userPassword = (EditText)findViewById(R.id.LoginUserPassword);

        loginBttn = (Button)findViewById(R.id.loginLoginBttn);
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAcc(userEmail.getText().toString(),userPassword.getText().toString(),auth);
            }
        });

        signUpBttn = (Button)findViewById(R.id.loginSignUpBttn);
        signUpBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToSignUpPage();
            }
        });
        forgotPasswordBttn = (Button)findViewById(R.id.loginForgotPasswordBttn);
        forgotPasswordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToForgotPassword();
            }
        });

        if(auth.getCurrentUser()!=null)
        {
            //direct To Home Page here

            logIntoHomePage();
        }

    }

    private void loginAcc(String useremail, String userpassword, FirebaseAuth auth){
        if(useremail.isEmpty() || userpassword.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please fill in all the fields",Toast.LENGTH_LONG).show();
        }
        else{
            auth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Successfully Logged In",Toast.LENGTH_LONG).show();
                        logIntoHomePage();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void logIntoHomePage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }

    private void directToSignUpPage(){
        Intent intent = new Intent(this,SignupActivity.class);
//        intent.putExtra("nickname",getIntent().getStringExtra("nickname"));
//        intent.putExtra("teamsID",getIntent().getStringArrayExtra("teamsID"));
        startActivity(intent);
    }

    private void directToForgotPassword(){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }


}

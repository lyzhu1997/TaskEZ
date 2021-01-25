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
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button resetPasswordButton;
    private ImageButton backButton;
    private EditText userEmail;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        auth = FirebaseAuth.getInstance();

        userEmail = (EditText)findViewById(R.id.fgtPasswordEmail);
        resetPasswordButton = (Button)findViewById(R.id.fgtpassResetPasswordBttn);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(userEmail.getText().toString(),auth);
            }
        });

        backButton = (ImageButton)findViewById(R.id.fgtPassBackBttn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToLoginPage();
            }
        });
    }

    private void resetPassword(String userEmail, FirebaseAuth auth){
        if(userEmail.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter email id",Toast.LENGTH_LONG);
        }
        else{
            boolean check = checkEmail(userEmail);
            if(check){
                auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Reset link sent to email", Toast.LENGTH_SHORT).show();
                            directToLoginPage();
                        } else {
                            Toast.makeText(getApplicationContext(), "Unable to send reset mail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Please enter proper email id", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean checkEmail(String userEmail){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(userEmail).matches();
    }

    private void directToLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
package com.example.compile1.Collaboration;

import android.content.Intent;
import android.os.Bundle;

import com.example.compile1.Login.LoginActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.compile1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {
    private String userId;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseListAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            userId = mFirebaseUser.getEmail();
        }
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chat");
        DatabaseReference messagesRef = mFirebaseDatabaseReference.child("chat");
        SnapshotParser<ChatMessage> parser = new SnapshotParser<ChatMessage>() {
            @Override
            public ChatMessage parseSnapshot(DataSnapshot dataSnapshot) {
                ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                if (chatMessage != null) {
                    chatMessage.setId(dataSnapshot.getKey());
                }
                return chatMessage;
            }
        };

        ImageButton sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chat")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                userId)
                        );

                // Clear the input
                input.setText("");
            }
        });

        ListView listOfMessages = (ListView)findViewById(R.id.messages_list);

        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setLayout(R.layout.item_message)
                        .setQuery(messagesRef, parser)
                        .build();
        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage chatMessage, int position) {
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                // Get references to the views of message.xml
                if (chatMessage.getMessageUser().equals(userId)) {
                    messageText.setText(chatMessage.getMessageText());
                    messageUser.setText("You");
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            chatMessage.getMessageTime()));
                } else {
                    messageText.setText(chatMessage.getMessageText());
                    messageUser.setText(chatMessage.getMessageUser());
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            chatMessage.getMessageTime()));
                }
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
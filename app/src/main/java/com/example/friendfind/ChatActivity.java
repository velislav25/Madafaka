package com.example.friendfind;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.friendfind.domain.User;
import com.example.friendfind.domain.UserMessage;
import com.example.friendfind.fragments.friends.FriendAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String chatRoomID;
    String userid;
    User user;
    String uname;
    List<UserMessage> msgList;
    private RecyclerView chatRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        msgList = new ArrayList<>();

        final TextView textView = findViewById(R.id.msgText);
        Button okBtn = findViewById(R.id.okBtn);

        chatRecyclerView = (RecyclerView) findViewById(R.id.msgRecycler);

        chatRecyclerView.setNestedScrollingEnabled(false);
        chatRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        chatRecyclerView.setLayoutManager(mLayoutManager);
        chatAdapter = new ChatAdapter(msgList);
        chatRecyclerView.setAdapter(chatAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userid = getIntent().getStringExtra("userid");
        DatabaseReference q = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    user = new User(userid);
                    String email = dataSnapshot.child("email").getValue().toString();
                    int index = email.indexOf('@');
                    uname = email.substring(0, index);
                    user.setEmail(email);
                    getSupportActionBar().setTitle("Chat with" + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chat").child(uid).child("chat").child(userid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //get chat id
                    chatRoomID = dataSnapshot.getValue().toString();
                    getMessages();
                } else {
                    //create new
                    DatabaseReference push = FirebaseDatabase.getInstance().getReference("chatrooms").push();
                    chatRoomID = push.getKey();
                    FirebaseDatabase.getInstance().getReference("chat").child(uid).child("chat").child(userid).setValue(chatRoomID);
                    FirebaseDatabase.getInstance().getReference("chat").child(userid).child("chat").child(uid).setValue(chatRoomID);
                    getMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView.getText() != null && textView.getText().toString() != "") {
                    String text = textView.getText().toString();
                    DatabaseReference push = FirebaseDatabase.getInstance().getReference("chatrooms").child(chatRoomID).child("messages").push();
                    String key = push.getKey();
                    FirebaseDatabase.getInstance().getReference("chatrooms").child(chatRoomID).child("messages").child(key).child("user").setValue(uname);
                    FirebaseDatabase.getInstance().getReference("chatrooms").child(chatRoomID).child("messages").child(key).child("text").setValue(text);
                    textView.setText("");
                }
            }
        });
    }

    private void getMessages() {
        DatabaseReference messages = FirebaseDatabase.getInstance().getReference("chatrooms").child(chatRoomID).child("messages");
        messages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("user").exists() && dataSnapshot.child("text").exists()) {
                    String user = dataSnapshot.child("user").getValue().toString();
                    String text = dataSnapshot.child("text").getValue().toString();
                    msgList.add(new UserMessage(user, text));
                    chatAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("user").exists() && dataSnapshot.child("text").exists()) {
                    String user = dataSnapshot.child("user").getValue().toString();
                    String text = dataSnapshot.child("text").getValue().toString();
                    msgList.add(new UserMessage(user, text));
                    chatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
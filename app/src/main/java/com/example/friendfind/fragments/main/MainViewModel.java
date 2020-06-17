package com.example.friendfind.fragments.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.friendfind.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainViewModel extends ViewModel {

    // TODO: Implement the ViewModel
    public List<User> getUsers(){
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        DatabaseReference friends = instance.getReference(myId).child("friends");

        friends.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapuser : dataSnapshot.getChildren()) {
                    User user = snapuser.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return null;
    }
}
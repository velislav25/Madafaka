package com.example.friendfind.fragments.invite;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteViewModel extends ViewModel {
    boolean exist;

    public Boolean checkUserExit(final String id) {
        exist = false;
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    String s = user.getValue().toString();
                    if (s.toLowerCase() == id.toLowerCase()) {
                        exist = true;
                    }else {
                        Log.d("","");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return exist;
    }
    // TODO: Implement the ViewModel
}
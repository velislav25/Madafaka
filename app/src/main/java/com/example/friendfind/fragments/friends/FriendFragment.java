package com.example.friendfind.fragments.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.friendfind.R;
import com.example.friendfind.domain.Follows;
import com.example.friendfind.domain.User;
import com.example.friendfind.fragments.invite.FindRCAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
    List<User> users = new ArrayList<>();
    private RecyclerView friendRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FriendAdapter  friendAdapter;

    public FriendFragment() {
        // Required empty public constructor
    }


    public static FriendFragment newInstance() {
        return new FriendFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Follows.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        friendRecyclerView = (RecyclerView) view.findViewById(R.id.friendlist);
        friendRecyclerView.setNestedScrollingEnabled(false);
        friendRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        friendRecyclerView.setLayoutManager(mLayoutManager);
        friendAdapter = new FriendAdapter(users,getContext());
        friendRecyclerView.setAdapter(friendAdapter);

        getUsers();


        return view;
    }

    private void getUsers() {

        DatabaseReference usersFB = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersFB.orderByChild("email");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Log.d("TAG", "onChildAdded: ");
                String email = "";
                String uid = dataSnapshot.getRef().getKey();
                if(dataSnapshot.child("email").getValue() != null){
                    email = dataSnapshot.child("email").getValue().toString();
                }
                if(!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    if(Follows.list.contains(uid)) {
                        User user = new User(email, uid);
                        users.add(user);
                        friendAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("TAG", "onChildChanged: ");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
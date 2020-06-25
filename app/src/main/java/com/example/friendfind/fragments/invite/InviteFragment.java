package com.example.friendfind.fragments.invite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.friendfind.R;
import com.example.friendfind.domain.Follows;
import com.example.friendfind.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class InviteFragment extends Fragment {

    private TextView invUserTextView;
    private Button searchBtn;
    private RecyclerView findRCview;
    private RecyclerView.LayoutManager mLayoutManager;
    private FindRCAdapter findRCAdapter;

    private ArrayList<User> FirebaseList = new ArrayList<>();

    public static InviteFragment newInstance() {
        return new InviteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_fragment, container, false);
        invUserTextView = (EditText) view.findViewById(R.id.findInput);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        findRCview = (RecyclerView) view.findViewById(R.id.FindRCview);

        findRCview.setNestedScrollingEnabled(false);
        findRCview.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        findRCview.setLayoutManager(mLayoutManager);
        findRCAdapter = new FindRCAdapter(getUsers(), getContext());
        findRCview.setAdapter(findRCAdapter);
        return view;
    }

    private List<User> getUsers() {
            return FirebaseList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Follows follows = new Follows();
        follows.getFollowedUsers();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clean();
                findUsers();
            }
        });
    }

    private void findUsers() {
        DatabaseReference usersFB = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersFB.orderByChild("email").startAt(invUserTextView.getText().toString()).endAt(invUserTextView.getText().toString() + "\uf8ff");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String email = "";
                String uid = dataSnapshot.getRef().getKey();
                if(dataSnapshot.child("email").getValue() != null){
                    email = dataSnapshot.child("email").getValue().toString();
                }
                if(!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    User user = new User(email, uid);
                    FirebaseList.add(user);
                    findRCAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    private void clean(){
        int size = this.FirebaseList.size();
        this.FirebaseList.clear();
        findRCAdapter.notifyItemRangeChanged(0,size);
    }

}
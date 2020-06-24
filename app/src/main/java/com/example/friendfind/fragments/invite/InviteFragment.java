package com.example.friendfind.fragments.invite;

import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendfind.R;
import com.example.friendfind.domain.User;

import java.util.ArrayList;
import java.util.List;

public class InviteFragment extends Fragment {

    private TextView invUserTextView;
    private Button inviteBtn;
    private RecyclerView findRCview;
    private RecyclerView.LayoutManager mLayoutManager;
    private FindRCAdapter findRCAdapter;

    public static InviteFragment newInstance() {
        return new InviteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_fragment, container, false);
        invUserTextView = (EditText) view.findViewById(R.id.findInput);
        inviteBtn = (Button) view.findViewById(R.id.searchBtn);
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
        List<User> users = new ArrayList<>();
        users.add(new User("email@com.com"));
        return users;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

}
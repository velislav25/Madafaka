package com.example.friendfind.fragments.invite;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendfind.R;

public class InviteFragment extends Fragment {

    private InviteViewModel mViewModel;
    private TextView invUserTextView;
    private Button inviteBtn;

    public static InviteFragment newInstance() {
        return new InviteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invite_fragment, container, false);
        invUserTextView = (TextView) view.findViewById(R.id.editTextTextInviteUser);
        inviteBtn = (Button) view.findViewById(R.id.invBtn);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InviteViewModel.class);
        // TODO: Use the ViewModel

        //Invite user on submit
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check is user exit
                if(!mViewModel.checkUserExit(invUserTextView.getText().toString())){
                    return;
                }
                //Check is already invited
                Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();

                //Invite

            }
        });

    }

}
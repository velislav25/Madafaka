package com.example.friendfind.fragments.invite;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.R;

public class FindRCHolder extends RecyclerView.ViewHolder {

    public TextView mEmail;
    public Button mFollow;

    public FindRCHolder(@NonNull View itemView) {
        super(itemView);
        mEmail = (TextView) itemView.findViewById(R.id.emailText);
        mFollow = (Button) itemView.findViewById(R.id.follow);

    }
}

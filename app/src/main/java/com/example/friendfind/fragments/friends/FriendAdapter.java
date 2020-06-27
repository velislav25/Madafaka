package com.example.friendfind.fragments.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.R;
import com.example.friendfind.domain.User;
import com.example.friendfind.fragments.invite.FindRCHolder;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

    private List<User> userList;

    public FriendAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, null, false);
        FriendHolder friendHolder = new FriendHolder(view);
        return friendHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class FriendHolder extends RecyclerView.ViewHolder {

        public TextView mEmail;


        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            mEmail = (TextView) itemView.findViewById(R.id.fiendEmail);

        }
    }

}


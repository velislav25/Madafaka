package com.example.friendfind.fragments.friends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.ChatActivity;
import com.example.friendfind.R;
import com.example.friendfind.domain.User;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

    private List<User> userList;
    private Context context;

    public FriendAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
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
        holder.mEmail.setText(userList.get(position).getEmail());
        final String userid = userList.get(holder.getLayoutPosition()).getUid();
        holder.mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("userid", userid);
                context.startActivity(intent);
            }
        });
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


package com.example.friendfind.fragments.invite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.R;
import com.example.friendfind.domain.Follows;
import com.example.friendfind.domain.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FindRCAdapter extends RecyclerView.Adapter<FindRCHolder> {
    private List<User> userList;
    private Context context;

    public FindRCAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public FindRCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_find_item, null, false);
        FindRCHolder findRCHolder = new FindRCHolder(view);
        return findRCHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FindRCHolder holder, final int position) {
        holder.mEmail.setText(userList.get(position).getEmail());

        if(Follows.list.contains(userList.get(holder.getLayoutPosition()).getUid())){
            holder.mFollow.setText("Followed");
        }else {
            holder.mFollow.setText("Follow");
        }

        holder.mFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sub

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(holder.mFollow.getText().equals("Follow")){
                    holder.mFollow.setText("Followed");
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("follower").child(userList.get(holder.getLayoutPosition()).getUid()).setValue(true);
                }else {
                    holder.mFollow.setText("Follow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("follower").child(userList.get(holder.getLayoutPosition()).getUid()).removeValue();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

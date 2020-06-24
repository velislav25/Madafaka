package com.example.friendfind.fragments.invite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.R;
import com.example.friendfind.domain.User;

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
    public void onBindViewHolder(@NonNull FindRCHolder holder, int position) {
        holder.mEmail.setText(userList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

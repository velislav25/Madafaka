package com.example.friendfind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendfind.domain.User;
import com.example.friendfind.domain.UserMessage;
import com.example.friendfind.fragments.friends.FriendAdapter;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    private List<UserMessage> msgList;
    private Context context;

    public ChatAdapter(List<UserMessage> userList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    public ChatAdapter(List<UserMessage> msgList) {
        this.msgList = msgList;

    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item, null, false);
        ChatAdapter.ChatHolder holder = new ChatAdapter.ChatHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        holder.mUser.setText(msgList.get(position).getUser());
        holder.mText.setText(msgList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder {

        public TextView mUser;
        public TextView mText;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);

            mUser = (TextView) itemView.findViewById(R.id.userName);
            mText = (TextView) itemView.findViewById(R.id.msgView);

        }
    }
}

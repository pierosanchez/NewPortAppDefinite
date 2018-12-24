package com.newport.app.ui.chats.messages;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newport.app.R;
import com.newport.app.data.models.response.ChatUserChatResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatMessagesViewHolder> {

    private List<ChatUserChatResponse.UserChatMessagesBean> userChatMessagesList;

    ChatMessagesAdapter() {
        userChatMessagesList = new ArrayList<>();
    }

    public void addData(List<ChatUserChatResponse.UserChatMessagesBean> messagesList) {
        this.userChatMessagesList.clear();
        Collections.reverse(messagesList);
        this.userChatMessagesList.addAll(messagesList);
        notifyDataSetChanged();
    }

    @Override
    public ChatMessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatMessagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatMessagesViewHolder holder, int position) {

        if (userChatMessagesList.get(position).getMessage() != null) {
            String userMessager = userChatMessagesList.get(position).getMessage();
            String userName = userChatMessagesList.get(position).getNOMBRE();
            holder.tvMessage.setText(userMessager);
            holder.tvUserMessager.setText(userName);
        } else {
            Log.d("messageAdapter", "it should not crash");
        }
    }

    @Override
    public int getItemCount() {
        return userChatMessagesList.size();
    }

    class ChatMessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserMessager;
        private TextView tvMessage;

        ChatMessagesViewHolder(View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvUserMessager = itemView.findViewById(R.id.tvUserMessager);

        }
    }
}

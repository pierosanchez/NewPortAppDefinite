package com.newport.app.ui.chats.messages;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    public int getItemViewType(int position) {
        int result = 0;
        if (userChatMessagesList.get(position).getUser_id().equals(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext()))) {
            result = 0;
        } else {
            result = 1;
        }
        return result;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViewMyMessage = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_message, parent, false);
        View itemViewTheirMessage = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_their_message, parent, false);
        switch (viewType) {
            case 0:
                return new ChatMessagesViewHolder(itemViewMyMessage);
            case 1:
                return new ChatTheirMessagesViewHolder(itemViewTheirMessage);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case 0:
                ChatMessagesViewHolder viewHolderMyMessage = (ChatMessagesViewHolder) holder;
                if (userChatMessagesList.get(position).getMessage() != null) {
                    String userMessager = userChatMessagesList.get(position).getMessage();
                    viewHolderMyMessage.message_body.setText(userMessager);
                } else {
                    Log.d("messageAdapter", "it should not crash");
                }
                break;
            case 1:
                ChatTheirMessagesViewHolder viewHolderTheirMessage = (ChatTheirMessagesViewHolder) holder;
                if (userChatMessagesList.get(position).getMessage() != null) {
                    String userMessager = userChatMessagesList.get(position).getMessage();
                    String userName = userChatMessagesList.get(position).getNOMBRE();
                    viewHolderTheirMessage.message_body.setText(userMessager);
                    viewHolderTheirMessage.name.setText(userName);
                } else {
                    Log.d("messageAdapter", "it should not crash");
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userChatMessagesList.size();
    }

    class ChatMessagesViewHolder extends RecyclerView.ViewHolder {

        //private TextView tvUserMessager;
        private TextView message_body;

        ChatMessagesViewHolder(View itemView) {
            super(itemView);

            message_body = itemView.findViewById(R.id.message_body);
            //tvUserMessager = itemView.findViewById(R.id.tvUserMessager);

        }
    }

    class ChatTheirMessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView message_body;

        ChatTheirMessagesViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            message_body = itemView.findViewById(R.id.message_body);

        }
    }
}

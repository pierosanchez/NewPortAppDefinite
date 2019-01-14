package com.newport.app.ui.chats.channels;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newport.app.R;
import com.newport.app.data.models.response.ChatChannelResponse;

import java.util.ArrayList;
import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChatChannelsViewHolder> {

    private List<ChatChannelResponse> newsResponseList;

    public interface OnClickChatChannelListener { void onChatChannelItemClick(int idChannel); }
    private OnClickChatChannelListener listener;
    void setOnChatChannelClickListener(OnClickChatChannelListener listener) { this.listener = listener; }

    ChannelsAdapter() {
        newsResponseList = new ArrayList<>();
    }

    public void addData(List<ChatChannelResponse> newsResponseList) {
        //this.newsResponseList = newsResponseList;
        this.newsResponseList.clear();
        this.newsResponseList.addAll(newsResponseList);
        notifyDataSetChanged();
    }

    @Override
    public ChatChannelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_channel, parent, false);
        return new ChatChannelsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatChannelsViewHolder holder, int position) {
        if (newsResponseList.get(position).getMarcacion() != null) {
            holder.tvChatChannelOwner.setText(newsResponseList.get(position).getMarcacion().getNOMBRE());
            holder.tvChatChannelName.setText(newsResponseList.get(position).getChannel_name());
        } else {
            holder.tvChatChannelOwner.setText(newsResponseList.get(position).getLast_message());
            holder.tvChatChannelName.setText(newsResponseList.get(position).getUser_initializer_chat_name());
        }
    }

    @Override
    public int getItemCount() {
        return newsResponseList.size();
    }

    class ChatChannelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivChannelPhoto;
        private TextView tvChatChannelName;
        private TextView tvChatChannelOwner;

        ChatChannelsViewHolder(View itemView) {
            super(itemView);

            ivChannelPhoto = itemView.findViewById(R.id.ivChannelPhoto);
            tvChatChannelName = itemView.findViewById(R.id.tvChatChannelName);
            tvChatChannelOwner = itemView.findViewById(R.id.tvChatChannelOwner);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onChatChannelItemClick(newsResponseList.get(getAdapterPosition()).getId());
            }
        }
    }
}

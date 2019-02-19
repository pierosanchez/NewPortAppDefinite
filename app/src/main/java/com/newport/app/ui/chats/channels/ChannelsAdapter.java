package com.newport.app.ui.chats.channels;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.ApplicationSQLiteDatabase.NewportAppBD;
import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.util.PreferencesHeper;

import java.util.ArrayList;
import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChatChannelsViewHolder> {

    private List<ChatChannelResponse> newsResponseList;

    private NewportAppBD db = new NewportAppBD(NewPortApplication.getAppContext().getApplicationContext());

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
            /*if (newsResponseList.get(position).getStatus_message() == 0 &&
                    !newsResponseList.get(position).getUser_id().equals(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))) {
                holder.ivNewMessages.setImageResource(R.drawable.ic_new_message);
            }
            Log.d("user_init_id", newsResponseList.get(position).getUser_id());*/

            holder.tvChatChannelOwner.setText(newsResponseList.get(position).getMarcacion().getNOMBRE());
            holder.tvChatChannelName.setText(newsResponseList.get(position).getChannel_name());
        } else {
            /*if (newsResponseList.get(position).getStatus_message() == 0 &&
                    !newsResponseList.get(position).getUser_id().equals(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))) {
                holder.ivNewMessages.setImageResource(R.drawable.ic_new_message);
                Log.d("owner_id", newsResponseList.get(position).getUser_id());
            }
            Log.d("owner_id", newsResponseList.get(position).getUser_id());*/

            holder.tvChatChannelOwner.setText(newsResponseList.get(position).getLast_message());
            holder.tvChatChannelName.setText(newsResponseList.get(position).getUser_initializer_chat_name());
        }
    }

    public void clearAdapter() {
        newsResponseList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsResponseList.size();
    }

    class ChatChannelsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivChannelPhoto;
        private ImageView ivNewMessages;
        private TextView tvChatChannelName;
        private TextView tvChatChannelOwner;

        ChatChannelsViewHolder(View itemView) {
            super(itemView);

            ivChannelPhoto = itemView.findViewById(R.id.ivChannelPhoto);
            ivNewMessages = itemView.findViewById(R.id.ivNewMessages);
            tvChatChannelName = itemView.findViewById(R.id.tvChatChannelName);
            tvChatChannelOwner = itemView.findViewById(R.id.tvChatChannelOwner);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onChatChannelItemClick(newsResponseList.get(getAdapterPosition()).getId());

                if (newsResponseList.get(getAdapterPosition()).getMarcacion() != null) {
                    Log.d("channelIdUser", String.valueOf(newsResponseList.get(getAdapterPosition()).getId()));

                    PreferencesHeper.setKeyChannelId(NewPortApplication.getAppContext().getApplicationContext(), newsResponseList.get(getAdapterPosition()).getId());
                    PreferencesHeper.setKeyChatOtherUserId(NewPortApplication.getAppContext().getApplicationContext(), newsResponseList.get(getAdapterPosition()).getChannel_owner_id());
                    PreferencesHeper.setKeyChatId(NewPortApplication.getAppContext().getApplicationContext(), String.valueOf(newsResponseList.get(getAdapterPosition()).getChat_id()));
                    PreferencesHeper.setKeyChannelName(NewPortApplication.getAppContext().getApplicationContext(), tvChatChannelName.getText().toString());
                } else {
                    Log.d("channelIdOwner", String.valueOf(newsResponseList.get(getAdapterPosition()).getChannel_id()));

                    PreferencesHeper.setKeyChannelId(NewPortApplication.getAppContext().getApplicationContext(), newsResponseList.get(getAdapterPosition()).getChannel_id());
                    PreferencesHeper.setKeyChatOtherUserId(NewPortApplication.getAppContext().getApplicationContext(), newsResponseList.get(getAdapterPosition()).getUser_initializer_chat_id());
                    PreferencesHeper.setKeyChatId(NewPortApplication.getAppContext().getApplicationContext(), String.valueOf(newsResponseList.get(getAdapterPosition()).getId()));
                    PreferencesHeper.setKeyChannelName(NewPortApplication.getAppContext().getApplicationContext(), tvChatChannelName.getText().toString());
                }
            }
        }
    }
}

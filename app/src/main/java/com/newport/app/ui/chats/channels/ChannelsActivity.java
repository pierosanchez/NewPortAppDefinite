package com.newport.app.ui.chats.channels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.chats.messages.ChatActivity;
import com.newport.app.util.PreferencesHeper;

import java.util.List;

public class ChannelsActivity extends BaseActivity implements ChannelsContract.View, ChannelsAdapter.OnClickChatChannelListener {

    private RecyclerView rvChatChannels;
    private RelativeLayout rltProgress;

    private ChannelsPresenter channelsPresenter;
    private ChannelsAdapter channelsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        init();
    }

    private void init() {
        rltProgress = findViewById(R.id.rltProgress);
        rvChatChannels = findViewById(R.id.rvChatChannels);

        channelsPresenter = new ChannelsPresenter();
        channelsPresenter.attachedView(this);

        channelsAdapter = new ChannelsAdapter();
        channelsAdapter.setOnChatChannelClickListener(this);
        rvChatChannels.setAdapter(channelsAdapter);

        channelsPresenter.getCHannels();
    }

    @Override
    public void onChatChannelItemClick(int idChannel) {
        PreferencesHeper.setKeyChannelId(NewPortApplication.getAppContext().getApplicationContext(), idChannel);
        Log.d("channelid", " " + PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext()));
        startActivity(new Intent(ChannelsActivity.this, ChatActivity.class));
    }

    @Override
    public void showLoading() {
        rltProgress.setVisibility(View.VISIBLE);
        rvChatChannels.setVisibility(View.GONE);
    }

    @Override
    public void HideLoading() {
        rltProgress.setVisibility(View.GONE);
        rvChatChannels.setVisibility(View.VISIBLE);
    }

    @Override
    public void showChannels(List<ChatChannelResponse> channelResponseList) {
        channelsAdapter.addData(channelResponseList);
    }

    @Override
    public void showChannelsError(String error) {
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();

    }
}

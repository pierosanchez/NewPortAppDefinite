package com.newport.app.ui.chats.channels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.response.ChatChannelResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.chats.messages.ChatActivity;
import com.newport.app.ui.chats.messages.ChatIteractor;
import com.newport.app.util.PreferencesHeper;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChannelsActivity extends BaseActivity implements ChannelsContract.View, ChannelsAdapter.OnClickChatChannelListener {

    private RecyclerView rvChatChannels;
    private RelativeLayout rltProgress;

    private ChannelsPresenter channelsPresenter;
    private ChannelsAdapter channelsAdapter;

    private FirebaseAnalytics mFirebaseAnalytics;

    private Timer timer;

    private boolean chatsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
        init();
    }

    private void init() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        rltProgress = findViewById(R.id.rltProgress);
        rvChatChannels = findViewById(R.id.rvChatChannels);

        channelsPresenter = new ChannelsPresenter();
        channelsPresenter.attachedView(this);

        channelsAdapter = new ChannelsAdapter();
        channelsAdapter.setOnChatChannelClickListener(this);
        rvChatChannels.setAdapter(channelsAdapter);

        channelsPresenter.getCHannels();

        channelsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                channelsAdapter.notifyItemRangeChanged(positionStart, itemCount);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        chatsLoaded = true;
        channelsAdapter.clearAdapter();
        channelsPresenter.getCHannels();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        chatsLoaded = true;
        channelsAdapter.clearAdapter();
        channelsPresenter.getCHannels();
    }

    @Override
    public void onChatChannelItemClick(int idChannel) {
        startActivity(new Intent(ChannelsActivity.this, ChatActivity.class));
        finish();
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
        channelsAdapter.clearAdapter();
        for (int i = 0; i < channelResponseList.size(); i++) {
            if (channelResponseList.get(i).getMarcacion() != null) {
                String[] nombreArray = channelResponseList.get(i).getMarcacion().getNOMBRE().split(" ");
                channelResponseList.get(i).getMarcacion().setNOMBRE(nombreArray[2] + " " + nombreArray[0] + " " + nombreArray[1]);
            }
        }
        channelsAdapter.addData(channelResponseList);
        new GettingChatsConstantly().execute();
    }

    @Override
    public void showChannelsError(String error) {
        if (error.equals(NewPortApplication.getAppContext().getString(R.string.no_chats_response))) {
            new GettingChatsConstantly().execute();
        }
        Log.d("error", error);
        //Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();

    }

    class GettingChatsConstantly extends AsyncTask<String, Void, Void> implements ChannelsContract.CallBack {

        @Override
        protected Void doInBackground(String... strings) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (chatsLoaded) {
                        try {
                            ChannelsIteractor.getChannels(GettingChatsConstantly.this);
                        } catch (Exception e) {
                            Crashlytics.logException(e);
                        }
                    } else {
                        this.cancel();
                    }
                }
            }, 5000, 5000);
            return null;
        }

        @Override
        public void getChannelsSuccess(List<ChatChannelResponse> channelResponseList) {
            for (int i = 0; i < channelResponseList.size(); i++) {
                if (channelResponseList.get(i).getMarcacion() != null) {
                    String[] nombreArray = channelResponseList.get(i).getMarcacion().getNOMBRE().split(" ");
                    channelResponseList.get(i).getMarcacion().setNOMBRE(nombreArray[2] + " " + nombreArray[0] + " " + nombreArray[1]);
                }
            }
            channelsAdapter.addData(channelResponseList);
            chatsLoaded = true;
        }

        @Override
        public void getChannelsError(String error) {

        }

        @Override
        public void getChannelsFailure(String failure) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatsLoaded = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatsLoaded = false;
    }
}

package com.newport.app.ui.chats.messages;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.response.ChatSendMessageResponse;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.util.PreferencesHeper;

import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends BaseActivity implements ChatContract.View, ChatContract.ViewSendMessage, View.OnClickListener {

    private TextView tvChatUserName;
    private EditText txtMessage;
    private Button btnSendMessage;

    private boolean messagesLoaded = false;

    private RelativeLayout rltProgress;
    private RecyclerView rvChatMessages;

    private ChatPresenter chatPresenter;
    private ChatMessagesAdapter chatMessagesAdapter;

    private ChatSendMessagePresenter chatSendMessagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        btnSendMessage = findViewById(R.id.btnSendMessage);
        tvChatUserName = findViewById(R.id.tvChatUserName);
        txtMessage = findViewById(R.id.txtMessage);

        rltProgress = findViewById(R.id.rltProgress);
        rvChatMessages = findViewById(R.id.rvChatMessages);

        chatPresenter = new ChatPresenter();
        chatPresenter.attachedView(this);

        chatSendMessagePresenter = new ChatSendMessagePresenter();
        chatSendMessagePresenter.attachedView(this);

        chatMessagesAdapter = new ChatMessagesAdapter();
        rvChatMessages.setAdapter(chatMessagesAdapter);

        btnSendMessage.setOnClickListener(this);

        chatPresenter.getChatUserChat();
    }

    @Override
    public void showLoading() {
        rltProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rltProgress.setVisibility(View.GONE);
    }

    @Override
    public void showChatSuccess(ChatUserChatResponse chatUserChatResponse) {
        chatMessagesAdapter.addData(chatUserChatResponse.getChat_messages());
        rvChatMessages.scrollToPosition(chatUserChatResponse.getChat_messages().size() - 1);
        messagesLoaded = true;
        new GettingMessagesConstantly().execute();
    }

    @Override
    public void showChatsError(String error) {
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendMessageSuccess(ChatSendMessageResponse chatSendMessageResponse) {
        if (chatSendMessageResponse.getResponse().equals("success")) {
            chatPresenter.getChatUserChat();
            txtMessage.setText("");
        }
    }

    @Override
    public void showSendMessageError(String error) {
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendMessage) {
            if (txtMessage.getText().toString().equals("")) {
                Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), "Ingrese un mensaje, para enviar.", Toast.LENGTH_SHORT).show();
            } else {
                ChatRequest chat = new ChatRequest();
                chat.setChatid(Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
                chat.setMessage(txtMessage.getText().toString());
                chat.setUserId(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()));
                chatSendMessagePresenter.sendMessage(chat);
            }
        }
    }

    class GettingMessagesConstantly extends AsyncTask<String, Void, String> implements ChatContract.CallBack{

        @Override
        protected String doInBackground(String... strings) {

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (messagesLoaded = true) {
                        ChatIteractor.getChatUserChat(GettingMessagesConstantly.this);
                    }
                }
            }, 5000, 5000);
            return null;
        }

        @Override
        public void getChatUserChatSuccess(ChatUserChatResponse chatUserChatResponse) {
            chatMessagesAdapter.addData(chatUserChatResponse.getChat_messages());
            messagesLoaded = true;
        }

        @Override
        public void getChatUserChatError(String error) {
            Log.d("showmessagestimer", "error " + error);
        }

        @Override
        public void getChatUserChatFailure(String failure) {
            Log.d("showmessagestimer", "failure " + failure);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        new GettingMessagesConstantly().cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        new GettingMessagesConstantly().cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatPresenter.getChatUserChat();
    }
}

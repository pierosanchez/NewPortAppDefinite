package com.newport.app.ui.chats.messages;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.request.AttentionCalificationRequest;
import com.newport.app.data.models.request.ChatRequest;
import com.newport.app.data.models.response.ChatSendMessageResponse;
import com.newport.app.data.models.response.ChatUserChatResponse;
import com.newport.app.data.models.response.GenericResponse;
import com.newport.app.ui.BaseActivity;
import com.newport.app.ui.chats.channels.ChannelsActivity;
import com.newport.app.util.PreferencesHeper;

import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends BaseActivity implements ChatContract.View, ChatContract.ViewSendMessage,
        ChatContract.ViewSetMessageTerminated, ChatContract.ViewAttentionCalification, View.OnClickListener {

    private TextView tvChatUserName;
    private EditText txtMessage;
    private Button btnSendMessage;
    private Button btnFinishConversation;
    private CheckBox btnStar1;
    private CheckBox btnStar2;

    private AlertDialog dialog;

    private boolean messagesLoaded = false;
    private boolean isDialogShowed = false;
    private boolean isRRHHUser = false;

    private RelativeLayout rltProgress;
    private RecyclerView rvChatMessages;

    private ChatPresenter chatPresenter;
    private ChatMessagesAdapter chatMessagesAdapter;

    private ChatSendMessagePresenter chatSendMessagePresenter;
    private ChatMessageTerminatedPresenter chatMessageTerminatedPresenter;
    private ChatMessageAttentionCalificationPresenter chatMessageAttentionCalificationPresenter;

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

        btnFinishConversation = findViewById(R.id.btnFinishConversation);

        chatPresenter = new ChatPresenter();
        chatPresenter.attachedView(this);

        chatSendMessagePresenter = new ChatSendMessagePresenter();
        chatSendMessagePresenter.attachedView(this);
        chatMessageTerminatedPresenter = new ChatMessageTerminatedPresenter();
        chatMessageTerminatedPresenter.attachedView(this);
        chatMessageAttentionCalificationPresenter = new ChatMessageAttentionCalificationPresenter();
        chatMessageAttentionCalificationPresenter.attachedView(this);

        chatMessagesAdapter = new ChatMessagesAdapter();
        rvChatMessages.setAdapter(chatMessagesAdapter);

        btnSendMessage.setOnClickListener(this);
        btnFinishConversation.setOnClickListener(this);
        tvChatUserName.setText(PreferencesHeper.getKeyChannelName(NewPortApplication.getAppContext().getApplicationContext()));

        chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));

        chatMessagesAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                rvChatMessages.scrollToPosition(chatMessagesAdapter.getItemCount());
            }
        });
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
        if (chatUserChatResponse != null) {
            chatMessagesAdapter.addData(chatUserChatResponse.getChat_messages());
            rvChatMessages.scrollToPosition(chatMessagesAdapter.getItemCount());

            if (chatUserChatResponse.getUser_initializer_chat_id() != null) {
                if (chatUserChatResponse.getUser_initializer_chat_id().equals(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()))) {
                    btnFinishConversation.setVisibility(View.GONE);
                }
            } else if (chatUserChatResponse.getUser_initializer_chat_id() == null) {
                btnFinishConversation.setVisibility(View.GONE);
            }

            if (btnFinishConversation.getVisibility() == View.VISIBLE) {
                if (chatUserChatResponse.getNOMBRE() != null) {
                    tvChatUserName.setText(chatUserChatResponse.getNOMBRE());
                    isRRHHUser = true;
                } else {
                    tvChatUserName.setText(PreferencesHeper.getKeyChannelName(NewPortApplication.getAppContext().getApplicationContext()));
                    isRRHHUser = false;
                }
            }

            if (chatUserChatResponse.getStatus_chat() == 2) {
                if (isDialogShowed == false) {
                    isDialogShowed = true;
                    showAttentionCalification();
                }
            } else {
                messagesLoaded = true;
                new GettingMessagesConstantly().execute();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        messagesLoaded = false;
        new ChatActivity.GettingMessagesConstantly().execute().cancel(true);
        startActivity(new Intent(NewPortApplication.getAppContext().getApplicationContext(), ChannelsActivity.class));
        finish();
    }

    private void showAttentionCalification() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View mView = this.getLayoutInflater().inflate(R.layout.dialog_chat_attention_calification, null);
        Button btnSendCalification = mView.findViewById(R.id.btnSendCalification);
        final int[] tagCount1 = {0};
        final int[] tagCount2 = {0};

        View.OnClickListener starsListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag1 = Integer.valueOf((String) v.getTag());
                tagCount1[0] = tag1;
                for (int i = 1; i <= tag1; i++) {
                    btnStar1 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
                    btnStar1.setChecked(true);
                }

                for (int i = tag1 + 1; i <= 5; i++) {
                    btnStar1 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
                    btnStar1.setChecked(false);
                }
            }
        };

        View.OnClickListener starsListener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag2 = Integer.valueOf((String) v.getTag());
                tagCount2[0] = tag2;
                for (int i = 6; i <= tag2; i++) {
                    btnStar2 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
                    btnStar2.setChecked(true);
                }

                for (int i = tag2 + 1; i <= 10; i++) {
                    btnStar2 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
                    btnStar2.setChecked(false);
                }
            }
        };

        for (int i = 1; i <= 5; i++) {
            btnStar1 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
            btnStar1.setOnClickListener(starsListener1);
        }

        for (int i = 6; i <= 10; i++) {
            btnStar2 = (CheckBox) mView.findViewWithTag(String.valueOf(i));
            btnStar2.setOnClickListener(starsListener2);
        }

        btnSendCalification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttentionCalificationRequest attentionCalificationRequest = new AttentionCalificationRequest();
                attentionCalificationRequest.setCalification(tagCount1[0]);
                attentionCalificationRequest.setCalification_solution_problem(tagCount2[0]);
                attentionCalificationRequest.setChat_id(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())));
                chatMessageAttentionCalificationPresenter.setAttionCalification(attentionCalificationRequest);
            }
        });

        builder.setView(mView);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showChatsError(String error) {
        messagesLoaded = false;
        chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSendMessageSuccess(ChatSendMessageResponse chatSendMessageResponse) {
        btnSendMessage.setEnabled(true);
        if (chatSendMessageResponse.getResponse().equals("success")) {
            if (chatSendMessageResponse.getStatus_chat() == 1) {
                if (isRRHHUser == false) {
                    Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), NewPortApplication.getAppContext().getString(R.string.default_response_message_chat_app), Toast.LENGTH_LONG).show();
                }
            }
            PreferencesHeper.setKeyChatId(NewPortApplication.getAppContext().getApplicationContext(), String.valueOf(chatSendMessageResponse.getChat_id()));
            chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                    Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
            txtMessage.setText("");
        }
    }

    @Override
    public void showSendMessageError(String error) {
        messagesLoaded = true;
        btnSendMessage.setEnabled(true);
        chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
        Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendMessage) {
            btnSendMessage.setEnabled(false);
            if (txtMessage.getText().toString().equals("")) {
                Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), "Ingrese un mensaje, para enviar.", Toast.LENGTH_SHORT).show();
            } else {
                ChatRequest chat = new ChatRequest();
                chat.setChannelId(Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
                chat.setMessage(txtMessage.getText().toString());
                chat.setUserId(PreferencesHeper.getSapCodeUser(NewPortApplication.getAppContext().getApplicationContext()));
                chat.setChatid(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())));
                chat.setUser_initializer_chat_id(PreferencesHeper.getKeyChatOtherUserId(NewPortApplication.getAppContext().getApplicationContext()));
                chatSendMessagePresenter.sendMessage(chat);
            }
        } else if (v.getId() == R.id.btnFinishConversation) {
            chatMessageTerminatedPresenter.setMessageTerminated(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())));
        }
    }

    @Override
    public void showSetMessageTerminatedSuccess(GenericResponse genericResponse) {
        if (genericResponse.getResponse().equals("success")){
            startActivity(new Intent(NewPortApplication.getAppContext().getApplicationContext(), ChannelsActivity.class));
            finish();
        }
    }

    @Override
    public void ShowSetMessageTerminatedError(String error) {
        Log.d("messageterminatederror", error);
    }

    @Override
    public void showAttentionCalificationSuccess(GenericResponse genericResponse) {
        if (genericResponse.getResponse().equals("success")){
            isDialogShowed = false;
            messagesLoaded = true;
            new GettingMessagesConstantly().execute();
            Toast.makeText(NewPortApplication.getAppContext().getApplicationContext(), "Muchas gracias por tu apoyo", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    public void showAttentionCalificationError(String error) {

    }

    class GettingMessagesConstantly extends AsyncTask<String, Void, String> implements ChatContract.CallBack {

        @Override
        protected String doInBackground(String... strings) {

            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (messagesLoaded) {
                        try {
                            ChatIteractor.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                                    Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())), GettingMessagesConstantly.this);
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
        public void getChatUserChatSuccess(ChatUserChatResponse chatUserChatResponse) {
            if (chatUserChatResponse != null) {
                if (chatUserChatResponse.getStatus_chat() == 2) {
                    if (isDialogShowed == false) {
                        isDialogShowed = true;
                        showAttentionCalification();
                    }
                }

                chatMessagesAdapter.addData(chatUserChatResponse.getChat_messages());
                if (chatUserChatResponse.getChat_messages() != null) {
                    rvChatMessages.scrollToPosition(chatUserChatResponse.getChat_messages().size());
                }
                messagesLoaded = true;
            }
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
        messagesLoaded = false;
        new ChatActivity.GettingMessagesConstantly().execute().cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        messagesLoaded = false;
        new ChatActivity.GettingMessagesConstantly().execute().cancel(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        messagesLoaded = true;
        chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        messagesLoaded = true;
        chatPresenter.getChatUserChat(Integer.parseInt(PreferencesHeper.getKeyChatId(NewPortApplication.getAppContext().getApplicationContext())),
                Integer.parseInt(PreferencesHeper.getKeyChannelId(NewPortApplication.getAppContext().getApplicationContext())));
    }
}

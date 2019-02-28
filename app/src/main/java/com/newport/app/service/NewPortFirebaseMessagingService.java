package com.newport.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.newport.app.NewPortApplication;
import com.newport.app.R;
import com.newport.app.data.models.ApplicationSQLiteDatabase.NewportAppBD;
import com.newport.app.util.PreferencesHeper;

/**
 * Created by tohure on 14/11/17.
 */

public class NewPortFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        Log.i("ACS", "NewPortFirebaseMessagingService.onMessageReceived(..."+remoteMessage.getNotification().getBody());
        String chat_id = "0";
        String time_sended_message = "";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //TODO: Revisar si se hace notificación personalizada
            //Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, "Message_data_payload: " + remoteMessage.getData().get("chat_id"));
            Log.d(TAG, "Message_data_payload: " + remoteMessage.getData().get("time_sended_message"));
            chat_id = remoteMessage.getData().get("chat_id");
            time_sended_message = remoteMessage.getData().get("time_sended_message");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            //TODO: Revisar si se obtiene más parámetros de la notificación estándar
            //Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            if (chat_id != null) {
                Log.d("chatDifiereNull", "entro!!!");
                Log.d("notifStatVal", String.valueOf(PreferencesHeper.getNotificationChatStatus(NewPortApplication.getAppContext().getApplicationContext())));
                if (!PreferencesHeper.getNotificationChatStatus(NewPortApplication.getAppContext().getApplicationContext())) {
                    Log.d("notifStatDiferNull", "entro!!!");
                    Log.d("notifStatDiferNullVal", String.valueOf(PreferencesHeper.getNotificationChatStatus(NewPortApplication.getAppContext().getApplicationContext())));
                    PreferencesHeper.setKeyChatIdNotification(NewPortApplication.getAppContext().getApplicationContext(), chat_id);
                    Log.d("notifStatDiferNullCid", chat_id);
                }
            }

            /*if (chat_id != null && time_sended_message!= null) {
                NewportAppBD newportAppBD = new NewportAppBD(NewPortApplication.getAppContext().getApplicationContext());
                newportAppBD.getWritableDatabase().execSQL("INSERT INTO Message(message_sended_time, chat_id) VALUES('" + time_sended_message + "', '" +  chat_id +  "')");
                newportAppBD.getWritableDatabase().close();
                newportAppBD.close();
            }*/

            String tittle = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            String click_action = remoteMessage.getNotification().getClickAction();
            Intent intent = new Intent(click_action);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notificationBuilder = new Notification.Builder(this);
            notificationBuilder.setContentTitle(tittle);
            notificationBuilder.setContentText(body);
            notificationBuilder.setSmallIcon(R.drawable.ic_notification);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setGroup("newportChat");
            notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.notify(0, notificationBuilder.build());
            }

            /*NotificationCompat.Builder summaryNotification = new NotificationCompat.Builder(this);
            summaryNotification.setSmallIcon(R.drawable.ic_notification);
            summaryNotification.setContentIntent(pendingIntent);
            summaryNotification.setStyle(new NotificationCompat.InboxStyle()
            .addLine(tittle + " " + body));
            summaryNotification.setGroup("NEWPORTAPPNOTIFICATIONS");
            summaryNotification.setGroupSummary(true);
            if (notificationManager != null) {
                notificationManager.notify(1, summaryNotification.build());
            }*/

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
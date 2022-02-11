package com.example.fcm2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        Log.d("5", "getNotification() : " + remoteMessage.getNotification().getTag());
//        Log.d("6", "getData : " + remoteMessage.getData());

        if (remoteMessage != null) {
            if (remoteMessage.getData() != null) {
                sendNotification(remoteMessage.getData());
            } else {
//                sendNotification(remoteMessage.getNotification(), remoteMessage.getData());
            }
        }

    }

    //백그라운드 고려 x
//    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = getNotificationBuilder(notificationManager, "channel id", "첫번째 채널");
//
//
//
//        if (data.get("pushGubun").equals("1")) {
//            builder.setContentTitle(notification.getTitle())
//                    .setContentText(notification.getBody())
//                    .setContentInfo(notification.getTag())
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true);
//
//            notificationManager.notify(notification.getTag(),1, builder.build());
//
//        } else if (data.get("pushGubun").equals("2")) {
//
//            String bigText = "20449-20489/com.example.fcm2 V/FA: Inactivity, disconnecting from the service";
//
//
//            builder.setContentTitle(notification.getTitle())
//                    .setContentText(bigText)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
//                    .setContentInfo(notification.getTag())
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true);
//
//            notificationManager.notify(2, builder.build());
//
//        }
//
//    }
    //백그라운드 고려 O
    private void sendNotification(Map<String, String> data) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = getNotificationBuilder(notificationManager, "channel id", "첫번째 채널");



        if (data.get("pushGubun").equals("1")) {
            builder.setContentTitle(data.get("title"))
                    .setContentText(data.get("body"))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notificationManager.notify(data.get("tag"),1, builder.build());

        } else if (data.get("pushGubun").equals("2")) {

            String bigText = "20449-20489/com.example.fcm2 V/FA: Inactivity, disconnecting from the service";


            builder.setContentTitle(data.get("title"))
                    .setContentText(bigText)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(bigText))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notificationManager.notify(2, builder.build());

        }

    }

//    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder builder = getNotificationBuilder(notificationManager, "channel id", "첫번째 채널");
//
//        builder.setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setContentInfo(notification.getTag())
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//
//
////        notificationManager.notify(notification.getTag(), 1234, builder.build());
//        notificationManager.notify(data.get("apple"), 1234, builder.build());
//    }

    protected  NotificationCompat.Builder getNotificationBuilder(NotificationManager notificationManager, String channelId, CharSequence channelName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            return builder;
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
            return builder;
        }
    }


//    private void sendNotification(RemoteMessage remoteMessage) {
//        String title = remoteMessage.getData().get("title");
//        String msg = remoteMessage.getData().get("message");
//
//        final String CHANNEL_ID = "ChID";
//        NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            final String CHANNEL_NAME = "ChName";
//            final String CHANNEL_DESCRIPTION = "ChDescription";
//            final int importance = NotificationManager.IMPORTANCE_HIGH;
//
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
//            mChannel.setDescription(CHANNEL_DESCRIPTION);
//            mChannel.enableLights(true);
//            mChannel.enableVibration(true);
//            mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
//            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            mManager.createNotificationChannel(mChannel);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
//        builder.setSmallIcon(R.drawable.ic_launcher_background);
//        builder.setAutoCancel(true);
//        builder.setDefaults(Notification.DEFAULT_ALL);
//        builder.setWhen(System.currentTimeMillis());
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentTitle(title);
//        builder.setContentText(msg);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//            builder.setContentTitle(title);
//            builder.setVibrate(new long[]{500, 500});
//        }
//        mManager.notify(0, builder.build());
//
//    }



    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}

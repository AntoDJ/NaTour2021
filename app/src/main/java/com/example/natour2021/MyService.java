package com.example.natour2021;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.*;

public class MyService extends FirebaseMessagingService{

    public void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.i("TOKEN", token);
                    }
                });
    }

    public void sendMessage (String token) {

        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        String title = remoteMessage.getNotification().getTitle();
        String text = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID  = "HEADS_UP_NOTIFICATION";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());
    }

}

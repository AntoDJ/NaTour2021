/*
package com.example.natour2021;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Message;

import static android.view.textclassifier.ConversationActions.*;

import android.os.StrictMode;
import android.util.Log;
import android.view.textclassifier.ConversationActions;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.*;

import com.google.firebase.messaging.*;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyService extends FirebaseMessagingService{
import org.json.JSONObject;

import java.io.*;
import java.net.*;

public class MyService {

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
        public  String send(String to,  String body) {
            try {
                final String apiKey = "AAAAXLoO1-I:APA91bGIxXfu6pEAerJVY5AHISh0v1N8sLJPmZ_wpLsL1DIWKtIpN17DT5cecFv-zY0Ypn6sdtJghaSTTnUlfM01xsiAfsFXCeIadHzFrmDq_MC_avHBoy3VCEJrx0DysNy57LIuU4QC";
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key=" + apiKey);
                conn.setDoOutput(true);
                JSONObject message = new JSONObject();
                message.put("to", to);
                message.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", "notifica");
                notification.put("body", body);
                message.put("data", notification);
                OutputStream os = conn.getOutputStream();
                os.write(message.toString().getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.i("msg","\nSending 'POST' request to URL : " + url);
                Log.i("msg","Post parameters : " + message.toString());
                Log.i("msg","Response Code : " + responseCode);
                Log.i("msg","Response Code : " + conn.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

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
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "error";
        }
    }
    }

*/

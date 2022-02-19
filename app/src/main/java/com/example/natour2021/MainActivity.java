package com.example.natour2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;

import Controller.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller c = Controller.getInstance();
        c.logincheck(this);


    }
}
package com.example.natour2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


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
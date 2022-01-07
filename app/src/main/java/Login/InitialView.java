package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;

public class InitialView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_view);

        Button registrationButton = findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(view -> {
            Controller.userRegistration(InitialView.this);
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Controller.userLogin(InitialView.this);
        });


    }
}
package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.natour2021.R;

import Controller.Controller;
import Entity.Path;

public class PersonalDetailView extends AppCompatActivity {
    Controller c;
    Path path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_view);

        c = Controller.getInstance();
        path = c.getAllDetailsOfPersonalPath();

        Button modificaSentieroButton = (Button) findViewById(R.id.modificaSentieroButton);
        modificaSentieroButton.setOnClickListener(view -> {
            c.openModificationView(this);
        });




    }
}
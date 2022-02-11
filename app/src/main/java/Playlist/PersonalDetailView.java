package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.natour2021.R;

import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;
import Search.DetailInterface;

public class PersonalDetailView extends AppCompatActivity implements DetailInterface {
    String puntoiniziale;
    ArrayList<String> coordinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");

        Button modificaSentieroButton = (Button) findViewById(R.id.modificaSentieroButton);
        modificaSentieroButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openModificationView(this);
        });

        Button b1= (Button) findViewById(R.id.deletePathButton);
        b1.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.deletePathOverlay(PersonalDetailView.this);
        });

    }

    public String getPuntoIniziale(){
        return puntoiniziale;
    }

    public ArrayList<String> getCoordinate(){
        return coordinate;
    }
}
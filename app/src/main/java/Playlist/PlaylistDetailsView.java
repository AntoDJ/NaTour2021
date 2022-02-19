package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;
import Search.DetailInterface;

public class PlaylistDetailsView extends AppCompatActivity implements DetailInterface {
    String puntoiniziale;
    ArrayList<String> coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");

        Slider durataSlider=(Slider) findViewById(R.id.durataPlaylistSlider);
        durataSlider.setValue(intent.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById(R.id.durataPlaylistDetailsView);
        if(intent.getFloatExtra("durata",0)-(int)intent.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaPlaylistSlider);
        difficoltàSlider.setValue(intent.getIntExtra("difficolta",0));

        TextView difficoltà = (TextView) findViewById(R.id.difficoltaPlaylistDetailsView);
        difficoltà.setText("Difficoltà "+intent.getIntExtra("difficolta",0));

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById(R.id.namePathDetailsView);
        nome.setText(intent.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById(R.id.descrizionePlaylistDetailsView);
        if(intent.getStringExtra("descrizione")!=null){
            descrizione.setText(intent.getStringExtra("descrizione"));
        }
        else{
            descrizione.setText("Descrizione Vuota");
        }

        Button b1 = (Button) findViewById(R.id.eliminaSentieroDallaPlaylistButton);
        b1.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.removeFromPlaylistOverlay(PlaylistDetailsView.this);
        });
    }

    public String getPuntoIniziale(){
        return puntoiniziale;
    }

    public ArrayList<String> getCoordinate(){
        return coordinate;
    }
}
package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Controller.Controller;

public class DetailView extends AppCompatActivity {
    String puntoiniziale;
    ArrayList<String> coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent i = getIntent();
        puntoiniziale = i.getStringExtra("puntoiniziale");
        coordinate = i.getStringArrayListExtra("coordinate");
        Slider durataSlider=(Slider) findViewById(R.id.durataSliderDetail);
        durataSlider.setValue(i.getFloatExtra("durata",5));

        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltàSliderDetail);
        difficoltàSlider.setValue(i.getFloatExtra("difficolta",5));

        TextView nome = (TextView) findViewById(R.id.nomeTextView);
        nome.setText(i.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById(R.id.descrizioneTextView);
        if(i.getStringExtra("descrizione")!=null){
            descrizione.setText(i.getStringExtra("descrizione"));
        }

        TextView durata = (TextView) findViewById(R.id.durataTextViewDetail);
        durata.setText("Durata "+i.getFloatExtra("durata",0)+" ore");

        TextView difficoltà = (TextView) findViewById(R.id.difficoltàTextViewDetail);
        difficoltà.setText("Difficoltà "+i.getIntExtra("difficolta",0));

        TextView ultimaModifica = (TextView) findViewById(R.id.modificaTextViewDetail);

        Button playlistButton=(Button) findViewById(R.id.playlistButton);
        playlistButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.addToPlaylistOverlay(DetailView.this);
        });

        Button reportButton=(Button) findViewById(R.id.reportButton);
        reportButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openReportOverlay(DetailView.this);
        });

    }
    public String getPuntoIniziale(){
        return puntoiniziale;
    }

    public ArrayList<String> getCoordinate(){
        return coordinate;
    }
 }
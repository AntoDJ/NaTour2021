package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import Controller.Controller;

public class DetailView extends AppCompatActivity implements DetailInterface{
    String puntoiniziale;
    ArrayList<String> coordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent i = getIntent();
        puntoiniziale = i.getStringExtra("puntoiniziale");
        coordinate = i.getStringArrayListExtra("coordinate");

        //Set Durata
        Slider durataSlider=(Slider) findViewById(R.id.durataSliderDetail);
        durataSlider.setValue(i.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById(R.id.durataTextViewDetail);
        if(i.getFloatExtra("durata",0)-(int)i.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)i.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata "+(int)i.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltàSliderDetail);
        difficoltàSlider.setValue(i.getIntExtra("difficolta",0));

        TextView difficoltà = (TextView) findViewById(R.id.difficoltàTextViewDetail);
        difficoltà.setText("Difficoltà "+i.getIntExtra("difficolta",0));

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById(R.id.nomeTextView);
        nome.setText(i.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById(R.id.descrizioneTextView);
        if(i.getStringExtra("descrizione")!=null){
            descrizione.setText(i.getStringExtra("descrizione"));
        }
        //Set Ultima Modifica

        TextView ultimaModifica = (TextView) findViewById(R.id.modificaTextViewDetail);

        //Bottoni
        Button playlistButton=(Button) findViewById(R.id.playlistButton);
        playlistButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.addToPlaylistOverlay(DetailView.this);
            c.cleanFragment(findViewById(R.id.detailOverlayContainer));
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

    public void ReportPath(String Motivazione){
        Intent i = getIntent();
        Controller c = Controller.getInstance();
        c.reportPath(DetailView.this, i.getStringExtra("nomesentiero"), Motivazione, i.getStringExtra("creatore"));
    }

    public void addToPlaylist(String nomePlaylist){
        Intent i = getIntent();
        Controller c = Controller.getInstance();
        c.addPathToPlaylist(DetailView.this, i.getStringExtra("nomesentiero"),nomePlaylist);
    }
 }
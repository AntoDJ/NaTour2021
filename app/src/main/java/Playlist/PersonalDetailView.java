package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Controller.Controller;
import Entity.Path;
import Search.DetailInterface;

public class PersonalDetailView extends AppCompatActivity implements DetailInterface {
    private deletePathOverlay deletepathOverlay;
    private String puntoiniziale;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private ArrayList<String> coordinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");

        Slider durataSlider=(Slider) findViewById(R.id.durataPersonalSlider);
        durataSlider.setValue(intent.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById(R.id.durataPersonalDetailsView);
        if(intent.getFloatExtra("durata",0)-(int)intent.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaPersonalSlider);
        difficoltàSlider.setValue(intent.getIntExtra("difficolta",0));

        TextView difficoltà = (TextView) findViewById(R.id.difficoltaPersonalDetailsView);
        difficoltà.setText("Difficoltà "+intent.getIntExtra("difficolta",0));

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById(R.id.namePathPersonalDetailsView);
        nome.setText(intent.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById(R.id.descrizionePersonalDetailsView);
        if(intent.getStringExtra("descrizione").equals("")){
            descrizione.setText(intent.getStringExtra("descrizione"));
        }
        else{
            descrizione.setText("Descrizione Vuota");
        }

        //Set Ultima Modifica

        TextView ultimaModifica = (TextView) findViewById(R.id.UltimaModificaTextView);
        if(intent.getSerializableExtra("data")!=null){
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy 'alle' HH:mm");
            String data= simpleDateFormat.format((Date)intent.getSerializableExtra("data"));
            ultimaModifica.setText("Data ultima modifica : "+data);
        }
        else ultimaModifica.setVisibility(View.GONE);


        Button modificaSentieroButton = (Button) findViewById(R.id.modificaSentieroButton);
        modificaSentieroButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            Controller.getInstance().openModificationView(this, intent.getStringExtra("nomesentiero"),
                    intent.getStringExtra("descrizione"), intent.getBooleanExtra("accessiblità",true),
                    intent.getFloatExtra("durata",0), intent.getIntExtra("difficolta",0));
        });

        Button cancellaSentieroButton= (Button) findViewById(R.id.cancellaSentieroButton);
        cancellaSentieroButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            deletepathOverlay = Controller.getInstance().deletePathOverlay(PersonalDetailView.this, intent.getStringExtra("nomesentiero"));
        });

    }

    public String getPuntoIniziale(){
        return puntoiniziale;
    }

    public ArrayList<String> getCoordinate(){
        return coordinate;
    }

    @Override
    public void onBackPressed() {
        if(deletepathOverlay!=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.deletePathContainer));
            deletepathOverlay=null;
        }
        else super.onBackPressed();
    }

    public void updatePath(String descrizione, float durata, int difficolta) {
        TextView descrizioneTW = (TextView) findViewById(R.id.descrizionePersonalDetailsView);
        if(descrizione!=null){
            descrizioneTW.setText(descrizione);
        }
        else{
            descrizioneTW.setText("Descrizione Vuota");
        }
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaPersonalSlider);
        difficoltàSlider.setValue(difficolta);

        TextView difficoltàTW = (TextView) findViewById(R.id.difficoltaPersonalDetailsView);
        difficoltàTW.setText("Difficoltà "+difficolta);

        Slider durataSlider=(Slider) findViewById(R.id.durataPersonalSlider);
        durataSlider.setValue(durata);

        TextView durataTW = (TextView) findViewById(R.id.durataPersonalDetailsView);
        if(durata-(int)durata==0.5)   durataTW.setText("Durata "+(int)durata+":30 ore");
        else durataTW.setText("Durata "+(int)durata+" ore");
    }
}
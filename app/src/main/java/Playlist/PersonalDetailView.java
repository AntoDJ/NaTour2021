package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import Controller.Controller;
import Search.DetailInterface;

public class PersonalDetailView extends AppCompatActivity implements DetailInterface {
    private deletePathOverlay deletepathOverlay;
    private String puntoiniziale;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private ArrayList<String> coordinate;
    private float durata;
    private int difficolta;
    private String descrizione;
    private boolean accessibilita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");
        durata = intent.getFloatExtra("durata",0);
        difficolta = intent.getIntExtra("difficolta",0);
        descrizione = intent.getStringExtra("descrizione");
        accessibilita = intent.getBooleanExtra("accessiblità",true);


        Slider durataSlider=(Slider) findViewById(R.id.durataPersonalSlider);
        durataSlider.setValue(durata);

        TextView durataTW = (TextView) findViewById(R.id.durataPersonalDetailsView);
        if(durata-(int)durata==0.5)   durataTW.setText("Durata "+(int)durata+":30 ore");
        else durataTW.setText("Durata "+(int)durata+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaPersonalSlider);
        difficoltàSlider.setValue(difficolta);

        TextView difficoltà = (TextView) findViewById(R.id.difficoltaPersonalDetailsView);
        difficoltà.setText("Difficoltà "+difficolta);

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById(R.id.namePathPersonalDetailsView);
        nome.setText(intent.getStringExtra("nomesentiero"));

        TextView descrizioneTW = (TextView) findViewById(R.id.descrizionePersonalDetailsView);
        if(!descrizione.equals("")){
            descrizioneTW.setText(descrizione);
        }
        else{
            descrizioneTW.setText("Descrizione Vuota");
        }

        //Set Ultima Modifica

        TextView ultimaModifica = (TextView) findViewById(R.id.UltimaModificaTextView);
        if(intent.getSerializableExtra("data")!=null){
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy 'alle' HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String data= simpleDateFormat.format((Date)intent.getSerializableExtra("data"));
            ultimaModifica.setText("Data ultima modifica di Admin : "+data);
        }
        else ultimaModifica.setVisibility(View.GONE);


        Button modificaSentieroButton = (Button) findViewById(R.id.modificaSentieroButton);
        modificaSentieroButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            Controller.getInstance().openModificationView(this, intent.getStringExtra("nomesentiero"),
                    descrizione, accessibilita, durata, difficolta);
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

    public void updatePath(String descrizione, float durata, int difficolta, boolean accessibilità) {
        TextView descrizioneTW = (TextView) findViewById(R.id.descrizionePersonalDetailsView);
        this.descrizione = descrizione;
        if(descrizione!=null&&!descrizione.trim().equals("")){
            descrizioneTW.setText(descrizione);
        }
        else{
            descrizioneTW.setText("Descrizione Vuota");
        }
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaPersonalSlider);
        difficoltàSlider.setValue(difficolta);
        this.difficolta = difficolta;

        TextView difficoltàTW = (TextView) findViewById(R.id.difficoltaPersonalDetailsView);
        difficoltàTW.setText("Difficoltà "+difficolta);

        Slider durataSlider=(Slider) findViewById(R.id.durataPersonalSlider);
        durataSlider.setValue(durata);
        this. durata = durata;

        TextView durataTW = (TextView) findViewById(R.id.durataPersonalDetailsView);
        if(durata-(int)durata==0.5)   durataTW.setText("Durata "+(int)durata+":30 ore");
        else durataTW.setText("Durata "+(int)durata+" ore");
        this.accessibilita = accessibilità;
    }
}
package Search;

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

public class DetailView extends AppCompatActivity implements DetailInterface{
    private ReportOverlay reportOverlay;
    private addToPlaylistFragment addtoPlaylistFragment;
    private String puntoiniziale;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);
    private ArrayList<String> coordinate;

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
        if(i.getSerializableExtra("data")!=null){
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy 'alle' HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String data= simpleDateFormat.format((Date)i.getSerializableExtra("data"));
            ultimaModifica.setText("Data ultima modifica di Admin: "+data);
        }
        else ultimaModifica.setVisibility(View.GONE);

        //Bottoni
        Button playlistButton=(Button) findViewById(R.id.playlistButton);
        playlistButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            addtoPlaylistFragment = Controller.getInstance().addToPlaylistFragment(DetailView.this,
                    i.getStringExtra("nomesentiero"));
        });

        Button reportButton=(Button) findViewById(R.id.reportButton);
        reportButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            reportOverlay= Controller.getInstance().openReportOverlay(this,
                    i.getStringExtra("nomesentiero"),
                    i.getStringExtra("creatore"));
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
        if(reportOverlay!=null|| addtoPlaylistFragment !=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.detailOverlayContainer));
            reportOverlay=null;
            addtoPlaylistFragment =null;
        }
        else super.onBackPressed();
    }
 }
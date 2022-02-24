package Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Controller.Controller;
import Playlist.PersonalDetailView;
import Playlist.deletePathOverlay;
import Search.DetailInterface;

public class AdminDetailView extends AppCompatActivity implements DetailInterface {
    private deletePathOverlay deletepathOverlay;
    private String puntoiniziale;
    private ArrayList<String> coordinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");

        /*Slider durataSlider=(Slider) findViewById();
        durataSlider.setValue(intent.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById();
        if(intent.getFloatExtra("durata",0)-(int)intent.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById();
        difficoltàSlider.setValue(intent.getIntExtra("difficolta",0));

        TextView difficoltà = (TextView) findViewById();
        difficoltà.setText("Difficoltà "+intent.getIntExtra("difficolta",0));

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById();
        nome.setText(intent.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById();
        if(intent.getStringExtra("descrizione").equals("")){
            descrizione.setText(intent.getStringExtra("descrizione"));
        }
        else{
            descrizione.setText("Descrizione Vuota");
        }

        //Set Ultima Modifica

        TextView ultimaModifica = (TextView) findViewById();
        if(intent.getSerializableExtra("data")!=null){
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy 'alle' HH:mm");
            String data= simpleDateFormat.format((Date)intent.getSerializableExtra("data"));
            ultimaModifica.setText("Data ultima modifica : "+data);
        }
        else ultimaModifica.setVisibility(View.GONE);


        Button modificaSentieroButton = (Button) findViewById();
        modificaSentieroButton.setOnClickListener(view -> {
        });

        Button cancellaSentieroButton= (Button) findViewById();
        cancellaSentieroButton.setOnClickListener(view -> {
        });*/

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
}
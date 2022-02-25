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
import java.util.TimeZone;

import Controller.Controller;
import Playlist.PersonalDetailView;
import Playlist.deletePathOverlay;
import Search.DetailInterface;

public class AdminDetailView extends AppCompatActivity implements DetailInterface {
    private String puntoiniziale;
    private AdminDeleteFragment adminDeleteFragment;
    private ArrayList<String> coordinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_view);
        Intent intent = getIntent();
        puntoiniziale = intent.getStringExtra("puntoiniziale");
        coordinate = intent.getStringArrayListExtra("coordinate");

        Slider durataSlider=(Slider) findViewById(R.id.durataSliderAdminDetail);
        durataSlider.setValue(intent.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById(R.id.durataAdminDetailView);
        if(intent.getFloatExtra("durata",0)-(int)intent.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata "+(int)intent.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltaSliderAdminDetail);
        difficoltàSlider.setValue(intent.getIntExtra("difficolta",0));

        TextView difficoltà = (TextView) findViewById(R.id.difficoltaAdminDetailView);
        difficoltà.setText("Difficoltà "+intent.getIntExtra("difficolta",0));

        //Set Nome e Descrizione
        TextView nome = (TextView) findViewById(R.id.namePathAdminDetailView);
        nome.setText(intent.getStringExtra("nomesentiero"));

        TextView descrizione = (TextView) findViewById(R.id.descrizioneAdminDetailView);
        if(!intent.getStringExtra("descrizione").equals("")){
            descrizione.setText(intent.getStringExtra("descrizione"));
        }
        else{
            descrizione.setText("Descrizione Vuota");
        }

        //Set Ultima Modifica

        TextView ultimaModifica = (TextView) findViewById(R.id.ultimaModificaAdminDetailView);
        if(intent.getSerializableExtra("data")!=null){
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy 'alle' HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            String data= simpleDateFormat.format((Date)intent.getSerializableExtra("data"));
            ultimaModifica.setText(data);
        }
        else ultimaModifica.setText("Mai modificato");

        TextView creatore = (TextView) findViewById(R.id.creatoreAdminDetailView);
        creatore.setText(intent.getStringExtra("creatore"));

        Button modificaSentieroButton = (Button) findViewById(R.id.editPathAdminDetailView);
        modificaSentieroButton.setOnClickListener(view -> {
            Controller.getInstance().openAdminModificationView(this, intent.getStringExtra("nomesentiero"),
                    intent.getStringExtra("descrizione"), intent.getBooleanExtra("accessibilità",true),
                    intent.getFloatExtra("durata",0), intent.getIntExtra("difficolta",0));
        });

        Button cancellaSentieroButton= (Button) findViewById(R.id.deletePathAdminDetailView);
        cancellaSentieroButton.setOnClickListener(view -> {
            adminDeleteFragment = Controller.getInstance().adminDeleteFragment(this, intent.getStringExtra("nomesentiero"));
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
        if(adminDeleteFragment!=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.deletePathAdminContainer));
            adminDeleteFragment=null;
        }
        else super.onBackPressed();
    }
}
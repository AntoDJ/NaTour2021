package Create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import Controller.Controller;

public class CreateView extends AppCompatActivity {
    private ArrayList<Marker> coordinate;
    private MapViewFragment mapViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        Controller c = Controller.getInstance();

        coordinate=new ArrayList<>();

        Slider durataSlider = (Slider) findViewById(R.id.durataPlaylistSlider);
        Slider difficoltaSlider = (Slider) findViewById(R.id.difficoltaPlaylistSlider);

        TextView durataTextView = (TextView) findViewById(R.id.durataTextView);
        TextView difficoltaTextView = (TextView) findViewById(R.id.difficoltaTextView);
        EditText nomeEditText = (EditText) findViewById(R.id.namePathTextView);
        EditText descrizioneEditText = (EditText) findViewById(R.id.descrizionePlainText);
        CheckBox accessibilitaCB = (CheckBox) findViewById((R.id.AccessibilitaCheckBox));


        durataSlider.addOnChangeListener((slider, value, fromUser) -> {
            if(durataSlider.getValue() - (int) durataSlider.getValue() == 0.5){
                durataTextView.setText("Durata: " +(int) durataSlider.getValue() + ":30 ore");
            }else{
                durataTextView.setText("Durata: " + (int) durataSlider.getValue() + " ore");
            }
        });

        difficoltaSlider.addOnChangeListener((slider, value, fromUser) -> {
            difficoltaTextView.setText("Difficoltà: " + (int)difficoltaSlider.getValue());
        });

        Button inserisciTracciatoButton = (Button) findViewById(R.id.inserisciTracciatoButton);
        inserisciTracciatoButton.setOnClickListener(view -> {
            mapViewFragment = c.openInsertPath(CreateView.this);
        });

        Button creaSentiero = (Button) findViewById(R.id.creaSentieroButton);
        creaSentiero.setOnClickListener(view -> {
            String puntoInziale=coordinate.get(0).getPosition().latitude+" "+coordinate.get(0).getPosition().longitude;
            coordinate.remove(0);
            String coor="";
            for(Marker mar:coordinate) coor += mar.getPosition().latitude+" "+mar.getPosition().longitude+" ";
            c.createPath(this,nomeEditText.getText().toString().trim(),descrizioneEditText.getText().toString().trim(),durataSlider.getValue(),(int)difficoltaSlider.getValue(),accessibilitaCB.isChecked(), puntoInziale, coor);
        });
    }

    public void setCoordinate(ArrayList<Marker> coor){
        coordinate.clear();
        coordinate.addAll(coor);
    }
    public ArrayList<Marker> getCoordinate(){
        return coordinate;
    }

    @Override
    public void onBackPressed() {
        if(mapViewFragment!=null){
            Controller c = Controller.getInstance();
            c.cleanFragment(findViewById(R.id.mapViewContainer));
            mapViewFragment=null;
        }
        else super.onBackPressed();
    }

    public void errore() {
        Log.i("msg","errore");
    }
}
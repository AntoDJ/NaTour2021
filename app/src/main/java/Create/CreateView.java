package Create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import Controller.Controller;

public class CreateView extends AppCompatActivity {
    private ArrayList<String> coordinate= new ArrayList<>();
    private String puntoiniziale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        Controller c = Controller.getInstance();

        Slider durataSlider = (Slider) findViewById(R.id.durataPlaylistSlider);
        Slider difficoltaSlider = (Slider) findViewById(R.id.difficoltaPlaylistSlider);

        TextView durataTextView = (TextView) findViewById(R.id.durataTextView);
        TextView difficoltaTextView = (TextView) findViewById(R.id.difficoltaTextView);
        EditText nomeEditText = (EditText) findViewById(R.id.namePathTextView);
        EditText descrizioneEditText = (EditText) findViewById(R.id.descrizionePlainText);
        CheckBox accessibilitaCB = (CheckBox) findViewById((R.id.AccessibilitaCheckBox));


        durataSlider.addOnChangeListener((slider, value, fromUser) -> {
            if(durataSlider.getValue() - (int) durataSlider.getValue() == 0.5){
                durataTextView.setText("Durata: " + String.valueOf((int) durataSlider.getValue() + ":30 ore"));
            }else{
                durataTextView.setText("Durata: " + String.valueOf((int) durataSlider.getValue() + " ore"));
            }
        });

        difficoltaSlider.addOnChangeListener((slider, value, fromUser) -> {
            difficoltaTextView.setText("Difficoltà: " + String.valueOf(difficoltaSlider.getValue()));
        });

        Button inserisciTracciatoButton = (Button) findViewById(R.id.inserisciTracciatoButton);
        inserisciTracciatoButton.setOnClickListener(view -> {
            c.openInsertPath(CreateView.this);
        });

        Button creaSentiero = (Button) findViewById(R.id.creaSentieroButton);
        creaSentiero.setOnClickListener(view -> {
            c.createPath(this,nomeEditText.getText().toString().trim(),descrizioneEditText.getText().toString().trim(),durataSlider.getValue(),difficoltaSlider.getValue(),accessibilitaCB.isChecked(), puntoiniziale, coordinate);
        });
    }

    public void setCoordinate(ArrayList<String> coor){
        if(coor.size()!=0){
            puntoiniziale=coor.get(0);
            coor.remove(0);
            coordinate=coor;
        }
    }
}
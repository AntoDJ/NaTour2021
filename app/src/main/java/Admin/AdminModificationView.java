package Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

import Controller.Controller;

public class AdminModificationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modification_view);
        Intent i = getIntent();


        EditText namePathTextView = (EditText) findViewById(R.id.editNamePathAdminET);
        namePathTextView.setText(i.getStringExtra("nomeSentiero"));

        EditText descrizioneEditText = (EditText) findViewById(R.id.editDescrizioneEditText);
        descrizioneEditText.setText(i.getStringExtra("descrizione"));

        //Set Durata
        Slider durataSlider=(Slider) findViewById(R.id.editDurataSlider);
        durataSlider.setValue(i.getFloatExtra("durata",0));

        TextView durata = (TextView) findViewById(R.id.editDurataTextView);
        if(i.getFloatExtra("durata",0)-(int)i.getFloatExtra("durata",0)==0.5)   durata.setText("Durata "+(int)i.getFloatExtra("durata",0)+":30 ore");
        else durata.setText("Durata: "+(int)i.getFloatExtra("durata",0)+" ore");

        //Set Difficoltà
        Slider difficoltaSlider=(Slider) findViewById(R.id.editDifficoltaSlider);
        difficoltaSlider.setValue(i.getIntExtra("difficoltà",0));

        TextView difficoltà = (TextView) findViewById(R.id.editDifficoltaTextView);
        difficoltà.setText("Difficoltà: "+i.getIntExtra("difficoltà",0));

        CheckBox editAccessibilityCB = (CheckBox) findViewById(R.id.editAccessibilityAdminCB);
        editAccessibilityCB.setChecked(i.getBooleanExtra("accessibilità",true));

        durataSlider.addOnChangeListener((slider, value, fromUser) -> {

            if(durataSlider.getValue() - (int) durataSlider.getValue() == 0.5){
                durata.setText("Durata: " + String.valueOf((int) durataSlider.getValue() + ":30 ore"));
            }else{
                durata.setText("Durata: " + String.valueOf((int) durataSlider.getValue() + " ore"));
            }
        });

        difficoltaSlider.addOnChangeListener((slider, value, fromUser) -> {
            difficoltà.setText("Difficoltà: " + String.valueOf((int)difficoltaSlider.getValue()));
        });


        Button cancelEditButton = (Button) findViewById(R.id.cancelEditAdminButton);
        cancelEditButton.setOnClickListener(view -> {
            finish();
        });

        Button editPathButton = (Button) findViewById(R.id.confirmEditAdminButton);
        editPathButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            if(namePathTextView.getText().toString().trim().equals("")){
                c.updatePathAdmin(this, i.getStringExtra("nomeSentiero"), descrizioneEditText.getText().toString().trim(), durataSlider.getValue(), (int) difficoltaSlider.getValue(), editAccessibilityCB.isChecked(), namePathTextView.getText().toString().trim());
            }
        });

    }
}
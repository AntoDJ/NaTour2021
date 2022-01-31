package Search;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.RangeSlider;

import Controller.Controller;


public class SearchView extends AppCompatActivity {
    private String posizione;

    public void setPosizione(String pos) {
        posizione= pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        RangeSlider durataSlider = (RangeSlider) findViewById(R.id.durataSliderSearch);
        TextView durataVal = (TextView) findViewById(R.id.durataTextViewSearch);

        durataSlider.addOnChangeListener((slider, value, fromUser) -> {
            String temp;
            if(durataSlider.getValues().get(0).equals(durataSlider.getValues().get(1))){
                if (durataSlider.getValues().get(0) - durataSlider.getValues().get(0).intValue() == 0.5) {
                    durataVal.setText("Durata " + String.valueOf(durataSlider.getValues().get(0).intValue()) + ":30 ore");
                } else
                    durataVal.setText("Durata " + String.valueOf(durataSlider.getValues().get(0).intValue()) + " ore");

            }
            else {
                if (durataSlider.getValues().get(0) - durataSlider.getValues().get(0).intValue() == 0.5) {
                    temp = "Durata da " + String.valueOf(durataSlider.getValues().get(0).intValue()) + ":30 a ";
                } else
                    temp = "Durata da " + String.valueOf(durataSlider.getValues().get(0).intValue()) + " a ";
                if (durataSlider.getValues().get(1) - durataSlider.getValues().get(1).intValue() == 0.5) {
                    durataVal.setText(temp + String.valueOf(durataSlider.getValues().get(1).intValue()) + ":30 ore");
                } else
                    durataVal.setText(temp + String.valueOf(durataSlider.getValues().get(1).intValue()) + " ore");
            }
        });

        RangeSlider difficoltaSlider = (RangeSlider) findViewById(R.id.difficoltàSliderSearch);
        TextView difficoltaVal = (TextView) findViewById(R.id.difficoltàTextViewSearch);

        difficoltaSlider.addOnChangeListener((slider2, value2, fromUser2) -> {
            if(difficoltaSlider.getValues().get(0).equals(difficoltaSlider.getValues().get(1))){
                difficoltaVal.setText("Difficoltà " + String.valueOf(Math.round(difficoltaSlider.getValues().get(0))));
            }
            else {
                difficoltaVal.setText("Difficoltà da " + String.valueOf(Math.round(difficoltaSlider.getValues().get(0))) + " a " + String.valueOf(Math.round(difficoltaSlider.getValues().get(1))));
            }
        });

        CheckBox access = (CheckBox) findViewById(R.id.accessibilitàCheckBoxSearch);
        Button cercaButton=(Button) findViewById(R.id.searchButton);
        cercaButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.searchPaths(this, durataSlider.getValues().get(0), durataSlider.getValues().get(1), difficoltaSlider.getValues().get(0), difficoltaSlider.getValues().get(1), posizione, access.isChecked());
        });
    }
}
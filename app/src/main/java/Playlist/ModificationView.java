package Playlist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

public class ModificationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_view);

        TextView durataTextView = (TextView) findViewById(R.id.durataTextViewModificatioView);
        TextView difficoltaTextView = (TextView) findViewById(R.id.difficoltaTextViewModificatioView);

        Slider durataSlider = (Slider) findViewById(R.id.durataSliderModificatioView);
        Slider difficoltaSlider = (Slider) findViewById(R.id.difficoltaSliderModificatioView);

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


    }
}
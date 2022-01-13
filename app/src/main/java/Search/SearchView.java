package Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.RangeSlider;

import Controller.Controller;


public class SearchView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        RangeSlider durataSlider = (RangeSlider) findViewById(R.id.durataSliderSearch);
        TextView durataVal = (TextView) findViewById(R.id.durataTextViewSearch);

        durataSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                durataVal.setText("Durata da "+String.valueOf(durataSlider.getValues().get(0))+" a "+String.valueOf(durataSlider.getValues().get(1))+" ore");
            }
        });

        durataSlider.addOnChangeListener((slider, value, fromUser) -> {
            durataVal.setText("Durata da "+String.valueOf(durataSlider.getValues().get(0))+" a "+String.valueOf(durataSlider.getValues().get(1))+" ore");
        });

        RangeSlider difficoltàSlider = (RangeSlider) findViewById(R.id.difficoltàSliderSearch);
        TextView difficoltàVal = (TextView) findViewById(R.id.difficoltàTextViewSearch);


        difficoltàSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                difficoltàVal.setText("Difficoltà da "+String.valueOf(Math.round(difficoltàSlider.getValues().get(0)))+" a "+String.valueOf(Math.round(difficoltàSlider.getValues().get(1))));
            }
        });

        difficoltàSlider.addOnChangeListener((slider, value, fromUser) -> {
            difficoltàVal.setText("Difficoltà da "+String.valueOf(Math.round(difficoltàSlider.getValues().get(0)))+" a "+String.valueOf(Math.round(difficoltàSlider.getValues().get(1))));
        });


        Button cercaButton=(Button) findViewById(R.id.searchButton);
        cercaButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.resultView(SearchView.this);
        });
    }


}
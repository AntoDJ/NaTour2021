package Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.RangeSlider;


public class SearchView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        RangeSlider rangeslider = (RangeSlider) findViewById(R.id.durataSliderSearch);
        TextView minVal = (TextView) findViewById(R.id.minVal);
        TextView maxVal = (TextView) findViewById(R.id.maxVal);

        rangeslider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                minVal.setText(String.valueOf(rangeslider.getValues().get(0)));
                maxVal.setText(String.valueOf(rangeslider.getValues().get(1)));
            }
        });

        rangeslider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                minVal.setText(String.valueOf(rangeslider.getValues().get(0)));
                maxVal.setText(String.valueOf(rangeslider.getValues().get(1)));
            }
        });



    }

}
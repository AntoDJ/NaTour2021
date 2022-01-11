package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

    }

}
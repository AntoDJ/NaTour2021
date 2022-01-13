package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Slider durataSlider=(Slider) findViewById(R.id.durataSliderDetail);

        Slider difficoltàSlider=(Slider) findViewById(R.id.difficoltàSliderDetail);

        TextView nome = (TextView) findViewById(R.id.nomeTextView);

        TextView descrizione = (TextView) findViewById(R.id.descrizioneTextView);

        TextView durata = (TextView) findViewById(R.id.durataTextViewDetail);

        TextView difficoltà = (TextView) findViewById(R.id.difficoltàTextViewDetail);

        TextView ultimaModifica = (TextView) findViewById(R.id.modificaTextViewDetail);

        Button playlistButton=(Button) findViewById(R.id.playlistButton);
        playlistButton.setOnClickListener(view -> {

        });
        Button reportButton=(Button) findViewById(R.id.reportButton);
        reportButton.setOnClickListener(view -> {

        });

    }
 }
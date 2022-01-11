package Create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;

public class CreateView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);

        SeekBar durataSeekBar = (SeekBar) findViewById(R.id.durataSeekBar);
        durataSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tv = (TextView) findViewById(R.id.valoreDurata);
                tv.setText(String.valueOf(getConvertedValue(progress)));
            }
            public double getConvertedValue(int intVal){
                double value = 0.0;
                value = 0.5 * intVal;
                return value;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar difficoltàSeekBar = (SeekBar) findViewById(R.id.difficoltàSeekBar);
        difficoltàSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView tv = (TextView) findViewById(R.id.valoreDifficoltà);
                tv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button inserisciTracciatoButton = (Button) findViewById(R.id.inserisciTracciatoButton);
        inserisciTracciatoButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openInsertPath(CreateView.this);
        });
    }
}
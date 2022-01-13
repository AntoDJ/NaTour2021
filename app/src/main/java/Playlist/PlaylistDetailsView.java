package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.natour2021.R;

import Controller.Controller;
import Entity.Path;

public class PlaylistDetailsView extends AppCompatActivity {
    Controller c;
    Path path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details_view);

        c = Controller.getInstance();
        path = c.getAllDetailsOfPath();


        SeekBar durataSeekBar = (SeekBar) findViewById(R.id.durataSeekBarDetailsView);
        //metodo per fissare valore alla seekbar
        durataSeekBar.setProgress(7);
        durataSeekBar.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });


        SeekBar difficoltàSeekBar = (SeekBar) findViewById(R.id.difficoltàSeekBarDetailsView);
        difficoltàSeekBar.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });

    }
}
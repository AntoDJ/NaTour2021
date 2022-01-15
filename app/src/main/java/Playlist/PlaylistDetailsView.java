package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.natour2021.R;
import com.google.android.material.slider.Slider;

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

        Slider durataSlider = (Slider) findViewById(R.id.durataPlaylistSlider);
        durataSlider.setValue(5);



    }
}
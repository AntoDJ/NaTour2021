package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
        Intent intent = getIntent();
        Log.i("msg",intent.getStringExtra("nomesentiero"));
        Log.i("msg",intent.getStringExtra("playlist"));



        Slider durataSlider = (Slider) findViewById(R.id.durataPlaylistSlider);
        durataSlider.setValue(5);

        Button b1 = (Button) findViewById(R.id.eliminaSentieroDallaPlaylistButton);
        b1.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.removeFromPlaylistOverlay(PlaylistDetailsView.this);
        });
    }
}
package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.natour2021.R;

import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;

public class PlaylistView extends AppCompatActivity {
    Controller c;
    ArrayList<Path> path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);

        ListView sentieriPlaylist = (ListView) findViewById(R.id.sentieriPlaylistListView);

        c = Controller.getInstance();
        TextView nomePlaylist = (TextView) findViewById(R.id.nomePlaylistTextView);
        nomePlaylist.setText(c.playlist);


        path = c.getPathOfPlaylist(nomePlaylist.getText().toString());
        ArrayList<String> dettagliSentiero = new ArrayList<>();
        for(Path p : path){
            dettagliSentiero.add(p.getNomeSentiero() + " \nDurata:" + p.getDurata() + "    Difficoltà:" + p.getDifficolta());
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dettagliSentiero);
        sentieriPlaylist.setAdapter(arrayAdapter);


        sentieriPlaylist.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            String text = sentieriPlaylist.getItemAtPosition(i).toString();
            int index = text.indexOf(' ');
            String sentiero = text.substring(0, index);
            c.openPlaylistDetailsView(this, sentiero);
        });




    }
}




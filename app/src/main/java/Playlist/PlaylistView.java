package Playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
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
        Intent intent = getIntent();

        ListView sentieriPlaylist = (ListView) findViewById(R.id.sentieriPlaylistListView);

        c = Controller.getInstance();
        TextView nomePlaylist = (TextView) findViewById(R.id.nomePlaylistTextView);
        nomePlaylist.setText(intent.getStringExtra("nomePlaylist"));

        ArrayList<String> nomiSentieri = intent.getStringArrayListExtra("nomiSentieri");
        ArrayList<String> durdiffSentieri = intent.getStringArrayListExtra("durdiffSentieri");

        ArrayList<String> dettagliSentiero= new ArrayList<>();
        for(int i=0;i<nomiSentieri.size();i++)
            dettagliSentiero.add(nomiSentieri.get(i)+" "+durdiffSentieri.get(i));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dettagliSentiero);
        sentieriPlaylist.setAdapter(arrayAdapter);


        sentieriPlaylist.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            c.getAllDetailsOfPlaylistPath(this, intent.getStringExtra("nomePlaylist"),nomiSentieri.get(i));
        });




    }
}




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
import android.widget.Toast;

import com.example.natour2021.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Controller.Controller;
import Entity.Path;

public class PlaylistView extends AppCompatActivity {
    Controller c;
    ArrayList<String> nomiSentieri;
    ArrayList<String> durdiffSentieri;
    ListView sentieriPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);
        Intent intent = getIntent();

        sentieriPlaylist = (ListView) findViewById(R.id.sentieriPlaylistListView);

        c = Controller.getInstance();
        TextView nomePlaylist = (TextView) findViewById(R.id.nomePlaylistTextView);
        nomePlaylist.setText(intent.getStringExtra("nomePlaylist"));

        nomiSentieri = intent.getStringArrayListExtra("nomiSentieri");
        durdiffSentieri = intent.getStringArrayListExtra("durdiffSentieri");

        ArrayList<String> dettagliSentiero= new ArrayList<>();
        for(int i=0;i<nomiSentieri.size();i++)
            dettagliSentiero.add("  "+nomiSentieri.get(i)+"\n"+durdiffSentieri.get(i));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dettagliSentiero);
        sentieriPlaylist.setAdapter(arrayAdapter);


        sentieriPlaylist.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller c = Controller.getInstance();
            c.getAllDetailsOfPlaylistPath(this, intent.getStringExtra("nomePlaylist"),nomiSentieri.get(i));
        });

    }

    public void removePathView(String nomesentiero){
        int posizione=-1;
        for(String nome:nomiSentieri){
            if(nome.equals(nomesentiero)) posizione=nomiSentieri.indexOf(nome);
        }
        if(posizione!=-1) {
            nomiSentieri.remove(posizione);
            durdiffSentieri.remove(posizione);
            if(nomiSentieri.size()==0){
                Toast.makeText(this,"Playlist chiusa perchè vuota",Toast.LENGTH_LONG).show();
                this.finish();
            }
            else{
                ArrayList<String> sentieroToList = new ArrayList<>();
                for(int i=0;i<nomiSentieri.size();i++) {
                    sentieroToList.add("  " + nomiSentieri.get(i) + "\n" + durdiffSentieri.get(i));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sentieroToList);
                sentieriPlaylist.setAdapter(arrayAdapter);
            }
        }
    }
}




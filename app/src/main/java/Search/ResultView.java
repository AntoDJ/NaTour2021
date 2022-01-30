package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.natour2021.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Controller.Controller;

public class ResultView extends AppCompatActivity {
    private ArrayList<String> Nomi;
    private ArrayList<String> Posizioni;
    private ArrayList<Integer> difficolta;
    private float[] durate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        Intent intent = getIntent();
        Nomi= intent.getStringArrayListExtra("Nomi");
        Posizioni = intent.getStringArrayListExtra("PuntiIniziali");
        difficolta = intent.getIntegerArrayListExtra("Difficoltà");
        durate = intent.getFloatArrayExtra("Durate");
    }

    public ArrayList<String> getNomi(){
        return Nomi;
    }

    public  ArrayList<String> getPosizioni(){
        return Posizioni;
    }

    public ArrayList<Integer> getDifficolta() { return difficolta;}

    public float[] getDurate() {return durate;}

    public void markerclicked(String title) {
        Controller c = Controller.getInstance();
        c.detailView(title, ResultView.this);
    }
}
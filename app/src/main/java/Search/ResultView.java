package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Toast;

import com.example.natour2021.R;

import java.util.ArrayList;

import Controller.Controller;

public class ResultView extends AppCompatActivity {
    private ArrayList<String> Nomi;
    private ArrayList<String> Posizioni;
    private ArrayList<Integer> difficolta;
    private long mLastClickTime = 0;
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

    public float[] getDurate() { return durate; }

    public void markerclicked(String title) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 4000){
            Toast.makeText(this,"Sto caricando i dettagli del sentiero",Toast.LENGTH_LONG).show();
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Controller c = Controller.getInstance();
        c.getAllDetailsOfPath(this,title);
    }
}
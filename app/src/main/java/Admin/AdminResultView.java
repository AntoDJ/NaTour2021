package Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.natour2021.R;

import java.util.ArrayList;

import Controller.Controller;


public class AdminResultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_result_view);
        Intent intent = getIntent();

        ListView adminResultList = (ListView) findViewById(R.id.AdminResultList);
        ArrayList<String> nomiSentieri = new ArrayList<>();
        ArrayList<String> creatoriSentieri = new ArrayList<>();

        ArrayList<String> dettagliSentiero= new ArrayList<>();
        for(int i=0;i<nomiSentieri.size();i++)
            dettagliSentiero.add("  "+nomiSentieri.get(i)+"\n"+creatoriSentieri.get(i));

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dettagliSentiero);
        adminResultList.setAdapter(arrayAdapter);


        adminResultList.setOnItemClickListener((adapterView, view, i, l) -> {
            Controller.getInstance().openAdminDetails(this, nomiSentieri.get(i));
        });

    }
}
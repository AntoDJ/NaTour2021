package Search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.natour2021.R;

import Controller.Controller;

public class ResultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);

        Button dettagliButton=(Button) findViewById(R.id.dettagliButton);
        dettagliButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.detailView(ResultView.this);
        });
    }
}
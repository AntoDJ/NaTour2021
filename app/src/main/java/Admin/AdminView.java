package Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.natour2021.R;

import Controller.Controller;

public class AdminView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        EditText AdminSearchET = (EditText) findViewById(R.id.AdminSearchET);


        Button AdminSearchButton = (Button) findViewById(R.id.AdminSearchButton);
        AdminSearchButton.setOnClickListener(view -> {
            Controller.getInstance().adminSearchPath(this, AdminSearchET.getText().toString().trim());
        });

        Button AdminBackButton = (Button) findViewById(R.id.AdminBackButton);
        AdminBackButton.setOnClickListener(view -> {
            finish();
        });


    }
}
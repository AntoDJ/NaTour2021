package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.MainActivity;
import com.example.natour2021.R;

import Controller.Controller;

public class LoginView extends AppCompatActivity {
    private int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        TextView passwordDimenticata = (TextView) findViewById(R.id.forgotPasswordTextView);
        passwordDimenticata.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openForgotPasswordOverlay(LoginView.this);
        });

        Button accediButton = (Button) findViewById(R.id.accediButton);
        accediButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.userLogin(LoginView.this);
        });

        Button registratiButton = (Button) findViewById(R.id.registratiButton);
        registratiButton.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.userRegistration(LoginView.this);
        });

    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            backButtonCount=0;
            finish();
        }
        else
        {
            Toast.makeText(this, "Premi indietro di nuovo per chiudere l'app", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
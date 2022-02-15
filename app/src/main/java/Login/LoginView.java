package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;

public class LoginView extends AppCompatActivity {
    private int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        EditText campomail = (EditText) findViewById(R.id.emailLoginPlainText);
        EditText campopass = (EditText) findViewById(R.id.passwordLoginPlainText);


        TextView passwordDimenticata = (TextView) findViewById(R.id.forgotPasswordTextView);
        passwordDimenticata.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openForgotPasswordOverlay(LoginView.this);
        });

        Button accediButton = (Button) findViewById(R.id.accediButton);
        accediButton.setOnClickListener(view -> {
            if(campomail.getText().toString().trim().length()!=0&&campopass.getText().toString().trim().length()!=0) {
                Controller c = Controller.getInstance();
                c.userLogin(LoginView.this, campomail.getText().toString().trim(), campopass.getText().toString().trim());
            }
            else Toast.makeText(this, "Inserisci le credenziali",Toast.LENGTH_SHORT).show();
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
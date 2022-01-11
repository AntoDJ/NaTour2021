package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.natour2021.MainActivity;
import com.example.natour2021.R;

import Controller.Controller;

public class LoginView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        TextView passwordDimenticata = (TextView) findViewById(R.id.forgotPasswordTextView);
        passwordDimenticata.setOnClickListener(view -> {
            Controller c = Controller.getInstance();
            c.openForgotPasswordOverlay(LoginView.this);
        });

        Button b = (Button) findViewById(R.id.accediButton);
        b.setOnClickListener(view -> {
            Intent i = new Intent(this, HomeView.class);
            startActivity(i);
        });

        Button b2 = (Button) findViewById(R.id.registratiButton);
        b2.setOnClickListener(view -> {
            Intent i= new Intent(this, RegistrationView.class);
            startActivity(i);
        });

    }
}
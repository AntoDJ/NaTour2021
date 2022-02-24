package Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natour2021.R;

import Controller.Controller;

public class RegistrationView extends AppCompatActivity {
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_view);
        buttonClick.setDuration(300);

        EditText email = findViewById(R.id.emailPlainText);
        EditText password = findViewById(R.id.passwordRegistrationPlainText);
        EditText confermaPassword = findViewById(R.id.confermaPasswordRegistrationPlainText);


        Button registrationButton = findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(view -> {
            view.startAnimation(buttonClick);
            if((password.getText().toString().equals(confermaPassword.getText().toString())) && !(password.getText().toString().equals(""))
                    && !(confermaPassword.getText().toString().equals("")) && !(email.getText().toString().equals(""))){

                Controller.getInstance().registraUtente(this, email.getText().toString().trim().toLowerCase(), password.getText().toString().trim());
            }else{
                Toast.makeText(this, "Controlla le credenziali",Toast.LENGTH_SHORT).show();
            }

        });


    }
}
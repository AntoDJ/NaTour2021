package Login;

import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.core.Amplify;
import com.example.natour2021.R;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import Controller.Controller;

public class LoginView extends AppCompatActivity {
    private int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);
        Controller.getInstance().setLoginView(this);
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

        ImageButton facebookButton = (ImageButton) findViewById(R.id.facebookButton);
        facebookButton.setOnClickListener(view -> {
            Amplify.Auth.signInWithSocialWebUI(AuthProvider.facebook(), this,
                    result -> {
                        Log.i("successo","login success");
                        Amplify.Auth.fetchUserAttributes(
                                attributes -> Controller.getInstance().logintoDB("facebook",attributes),
                                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                        );
                    },
                    error -> Log.i("errore","errore")
            );
        });

        ImageButton googleButton = (ImageButton) findViewById(R.id.googleButton);
        googleButton.setOnClickListener(view -> {
            Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), this,
                    result -> {
                        Log.i("successo","login success");
                        Amplify.Auth.fetchUserAttributes(
                                attributes -> Controller.getInstance().logintoDB("google",attributes),
                                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
                        );
                    },
                    error -> Log.i("errore","errore")
            );
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
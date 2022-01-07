package Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natour2021.R;

import Controller.Controller;

public class LoginView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        TextView passwordDimenticata = (TextView) findViewById(R.id.forgotPasswordTextView);
        passwordDimenticata.setOnClickListener(view -> {
            Controller.openForgotPasswordOverlay(LoginView.this);
        });




    }
}
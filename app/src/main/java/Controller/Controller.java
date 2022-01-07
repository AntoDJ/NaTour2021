package Controller;

import android.content.Intent;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2021.R;

import Login.InitialView;
import Login.LoginView;
import Login.PasswordOverlay;
import Login.RegistrationView;

public class Controller {


    public static void userRegistration(InitialView initialView){
        Intent i = new Intent(initialView, RegistrationView.class);
        initialView.startActivity(i);
    }

    public static void userLogin(InitialView initialView){
        Intent i = new Intent(initialView, LoginView.class);
        initialView.startActivity(i);
    }

    public static void openForgotPasswordOverlay(LoginView loginView){
        FragmentManager fragmentManager = loginView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PasswordOverlay passwordOverlay = new PasswordOverlay();
        fragmentTransaction.add(R.id.passwordOverlayContainer, passwordOverlay, null);
        fragmentTransaction.commit();
    }

    public static void cleanFragment(FrameLayout frameLayout){
        frameLayout.removeAllViews();
    }


}

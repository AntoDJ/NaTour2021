package Controller;

import android.content.Intent;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2021.R;

import Login.*;
import Login.ui.home.HomeFragment;
import Search.*;
import Create.CreateView;
import Create.MapView;
import Login.HomeView;
import Login.LoginView;
import Login.PasswordOverlay;
import Login.RegistrationView;
import Login.ui.home.HomeFragment;

public class Controller {
    //Singleton
    private static Controller instance;
    private Controller() {}
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return(instance);
    }

    public void userLogin(LoginView loginView){
        Intent i = new Intent(loginView, HomeView.class);
        loginView.startActivity(i);
    }

    public void userRegistration(LoginView loginView){
        Intent i = new Intent(loginView, RegistrationView.class);
        loginView.startActivity(i);
    }

    public void openForgotPasswordOverlay(LoginView loginView){
        FragmentManager fragmentManager = loginView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PasswordOverlay passwordOverlay = new PasswordOverlay();
        fragmentTransaction.add(R.id.passwordOverlayContainer, passwordOverlay, null);
        fragmentTransaction.commit();
    }

    public void openCreatePathView(HomeFragment homeFragment){
        Intent i = new Intent(homeFragment.getActivity(), CreateView.class);
        homeFragment.startActivity(i);
    }






















    public void searchView(HomeFragment homeFragment){
        Intent i= new Intent(homeFragment.getActivity(), SearchView.class);
        homeFragment.startActivity(i);
    }

    public static void cleanFragment(FrameLayout frameLayout){
        frameLayout.removeAllViews();
    }

    public void openInsertPath(CreateView createView){
        Intent i = new Intent(createView, MapView.class);
        createView.startActivity(i);
    }




}

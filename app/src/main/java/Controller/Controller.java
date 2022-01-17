package Controller;

import android.app.appsearch.ReportSystemUsageRequest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2021.R;

import Login.*;
import java.util.ArrayList;

import Login.ui.MyPath.*;
import Login.*;
import Entity.*;
import Login.ui.home.*;
import Login.ui.playlist.*;
import Playlist.*;
import Search.*;
import Create.*;
import Login.ui.home.*;


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
    public void resultView(SearchView searchView){
        Intent i = new Intent(searchView, ResultView.class);
        searchView.startActivity(i);
    }

    public void detailView(ResultView resultView){
        Intent i = new Intent(resultView, DetailView.class);
        resultView.startActivity(i);
    }

    //Mi salvo il nome della playlist se può servire dopo
    public String playlist;
    public void openPlaylistView(PlaylistFragment playlistFragment, String nomePlaylist){
        playlist = nomePlaylist;
        Intent i = new Intent(playlistFragment.getActivity(), PlaylistView.class);
        playlistFragment.startActivity(i);
    }

    public ArrayList<Path> getPathOfPlaylist(String nomePlaylist){

        Path p1 = new Path("s1", 5, 2);
        Path p2 = new Path("s2", 3, 2);

        ArrayList<Path> path = new ArrayList<>();
        path.add(p1);
        path.add(p2);


        //Scrivere codice che si collega al db per prendere i sentieri della playlist
        return path;
    }

    public ArrayList<Path> getPersonalPathOfPlaylist(){

        Path p1 = new Path("s3", 5, 2);
        Path p2 = new Path("s4", 5, 2);

        ArrayList<Path> path = new ArrayList<>();
        path.add(p1);
        path.add(p2);

        //Scrivere codice che si collega al db per prendere i sentieri della playlist
        return path;
    }

    public String namePath;
    public void openPlaylistDetailsView(PlaylistView playlistView, String pathName){
        namePath = pathName;
        Intent i = new Intent(playlistView, PlaylistDetailsView.class);
        playlistView.startActivity(i);
    }

    public void openPersonalDetailsView(PersonalPlaylistView personalPlaylistView){
        Intent i = new Intent(personalPlaylistView.getContext(), PersonalDetailView.class);
        personalPlaylistView.startActivity(i);
    }

    public void openModificationView(PersonalDetailView personalDetailView){
        Intent i = new Intent(personalDetailView, ModificationView.class);
        personalDetailView.startActivity(i);
    }


    public Path getAllDetailsOfPath(){
        //chiamata  al db che restituisce i dettagli utilizzando la variabile 'namePath' e mettendola a null dopo l'utilizzo
        return new Path();
    }

    public Path getAllDetailsOfPersonalPath(){
        //chiamata  al db che restituisce i dettagli utilizzando la variabile 'namePath' e mettendola a null dopo l'utilizzo
        return new Path();
    }



    public void openReportOverlay(DetailView detailView){
        FragmentManager fragmentManager = detailView.getSupportFragmentManager();
        fragmentManager.setFragmentResultListener("", detailView, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                detailView.findViewById(R.id.playlistButton).setEnabled(true);
            }
        });
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReportOverlay reportOverlay = new ReportOverlay();
        fragmentTransaction.add(R.id.reportOverlayContainer, reportOverlay, null);
        fragmentTransaction.commit();
    }
}

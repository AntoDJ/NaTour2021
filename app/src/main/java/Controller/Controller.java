package Controller;

import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2021.R;

import Login.*;
import java.util.ArrayList;

import Login.ui.MyPath.*;
import Entity.*;
import Login.ui.home.*;
import Login.ui.playlist.*;
import Login.ui.settings.*;
import Playlist.*;
import Search.*;
import Create.*;
import DAO.UtenteDAO;


public class Controller {
    UtenteDAO utenteDAO = new UtenteDAO();
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

    public void registraUtente(String email, String password){
        User utente = new User(email,password);
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

    public void cleanFragment(FrameLayout frameLayout){
        frameLayout.removeAllViews();
    }

    public void openInsertPath(CreateView createView){
        FragmentManager fragmentManager = createView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapViewFragment mapViewFragment = new MapViewFragment();
        fragmentTransaction.add(R.id.mapViewContainer, mapViewFragment, null);
        fragmentTransaction.commit();
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


    public Path getAllDetailsOfPersonalPath(){
        //chiamata  al db che restituisce i dettagli utilizzando la variabile 'namePath' e mettendola a null dopo l'utilizzo
        return new Path();
    }


    public void openReportOverlay(DetailView detailView){
        FragmentManager fragmentManager = detailView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReportOverlay reportOverlay = new ReportOverlay();
        fragmentTransaction.add(R.id.detailOverlayContainer, reportOverlay, null);
        fragmentTransaction.commit();
    }

    public void addToPlaylistOverlay(DetailView detailView){
        FragmentManager fragmentManager = detailView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        addToPlaylistOverlay addtoplaylistoverlay = new addToPlaylistOverlay();
        fragmentTransaction.add(R.id.detailOverlayContainer, addtoplaylistoverlay, null);
        fragmentTransaction.commit();
    }

    public void removeFromPlaylistOverlay(PlaylistDetailsView playlistdetailsview){
        FragmentManager fragmentManager = playlistdetailsview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        removeFromPlaylistOverlay removefromplaylistoverlay = new removeFromPlaylistOverlay();
        fragmentTransaction.add(R.id.removeFromPlaylistContainer, removefromplaylistoverlay, null);
        fragmentTransaction.commit();
    }

    public void deletePathOverlay(PersonalDetailView personaldetailview){
        FragmentManager fragmentManager = personaldetailview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        deletePathOverlay deletepathoverlay = new deletePathOverlay();
        fragmentTransaction.add(R.id.deletePathContainer, deletepathoverlay, null);
        fragmentTransaction.commit();
    }

    public void logoutOverlay(HomeView homeview){
        FragmentManager fragmentManager = homeview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogoutFragment logoutoverlay = new LogoutFragment();
        fragmentTransaction.add(R.id.HomeContainer, logoutoverlay, null);
        fragmentTransaction.commit();
    }

    public void createPath(String nome, String descrizione, float durata, float difficoltà, boolean access, String puntoiniziale, ArrayList<String> coordinate) {
        //insert nel db con la roba sopra bisogna mettere i controlli da qualche parte non so se possiamo farlo tramite DB
        Log.i("nome",nome);
        Log.i("descrizione",descrizione);
        Log.i("durata", String.valueOf(durata));
        Log.i("difficolta",String.valueOf(difficoltà));
        Log.i("access", String.valueOf(access));
        Log.i("puntoinziiale",puntoiniziale);
        int i=1;
        for(String s: coordinate){
            Log.i("punto "+i++,s);
        }
    }

    public void searchPaths(SearchView searchView, float mindiff, float maxdiff, float mindur, float maxdur, String pos, boolean access) {
        ArrayList<Path> sentierifiltrati = this.getFilteredPaths(mindiff, maxdiff, mindur, maxdur, pos, access);

        //sentieri temporanei
        Path p1 = new Path("Sentiero1",null, "40.956116 14.530439",3, 3.5f,null,true,null,"utente1");
        Path p2 = new Path("Sentiero2",null, "41.255365 14.035338",3, 5f,null,true,null,"utente2");
        Path p3 = new Path("Sentiero3",null, "41.136097 14.932931",3, 6f,null,true,null,"utente3");
        sentierifiltrati.add(p1); sentierifiltrati.add(p2); sentierifiltrati.add(p3);
        if(sentierifiltrati.size()==0){
            Log.i("errore","Non esistono sentieri ");
            //metodo errore nella view
        }
        else{
            ArrayList<String> nomisentieri= new ArrayList<>();
            ArrayList<String> puntiiniziali= new ArrayList<>();
            ArrayList<Integer> difficoltasentieri= new ArrayList<>();
            float[] duratasentieri = new float[sentierifiltrati.size()];
            int i=0;
            for (Path p: sentierifiltrati){
                nomisentieri.add(p.getNomeSentiero());
                puntiiniziali.add(p.getPuntoIniziale());
                difficoltasentieri.add(p.getDifficolta());
                duratasentieri[i++]=p.getDurata();
            }
            this.resultView(searchView, nomisentieri, puntiiniziali, difficoltasentieri, duratasentieri);
        }
    }

    private ArrayList<Path> getFilteredPaths(float mindiff, float maxdiff, float mindur, float maxdur, String pos, boolean access) {
        ArrayList<Path> sentierifiltrati = new ArrayList<>();
        //query per prendere i sentieri secondo questi campi dal database
        return sentierifiltrati;

    }

    private void resultView(SearchView searchView, ArrayList<String> nomisentieri, ArrayList<String> puntiiniziali, ArrayList<Integer> difficoltasentieri, float[] duratasentieri) {
        Intent i = new Intent(searchView, ResultView.class);
        i.putExtra("Nomi", nomisentieri);
        i.putExtra("PuntiIniziali", puntiiniziali);
        i.putExtra("Difficoltà", difficoltasentieri);
        i.putExtra("Durate", duratasentieri);
        searchView.startActivity(i);
    }

    public void detailView(String title, ResultView resultView) {
        //Query per i dettagli del sentiero
        Path p1 = this.getAllDetailsOfPath(title);
        //sentiero temporaneo per vedere se funziona tutto
        ArrayList<String>coordinate = new ArrayList<>();
        coordinate.add("41.255365 14.035338");
        coordinate.add("41.136097 14.932931");
        p1 = new Path(title,coordinate, "40.956116 14.530439",3, 3.5f,"che buona questa cadrega",true,null,"utente1");
        Intent i = new Intent(resultView, DetailView.class);
        i.putExtra("nomesentiero", p1.getNomeSentiero());
        i.putExtra("coordinate", p1.getCoordinate());
        i.putExtra("puntoiniziale", p1.getPuntoIniziale());
        i.putExtra("difficolta", p1.getDifficolta());
        i.putExtra("durata", p1.getDurata());
        i.putExtra("descrizione", p1.getDescrizione());
        i.putExtra("accessibilita", p1.isAccessibilitaDisabili());
        i.putExtra("datamodifica", p1.getDataModifica());
        i.putExtra("creatore", "utente1");
        resultView.startActivity(i);
    }

    public Path getAllDetailsOfPath(String nomesentiero){
        //chiamata  al db che restituisce i dettagli del sentiero secondo nomesentiero
        return new Path();
    }
}

package Controller;

import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.natour2021.R;

import DAO.*;

import Entity.*;

import Login.*;
import Login.ui.home.*;
import Login.ui.playlist.*;
import Login.ui.settings.*;
import Login.ui.MyPath.*;
import Playlist.*;
import Search.*;
import Create.*;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Controller {
    PathDAO pathDAO;


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
        //Cose per retrofit, poi nel caso le togliamo da qua
        Retrofit retrofit = RetrofitIstance.getIstanza();
        pathDAO = retrofit.create(PathDAO.class);

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

    public MapViewFragment openInsertPath(CreateView createView){
        FragmentManager fragmentManager = createView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapViewFragment mapViewFragment = new MapViewFragment();
        fragmentTransaction.add(R.id.mapViewContainer, mapViewFragment, null);
        fragmentTransaction.commit();
        return mapViewFragment;
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

    public void createPath(CreateView createView, String nome, String descrizione, float durata, int difficolta, boolean access, String puntoiniziale, String coordinate) {
        // RICORDA DI CAMBIARE IL CREATORE
        Path p = new Path(nome, coordinate, puntoiniziale, difficolta, descrizione, access, "creatorerandom",durata);
        Log.i("nome",nome);
        Log.i("punto ",coordinate);
        Log.i("descrizione",descrizione);
        Log.i("durata", String.valueOf(durata));
        Log.i("difficolta",String.valueOf(difficolta));
        Log.i("access", String.valueOf(access));
        Log.i("puntoinziiale",puntoiniziale);

        Path tmpPath = new Path( nome, coordinate, puntoiniziale, difficolta, descrizione, access, "antonio", durata);

        Call<Path> call = pathDAO.insertPath(tmpPath);

        call.enqueue(new Callback<Path>() {
                         @Override
                         public void onResponse(Call<Path> call, Response<Path> response) {
                             createView.finish();
                         }

                         @Override
                         public void onFailure(Call<Path> call, Throwable t) {
                         }
                     });
    }

    private void searchPaths(SearchView searchView, ArrayList<Path> sentieritrovati) {
        if(sentieritrovati.size()==0){
            Log.i("errore","Non esistono sentieri ");
            //metodo errore nella view
        }
        else{
            ArrayList<String> nomisentieri= new ArrayList<>();
            ArrayList<String> puntiiniziali= new ArrayList<>();
            ArrayList<Integer> difficoltasentieri= new ArrayList<>();
            float[] duratasentieri= new float[sentieritrovati.size()];
            int i=0;
            for (Path p: sentieritrovati){
                nomisentieri.add(p.getNomeSentiero());
                puntiiniziali.add(p.getPuntoIniziale());
                difficoltasentieri.add(p.getDifficolta());
                duratasentieri[i++]=p.getDurata();
            }
            this.resultView(searchView, nomisentieri, puntiiniziali, difficoltasentieri, duratasentieri);
        }
    }

    public void getFilteredPaths(SearchView searchView, float mindur, float maxdur, float mindiff, float maxdiff, String pos, boolean access) {
        /*    accessibilità ricerca     accessiblità del sentiero    risultato
        // bisogna ritornare solo nome sentiero, punto iniziale, durata e difficoltà
            false                        *                              true
            true                         true                           true
            true                         false                          false*/
        //query per prendere i sentieri secondo questi campi dal database
        //il result deve chiamare searchPaths sta subito sopra
        String[] parts = pos.split(" ");

        Path.PathToFilter pathToFilter = new Path.PathToFilter(mindur,maxdur,mindiff,maxdiff, parts[0], parts[1], access);
        Call<ArrayList<Path>> call = pathDAO.getAllFilteredPath(pathToFilter);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> paths = response.body();
                searchPaths(searchView, paths);
            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {

            }
        });

    }

    private void resultView(SearchView searchView, ArrayList<String> nomisentieri, ArrayList<String> puntiiniziali, ArrayList<Integer> difficoltasentieri, float[] duratasentieri) {
        Intent i = new Intent(searchView, ResultView.class);
        i.putExtra("Nomi", nomisentieri);
        i.putExtra("PuntiIniziali", puntiiniziali);
        i.putExtra("Difficoltà", difficoltasentieri);
        i.putExtra("Durate", duratasentieri);
        searchView.startActivity(i);
    }

    private void detailView(ResultView resultView, Path p) {
        Intent i = new Intent(resultView, DetailView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("datamodifica", p.getDataModifica());
        i.putExtra("creatore", p.getCreatore());
        resultView.startActivity(i);
    }


    public void getAllDetailsOfPath(ResultView resultview,String nomeSentiero){
        Path tmpPath = new Path(nomeSentiero);
        Call<Path> call = pathDAO.getPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                Controller c = Controller.getInstance();
                c.detailView(resultview, response.body());
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
            }
        });
    }
}

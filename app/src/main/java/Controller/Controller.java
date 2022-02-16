package Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.natour2021.MainActivity;
import com.example.natour2021.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import DAO.*;

import Entity.*;

import Login.*;
import Login.ui.home.*;
import Login.ui.notification.NotificationFragment;
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
    PlaylistDAO playlistDAO;
    UtenteDAO utenteDAO;
    ReportDAO reportDAO;
    SharedPreferences sharedPref;


    //Singleton
    private static Controller instance;
    private Controller() {}
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return(instance);
    }

    private void bigError() {

    }

    public void logincheck(MainActivity mainActivity){
        Retrofit retrofit = RetrofitIstance.getIstanza();
        pathDAO = retrofit.create(PathDAO.class);
        utenteDAO = retrofit.create(UtenteDAO.class);
        reportDAO = retrofit.create(ReportDAO.class);
        playlistDAO = retrofit.create(PlaylistDAO.class);

        sharedPref= mainActivity.getSharedPreferences(String.valueOf(R.string.preference_file_key),Context.MODE_PRIVATE);
        String email = sharedPref.getString(String.valueOf(R.string.logged_email),"");

        if(!email.equals("")) {
            Amplify.Auth.signIn(
                    email,
                    "",
                    result -> {
                        Intent i = new Intent(mainActivity, HomeView.class);
                        mainActivity.startActivity(i);
                        mainActivity.finish();
                    },
                    error -> {
                        loginView(mainActivity);
                    }
            );
        }
        else loginView(mainActivity);
    }


    public void loginView(MainActivity mainActivity){
        Intent i = new Intent(mainActivity, LoginView.class);
        mainActivity.startActivity(i);
        mainActivity.finish();
    }


    public void userLogin(LoginView loginView, String email, String password){
        Amplify.Auth.signIn(
                email,
                password,
                result -> {
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString(String.valueOf(R.string.logged_email),email);
                    editor.apply();

                    Intent i = new Intent(loginView, HomeView.class);
                    loginView.startActivity(i);
                    loginView.finish();
                },
                error -> {
                    loginView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(loginView, "Credenziali Errate", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        );
    }

    public void getMail(LoginView loginView) { ;
        Amplify.Auth.fetchUserAttributes(
                attributes -> Controller.getInstance().logintoDB(attributes.get(0).toString()),
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
    }

    public void logintoDB(String email){
        Log.i("email",email);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.logged_email),email);
        editor.apply();

        /*Intent i = new Intent(loginView, HomeView.class);
        loginView.startActivity(i);
        loginView.finish();*/
    }

    public void userRegistration(LoginView loginView){
        Intent i = new Intent(loginView, RegistrationView.class);
        loginView.startActivity(i);
    }

    public void registraUtente(RegistrationView registrationView, String email, String password){
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> Controller.getInstance().openRegistrationConfirmFragment(email, registrationView),
                error -> {
                    Log.i("Erroreee!!", error.getMessage());
                    registrationView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(registrationView, "Errore nella registrazione: "+error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
        );
    }

    public void openRegistrationConfirmFragment(String email, RegistrationView registrationView){
        FragmentManager fragmentManager = registrationView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegistrationConfirmFragment registrationConfirmFragment = new RegistrationConfirmFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        registrationConfirmFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.registrationFrameLayout, registrationConfirmFragment, null);
        fragmentTransaction.commit();
    }

    public void confermaRegistrazione(String codice, FrameLayout frameLayout, RegistrationView registrationView, String email){
        Amplify.Auth.confirmSignUp(
                email,
                codice,
                result -> {
                    Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                    insertUser(email, frameLayout, registrationView);
                },
                error -> {
                    registrationView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(registrationView, "Codice sbagliato", Toast.LENGTH_LONG).show();
                        }
                    });
                }
        );
    }


    //inserimento user all'interno del db
    public void insertUser(String email, FrameLayout frameLayout, RegistrationView registrationView){
        User user = new User(email, null);
        Call<User> call = utenteDAO.insertUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body().getEmail()!=null) {
                    Controller.getInstance().createPlaylistUser(email, frameLayout, registrationView);
                }
                else{
                    Toast.makeText(registrationView, "Registrazione eseguita con successo", Toast.LENGTH_LONG).show();
                    cleanFragment(frameLayout);
                    registrationView.finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("errore user", "errore user");
            }
        });
    }

    //creazione delle playlist dell'utente
    public void createPlaylistUser(String email, FrameLayout frameLayout, RegistrationView registrationView){
        User user = new User(email, null);
        Call<Playlist> call = playlistDAO.createPlaylistOfUser(user);

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                Toast.makeText(registrationView, "Registrazione eseguita con successo", Toast.LENGTH_LONG).show();
                cleanFragment(frameLayout);
                registrationView.finish();
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.i("errore user", "errore user");
            }
        });
    }

    public void logout(FrameLayout frameLayout, HomeView homeview){
        Amplify.Auth.signOut(
                () -> {
                    homeview.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(homeview, "Logout effettuato con successo", Toast.LENGTH_LONG).show();
                        }
                    });
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.logged_email),"");
        editor.apply();
        cleanFragment(frameLayout);
        Intent intent = new Intent(homeview,LoginView.class);
        homeview.startActivity(intent);
        homeview.finish();
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
        Log.i("msg",sharedPref.getString(String.valueOf(R.string.logged_email),""));

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

    public void getPathOfPlaylist(PlaylistFragment playlistFragment, String nomePlaylist){

    //SOSTITUIRE QUESTO CEATORE CON QUELLO NELLE SHARED
        String creatorePlaylist = sharedPref.getString(String.valueOf(R.string.logged_email),"");

        AssPlaylistSentiero assPlaylistSentiero = new AssPlaylistSentiero(nomePlaylist, creatorePlaylist);
        Call<ArrayList<Path>> call = pathDAO.getPathsOfPlaylist(assPlaylistSentiero);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> paths = response.body();
                if(paths.size()!=0) {
                    openPlayListView(playlistFragment, paths, nomePlaylist);
                }
                else playlistFragment.playlistvuota(nomePlaylist);

            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {

            }
        });
    }

    public void openPlayListView(PlaylistFragment playlistFragment, ArrayList<Path> pathOfPlaylist, String nomePlaylist){
        ArrayList<String> nomiSentieri = new ArrayList<>();
        ArrayList<String> durdiffSentieri= new ArrayList<>();
        for(Path p:pathOfPlaylist) {
            nomiSentieri.add(p.getNomeSentiero());
            durdiffSentieri.add("  Durata: "+p.getDurata()+"  Difficoltà: "+p.getDifficolta());
        }
        Intent i = new Intent(playlistFragment.getActivity(), PlaylistView.class);
        i.putExtra("nomiSentieri",nomiSentieri);
        i.putExtra("durdiffSentieri",durdiffSentieri);
        i.putExtra("nomePlaylist",nomePlaylist);
        playlistFragment.startActivity(i);
    }



    public void getAllDetailsOfPlaylistPath(PlaylistView playlistView,String nomePlaylist ,String nomeSentiero){
        Path tmpPath = new Path(nomeSentiero);
        Call<Path> call = pathDAO.getPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null&&response.body().getNomeSentiero().equals(nomeSentiero)) {
                    Controller c = Controller.getInstance();
                    c.openPlaylistDetailsView(playlistView, nomePlaylist, response.body());
                }
                else{
                    Controller c = Controller.getInstance();
                    c.bigError();
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
            }
        });
    }

    public void openPlaylistDetailsView(PlaylistView playlistView, String playlistName, Path p){
        Intent i = new Intent(playlistView, PlaylistDetailsView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("datamodifica", p.getDataModifica());
        i.putExtra("creatore", p.getCreatore());
        i.putExtra("playlist", playlistName);
        playlistView.startActivity(i);
    }


    public void getPersonalPathOfPlaylist(PersonalPlaylistView personalPlaylistView){
    //SOSTITUIRE CREATORE CON SHARED
        String creatore = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        User tmpUser = new User(creatore, null);
        Call<ArrayList<Path>> call = pathDAO.getPersonalPathsOfPlaylist(tmpUser);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> path = response.body();

                ArrayList<String> Sentieri = new ArrayList<>();
                for(Path p:path){
                    Sentieri.add(p.getNomeSentiero());
                }
                personalPlaylistView.setPersonalList(Sentieri);
            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {

            }
        });


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
        Path path = new Path("sentiero2");
        Call<Path> call = pathDAO.getAllDetailsOfPersonalPath(path);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                Path resPath = response.body();
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
            }
        });

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
        String creatore = sharedPref.getString(String.valueOf(R.string.logged_email),"");

        Path tmpPath = new Path( nome, coordinate, puntoiniziale, difficolta, descrizione, access, creatore, durata);
        Call<Path> call = pathDAO.insertPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body().getNomeSentiero()!=null){
                createView.finish();
                }
                else createView.errore();
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
            }
        });
    }

    private void searchPaths(SearchView searchView, ArrayList<Path> sentieritrovati) {
        ArrayList<String> nomisentieri = new ArrayList<>();
        ArrayList<String> puntiiniziali = new ArrayList<>();
        ArrayList<Integer> difficoltasentieri = new ArrayList<>();
        float[] duratasentieri = new float[sentieritrovati.size()];
        int i=0;
        for (Path p: sentieritrovati){
            nomisentieri.add(p.getNomeSentiero());
            puntiiniziali.add(p.getPuntoIniziale());
            difficoltasentieri.add(p.getDifficolta());
            duratasentieri[i++]=p.getDurata();
        }
        this.resultView(searchView, nomisentieri, puntiiniziali, difficoltasentieri, duratasentieri);
    }

    public void getFilteredPaths(SearchView searchView, float mindur, float maxdur, float mindiff, float maxdiff, String pos, boolean access) {
        String[] parts = pos.split(" ");
        // ricorda di cambiare la lambda da 1 e 1.5 a 0.75 e 1
        Path.PathToFilter pathToFilter = new Path.PathToFilter(mindur,maxdur,mindiff,maxdiff, parts[0], parts[1], access);
        Call<ArrayList<Path>> call = pathDAO.getAllFilteredPath(pathToFilter);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> paths = response.body();
                if(paths != null&& paths.size()!=0) {
                    searchPaths(searchView, paths);
                }
                else{
                    searchView.errore();
                }
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
                if(response.body()!=null&&response.body().getNomeSentiero().equals(nomeSentiero)) {
                    Controller c = Controller.getInstance();
                    c.detailView(resultview, response.body());
                }
                else{
                    Controller c = Controller.getInstance();
                    c.bigError();
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
            }
        });
    }



    public void reportPath(DetailView detailView, String nomesentiero, String motivazione, String segnalato) {
        String segnalante = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        Report report = new Report(1,motivazione, nomesentiero,segnalante,segnalato);
        Call<Report> call = reportDAO.reportPath(report);

        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                Report tmpReport = response.body();
                if(tmpReport.getNomeSentiero().equals(nomesentiero)){
                    // il report è stato aggiunto. fare qualcosa
                }else{
                    // il report non è stato aggiunto. fare qualcosa
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {

            }
        });
    }

    public void addPathToPlaylist(DetailView detailView, String nomesentiero, String nomePlaylist) {
        String creatorePlaylist = sharedPref.getString(String.valueOf(R.string.logged_email),"");

        //log di prova per vedere se passa roba giusta
        Log.i("prova",nomesentiero);
        Log.i("prova",nomePlaylist);
        //Insert nel DB nella tabella di mezzo tra sentiero e playlist, non so come
        //praticamente c'è la tabella dove hai sentiero playlist e creatore della playlist
        //il creatore chiamalo "utente" che come sopra dobbiamo prenderlo dall'utenteloggato
        //Questa deve ritornare qualcosa perchè se l'insert non va vuol dire che ci sta un problema grave
        // te la lascio vuota perchè non so cosa scriverci, nel caso fai la call e il corpo lo metto io se serve
    }

    public void getNotification(NotificationFragment notificationFragment) {
    }


}

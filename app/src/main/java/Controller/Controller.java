package Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    PlaylistDAO playlistDAO;
    UtenteDAO utenteDAO;


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

    public void loginView(MainActivity mainActivity){
        Retrofit retrofit = RetrofitIstance.getIstanza();
        pathDAO = retrofit.create(PathDAO.class);
        utenteDAO = retrofit.create(UtenteDAO.class);
        //reportDAO = retrofit.create(ReportDAO.class);
        playlistDAO = retrofit.create(PlaylistDAO.class);
        //String utenteloggato = prendi dalle preferencies;
        //controllo bla lba

        Intent i = new Intent(mainActivity, LoginView.class);
        mainActivity.startActivity(i);
        mainActivity.finish();
    }


    public void userLogin(LoginView loginView){

        Amplify.Auth.signIn(
                "antoniodig2017@gmail.com",
                "",
                result -> {
                    Intent i = new Intent(loginView, HomeView.class);
                    loginView.startActivity(i);
                },
                error -> {
                    //fare toast "credenziali sbagliate"
                }
        );

    }

    public void userRegistration(LoginView loginView){
        Intent i = new Intent(loginView, RegistrationView.class);
        loginView.startActivity(i);
    }

    public void registraUtente(RegistrationView registrationView, String email, String password){
        //Codice di cognito per la registrazione e il mandamento del codice
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result.toString());
                    //inserimento user all'interno del db
                    insertUser(email);
                },
                error -> Log.i("Erroreee!!", error.getMessage())
        );

        FragmentManager fragmentManager = registrationView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RegistrationConfirmFragment registrationConfirmFragment = new RegistrationConfirmFragment();
        fragmentTransaction.add(R.id.registrationFrameLayout, registrationConfirmFragment, null);
        fragmentTransaction.commit();
    }

    public void confermaRegistrazione(String codice, FrameLayout frameLayout, RegistrationView registrationView){
        //controllo codice di cognito, se è giusto una cosa, se è sbagliato l'altra, mo metto n'if strano
        String email = "antoniodig2017@gmail.com";

        Amplify.Auth.confirmSignUp(
                email,
                codice,
                result -> {
                    Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                    createPlaylistUser(email);
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
        }
        );
    }

    //inserimento user all'interno del db
    public void insertUser(String email){
        User user = new User(email, null);
        Call<User> call = utenteDAO.insertUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("Creato user", "Creato user");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("errore user", "errore user");
            }
        });
    }

    //creazione delle playlist dell'utente
    public void createPlaylistUser(String email){
        User user = new User(email, null);
        Call<Playlist> call = playlistDAO.createPlaylistOfUser(user);

        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                Log.i("Creato user", "Creato user");
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Log.i("errore user", "errore user");
            }
        });
    }

    //DA VEDERE COME RISOLVERE
    public void printMessageRegistration(RegistrationView registrationView, FrameLayout frameLayout, int res){
        if(res == 1){
            Toast.makeText(registrationView,"Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show();
            frameLayout.removeAllViews();
            registrationView.finish();
        }
        else {
            Toast.makeText(registrationView,"Codice Errato!", Toast.LENGTH_SHORT).show();
        }
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

    public void getPathOfPlaylist(PlaylistFragment playlistFragment, String nomePlaylist){


    //SOSTITUIRE QUESTO CEATORE CON QUELLO NELLE SHARED
        String creatorePlaylist = "antoniodig2017@gmail.com";

        AssPlaylistSentiero assPlaylistSentiero = new AssPlaylistSentiero(nomePlaylist, creatorePlaylist);
        Call<ArrayList<Path>> call = pathDAO.getPathsOfPlaylist(assPlaylistSentiero);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> paths = response.body();
                openPlayListView(playlistFragment,paths, nomePlaylist);

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

        Path p1 = new Path("Sentiero prova", 5, 2);
        Path p2 = new Path("Sentiero30", 5, 2);
        Path p3 = new Path("Sentiero31", 5, 2);
        Path p4 = new Path("Sentiero2", 5, 2);

        ArrayList<Path> path = new ArrayList<>();
        //path.add(p1);
        //path.add(p2);path.add(p3);path.add(p4);

        ArrayList<String> Sentieri = new ArrayList<>();
        for(Path p:path){
            Sentieri.add(p.getNomeSentiero());
        }

        //Scrivere codice che si collega al db per prendere i sentieri della playlist
        personalPlaylistView.setPersonalList(Sentieri);
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
        // Ricorda di prendere utente loggato

        Path tmpPath = new Path( nome, coordinate, puntoiniziale, difficolta, descrizione, access, "antonio", durata);
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
        //Insert nel Database per il report ricorda di cambiare l'utente in utenteloggato
        //log di prova per vedere se passa roba giusta
        // l'id del report l'ho messo a caso perchè giustamente se lo dovrebbe prendere il db
        //il segnalante chiamalo "utente" che come sopra dobbiamo prenderlo dall'utenteloggato
        // Ricorda di cambiare a unique key motivazione, nomesentiero, segnalante e segnalato
        Log.i("prova",nomesentiero);
        Log.i("prova",motivazione);
        Log.i("prova",segnalato);
        Report report = new Report(1,motivazione, nomesentiero,"utente",segnalato);
    }

    public void addPathToPlaylist(DetailView detailView, String nomesentiero, String nomePlaylist) {
        //log di prova per vedere se passa roba giusta
        Log.i("prova",nomesentiero);
        Log.i("prova",nomePlaylist);
        //Insert nel DB nella tabella di mezzo tra sentiero e playlist, non so come
        //praticamente c'è la tabella dove hai sentiero playlist e creatore della playlist
        //il creatore chiamalo "utente" che come sopra dobbiamo prenderlo dall'utenteloggato
        //Questa deve ritornare qualcosa perchè se l'insert non va vuol dire che ci sta un problema grave
        // te la lascio vuota perchè non so cosa scriverci, nel caso fai la call e il corpo lo metto io se serve
    }
}

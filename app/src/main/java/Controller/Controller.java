package Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amplifyframework.auth.AuthProvider;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.natour2021.MainActivity;
import com.example.natour2021.R;


import DAO.*;
import Entity.*;
import Login.*;
import Login.ui.home.*;
import Login.ui.notification.*;
import Login.ui.playlist.*;
import Login.ui.settings.*;
import Login.ui.MyPath.*;
import Playlist.*;
import Search.*;
import Create.*;
import Admin.*;

import Exception.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Controller {
    private PathDAO pathDAO;
    private AssPlaylistSentieroDAO assPlaylistSentieroDAO;
    private UserDAO userDAO;
    private ReportDAO reportDAO;
    private SharedPreferences sharedPref;
    private LoginView loginView;
    private NotificationFragment notificationFragment;
    private PersonalDetailView personalDetailView;
    private PlaylistView playlistView;
    private PersonalPlaylistFragment personalPlaylistFragment;

    //METODI GENERALI

    //Singleton
    private static Controller instance;
    public Controller() {}
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return(instance);
    }

    private void bigError(Activity activity) {
        Log.i("ERROR","BIGERROR");
        if(activity!=null){
            Log.i("msg", activity.toString());
            Toast.makeText(activity,"Errore gravissimo, contatta gli amministratori",Toast.LENGTH_LONG).show();
        }
    }

    public void cleanFragment(FrameLayout frameLayout){
        frameLayout.removeAllViews();
    }

    //LOGIN

    public void setLoginView(LoginView loginView) {
        this.loginView=loginView;
    }


    public void logincheck(MainActivity mainActivity){

        Retrofit retrofit = RetrofitIstance.getIstanza();
        pathDAO = retrofit.create(PathDAO.class);
        userDAO = retrofit.create(UserDAO.class);
        reportDAO = retrofit.create(ReportDAO.class);
        assPlaylistSentieroDAO = retrofit.create(AssPlaylistSentieroDAO.class);

        sharedPref= mainActivity.getSharedPreferences(String.valueOf(R.string.preference_file_key),Context.MODE_PRIVATE);
        String email = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        String tipo = sharedPref.getString(String.valueOf(R.string.tipo_login),"");

        Log.i("Tipo login", tipo);

        switch(tipo){
            case "normale":
                Amplify.Auth.signIn(
                        email,
                        "",
                        result -> {
                            Intent i = new Intent(mainActivity, HomeView.class);
                            mainActivity.startActivity(i);
                            mainActivity.finish();
                        },
                        error -> {
                            openloginView(mainActivity);
                        }
                );
                break;
            case "google":
                Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), mainActivity,
                        result -> {
                            Intent i = new Intent(mainActivity, HomeView.class);
                            mainActivity.startActivity(i);
                            mainActivity.finish();
                        },
                        error -> openloginView(mainActivity)
                );
                break;
            case "facebook":
                Amplify.Auth.signInWithSocialWebUI(AuthProvider.facebook(), mainActivity,
                        result -> {
                            Intent i = new Intent(mainActivity, HomeView.class);
                            mainActivity.startActivity(i);
                            mainActivity.finish();
                        },
                        error -> openloginView(mainActivity)
                );
                break;
            default: openloginView(mainActivity);

        }
    }


    public void openloginView(MainActivity mainActivity){
        Intent i = new Intent(mainActivity, LoginView.class);
        mainActivity.startActivity(i);
        mainActivity.finish();
    }


    public void userLogin(LoginView loginView, String email, String password){
        Amplify.Auth.signIn(
                email,
                password,
                result -> Controller.getInstance().openHomeView(loginView, email),
                error -> {
                    loginView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(loginView, "Credenziali Errate", Toast.LENGTH_SHORT).show();
                            Log.e("Error: ", " Credenziali Errate");
                        }
                    });
                }
        );
    }

    public void logintoDB(String tipo,List<AuthUserAttribute> attributes){
        String email = attributes.get(3).getValue();
        SharedPreferences.Editor editor = sharedPref.edit();
        insertUser(email);
        editor.putString(String.valueOf(R.string.logged_email),email);
        editor.putString(String.valueOf(R.string.tipo_login),tipo);
        editor.apply();
        Intent i = new Intent(loginView, HomeView.class);
        loginView.startActivity(i);
        loginView.finish();
    }


    //CAMBIO PASSWORD


    public PasswordOverlay openForgotPasswordOverlay(LoginView loginView){
        FragmentManager fragmentManager = loginView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PasswordOverlay passwordOverlay = new PasswordOverlay();
        fragmentTransaction.add(R.id.passwordOverlayContainer, passwordOverlay, null);
        fragmentTransaction.commit();
        return passwordOverlay;
    }

    public void resetPassword(String email){
        Amplify.Auth.resetPassword(
                email,
                result -> Toast.makeText(loginView, "Codice per il reset della password inviato alla mail",Toast.LENGTH_LONG).show(),
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    error.getClass().getSimpleName();
                    switch(error.getClass().getSimpleName()){
                        case "LimitExceededException":
                            Toast.makeText(loginView, "Hai provato a fare il reset della password troppe volte, sarai bloccato per 24 ore dal riprovare",Toast.LENGTH_LONG).show();
                            Log.e("Error: ", " reset troppe volte");
                            break;
                        case "UserNotFoundException":
                            Toast.makeText(loginView, "Utente non registrato",Toast.LENGTH_LONG).show();
                            Log.e("Error: ", "Utente non registrato");
                            break;
                        default:
                            Toast.makeText(loginView, "Errore grave, contatta gli amministratori",Toast.LENGTH_LONG).show();
                            Log.e("Error: ", "Errore grave, contatta gli amministratori");
                    }
                }
        );
    }

    public void confirmResetPassword(String password, String codice){
        Amplify.Auth.confirmResetPassword(
                password,
                codice,
                () -> {
                    Controller.getInstance().cleanFragment(loginView.findViewById(R.id.passwordOverlayContainer));
                    loginView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(loginView, "Password Cambiata con successo", Toast.LENGTH_SHORT).show();
                            Log.i("Success: ", "Password cambiata con successo");
                        }
                    });
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    Log.i("errore",error.getClass().getSimpleName());
                    switch(error.getClass().getSimpleName()){
                        case "AuthException":
                            loginView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginView, "Devi prima farti mandare il codice sopra",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Mandare prima codice");
                                }
                            });
                            break;
                        case "CodeMismatchException":
                            loginView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginView, "Codice errato",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Codice errato");
                                }
                            });
                            break;
                        case "InvalidPasswordException":
                        case "InvalidParameterException":
                            loginView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginView, "La password deve essere almeno di 8 caratteri",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Password almeno 8 caratteri");
                                }
                            });
                            break;
                        case "LimitExceededException":
                            loginView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginView, "Hai provato troppe volte a cambiare la password, sarai bloccato per 24 ore dal riprovare",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Provato troppe volte");
                                }
                            });
                            break;
                        default:
                            loginView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginView, "Errore grave, contatta gli amministratori",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Errore grave, contatta gli amministratori");
                                }
                            });
                    }
                }
        );
    }

    //REGISTRAZIONE

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
            Log.e("AuthQuickstart", error.toString());
                    error.getClass().getSimpleName();
                    switch(error.getClass().getSimpleName()){
                        case"InvalidPasswordException":
                            registrationView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(registrationView, "La password deve essere di 8 caratteri",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Password almeno 8 caratteri");
                                }
                            });
                            break;
                        case "InvalidParameterException":
                            registrationView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(registrationView, "Email o password non validi",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Email o password non validi");
                                }
                            });
                            break;
                        case "UsernameExistsException":
                            registrationView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(registrationView, "Email già registrata",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Email già registrata");
                                }
                            });
                            break;
                        default:
                            registrationView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(registrationView, "Errore grave, contatta gli amministratori",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Errore grave, contatta gli amministratori");
                                }
                            });
                }
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
                            Log.e("Error: ", "Codice sbagliato");
                        }
                    });
                }
        );
    }


    //INSERIMENTO DELL'UTENTE E DELLE PLAYLIST NEL DB


    private void insertUser(String email, FrameLayout frameLayout, RegistrationView registrationView){
        User user = new User(email);
        Call<User> call = userDAO.insertUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Toast.makeText(registrationView, "Registrazione eseguita con successo", Toast.LENGTH_LONG).show();
                Log.i("Success: ", "Registrazione eseguita con successo");
                cleanFragment(frameLayout);
                registrationView.finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                bigError(registrationView);
                Log.e("BigError: ", "Errore inserimento utente nel db");
            }
        });
    }

    private void insertUser(String email){
        User user = new User(email);
        Call<User> call = userDAO.insertUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("Success: ", "Registrazione avvenuta con successo");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                bigError(loginView);
                Log.e("BigError: ", "Errore inserimento utente nel db");
            }
        });
    }

    //HOMEVIEW

    public void openHomeView(LoginView loginview, String email){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.logged_email),email);
        editor.putString(String.valueOf(R.string.tipo_login),"normale");
        editor.apply();

        Intent i = new Intent(loginView, HomeView.class);
        loginview.startActivity(i);
        loginview.finish();
    }

    //SETTINGS


    public LogoutFragment logoutOverlay(HomeView homeview){
        FragmentManager fragmentManager = homeview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogoutFragment logoutoverlay = new LogoutFragment();
        fragmentTransaction.add(R.id.LogoutContainer, logoutoverlay, null);
        fragmentTransaction.commit();
        return logoutoverlay;
    }


    public void logout(FrameLayout frameLayout, HomeView homeView){
        Amplify.Auth.signOut(
                () -> {
                    homeView.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(homeView, "Logout effettuato con successo", Toast.LENGTH_LONG).show();
                            Log.i("Success: ", "Logout effettuato con successo");
                        }
                    });
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(R.string.logged_email),"");
        editor.putString(String.valueOf(R.string.tipo_login),"");
        editor.apply();
        cleanFragment(frameLayout);
        Intent intent = new Intent(homeView,LoginView.class);
        homeView.startActivity(intent);
        homeView.finish();
    }

    public ChangePasswordFragment changePasswordOverlay(HomeView homeView) {
        FragmentManager fragmentManager = homeView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        fragmentTransaction.add(R.id.PasswordContainer, changePasswordFragment, null);
        fragmentTransaction.commit();
        return changePasswordFragment;
    }

    public void changePassword(FrameLayout frameLayout ,HomeView homeView, String oldPassword, String newPassword){
        Amplify.Auth.updatePassword(
                oldPassword,
                newPassword,
                () -> {
                    homeView.runOnUiThread(new Runnable() {
                        public void run() {
                            cleanFragment(frameLayout);
                            Toast.makeText(homeView, "Password cambiata con successo", Toast.LENGTH_LONG).show();
                            Log.i("Success: ", "Password cambiata con successo");
                        }
                    });
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    error.getClass().getSimpleName();
                    switch(error.getClass().getSimpleName()){
                        case"InvalidPasswordException":
                        case"InvalidParameterException":
                            homeView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(homeView, "La password deve essere di 8 caratteri",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Password almeno di 8 caratteri");
                                }
                            });
                            break;
                        case "LimitExceededExcepion":
                            homeView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(homeView, "Hai provato a cambiare troppe volte la password senza successo, sarai bloccato per 24 ore",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Provato a cambiare password troppe volte");
                                }
                            });
                            break;
                        case "NotAuthorizedException":
                            homeView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(homeView, "Password vecchia sbagliata",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Password vecchia sbagliata");
                                }
                            });
                            break;
                        default:
                            homeView.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(homeView, "Errore grave, contatta gli amministratori",Toast.LENGTH_LONG).show();
                                    Log.e("Error: ", "Errore grave, contatta gli amministratori");
                                }
                            });
                    }
                }
        );
    }

    //NOTIFICHE


    public void getNotification(NotificationFragment notificationFragment) {
        String creatore = sharedPref.getString(String.valueOf(R.string.logged_email),"");

        Report report = new Report(0, null,null,null,null, creatore);
        Call<ArrayList<Report>> call = reportDAO.getNotification(report);
        call.enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                ArrayList < Report > notifiche = response.body();
                ArrayList<String> descrizioniNotifiche = new ArrayList<>();
                ArrayList<String> nomiSentieriNotifiche = new ArrayList<>();
                ArrayList<Integer> notificheID = new ArrayList<>();
                for(Report r:notifiche){
                    nomiSentieriNotifiche.add(r.getNomeSentiero());
                    descrizioniNotifiche.add(r.getDescrizione());
                    notificheID.add(r.getIdSegnalazione());
                }
                notificationFragment.setNotification(nomiSentieriNotifiche,descrizioniNotifiche,notificheID);
            }

            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                bigError(notificationFragment.getActivity());
                Log.e("BigError: ", "errore recupero notifiche");
            }
        });
    }


    public AnswerReportFragment openAnswerReportOverlay(String nomesentiero, String descrizione, Integer id, HomeView homeView, NotificationFragment notificationFragment){
        FragmentManager fragmentManager = homeView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AnswerReportFragment answerReportFragment = new AnswerReportFragment();
        this.notificationFragment= notificationFragment;
        Bundle bundle = new Bundle();
        bundle.putString("nomesentiero",nomesentiero);
        bundle.putString("descrizione",descrizione);
        bundle.putInt("id",id);
        answerReportFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.reportAnswerLayout,answerReportFragment,null);
        fragmentTransaction.commit();
        return answerReportFragment;
    }


    public void rispondiSegnalazione(int idnotifica, String risposta) {
        Report report = new Report(idnotifica, risposta);
        Call<Report>  call = reportDAO.addReply(report);
        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if(response.body()!=null){
                    notificationFragment.removeNotification(idnotifica);
                    Toast.makeText(notificationFragment.getContext(), "Risposta inviata con successo",Toast.LENGTH_LONG).show();
                    Log.e("Error: ", "Risposta inviata con successo");
                }
                else{
                    Toast.makeText(notificationFragment.getContext(), "Errore nella risposta, contatta gli amministratori",Toast.LENGTH_LONG).show();
                    Log.e("Error: ", "Errore nella risposta, contatta gli amministratori");
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                bigError(notificationFragment.getActivity());
                Log.e("BigError: ", "errore aggiunta playlist");
            }
        });

    }

    //PLAYLIST

    public void getPathOfPlaylist(PlaylistFragment playlistFragment, String nomePlaylist){
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
                else {
                    Toast.makeText(playlistFragment.getContext(), "Non ci sono sentieri nella playlist " + nomePlaylist, Toast.LENGTH_LONG).show();
                    Log.i("Error: ", "Playlist vuota");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {
                bigError(playlistFragment.getActivity());
                Log.e("BigError: ", "errore aggiunta playlist");
            }
        });
    }

    private void openPlayListView(PlaylistFragment playlistFragment, ArrayList<Path> pathOfPlaylist, String nomePlaylist){
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
                if(response.body()!=null) {
                    Controller c = Controller.getInstance();
                    c.openPlaylistDetailsView(playlistView, nomePlaylist, response.body());
                }
                else{
                    Toast.makeText(playlistView,"Errore nel recupero dei dettagli del sentiero, ricarica la playlist",Toast.LENGTH_LONG).show();
                    Log.e("Error","Errore nel recupero dei dettagli del sentiero");
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(playlistView);
                Log.e("BigError","Errore nel recupero dei dettagli");
            }
        });
    }

    private void openPlaylistDetailsView(PlaylistView playlistView, String playlistName, Path p){
        Intent i = new Intent(playlistView, PlaylistDetailsView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("creatore", p.getCreatore());
        i.putExtra("playlist", playlistName);
        this.playlistView=playlistView;
        playlistView.startActivity(i);
    }

    public removeFromPlaylistOverlay removeFromPlaylistOverlay(PlaylistDetailsView playlistdetailsview, String nomesentiero, String nomeplaylist){
        FragmentManager fragmentManager = playlistdetailsview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        removeFromPlaylistOverlay removefromplaylistoverlay = new removeFromPlaylistOverlay();
        Bundle bundle = new Bundle();
        bundle.putString("nomesentiero",nomesentiero);
        bundle.putString("nomeplaylist",nomeplaylist);
        removefromplaylistoverlay.setArguments(bundle);
        fragmentTransaction.add(R.id.removeFromPlaylistContainer, removefromplaylistoverlay, null);
        fragmentTransaction.commit();
        return removefromplaylistoverlay;
    }

    public void removeFromPlaylist(String nomeSentiero, String nomeplaylist, PlaylistDetailsView playlistDetailsView){
        String creatorePlaylist = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        AssPlaylistSentiero assPlaylistSentiero = new AssPlaylistSentiero(nomeplaylist, creatorePlaylist, nomeSentiero);
        Call<AssPlaylistSentiero> call = assPlaylistSentieroDAO.removeFromPlaylist(assPlaylistSentiero);

        call.enqueue(new Callback<AssPlaylistSentiero>() {
            @Override
            public void onResponse(Call<AssPlaylistSentiero> call, Response<AssPlaylistSentiero> response) {
                if (response.body()!=null) {
                    playlistDetailsView.finish();
                    playlistView.removePathView(nomeSentiero);
                    Log.i("Success","Rimosso sentiero dalla playlist");
                }
                else {
                    Toast.makeText(playlistDetailsView, "Errore nella cancellazione del sentiero dalla playlist, ricarica la playlist", Toast.LENGTH_LONG).show();
                    Log.e("Success", "Errore nella cancellazione del sentiero dalla playlist");
                }
            }
            @Override
            public void onFailure(Call<AssPlaylistSentiero> call, Throwable t) {
                bigError(playlistDetailsView);
                Log.e("BigError","Errore nella cancellazione del sentiero dalla playlist");
            }
        });

    }

    //SENTIERI PERSONALI

    public void getPersonalPaths(PersonalPlaylistFragment personalPlaylistFragment){
        this.personalPlaylistFragment = personalPlaylistFragment;
        String creatore = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        User tmpUser = new User(creatore);
        Call<ArrayList<Path>> call = pathDAO.getPersonalPathsOfPlaylist(tmpUser);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> path = response.body();
                ArrayList<String> Sentieri = new ArrayList<>();
                for(Path p:path){
                    Sentieri.add(p.getNomeSentiero());
                }
                personalPlaylistFragment.setPersonalList(Sentieri);
            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {
                bigError(personalPlaylistFragment.getActivity());
                Log.e("BigError","Errore recupero sentieri");
            }
        });
    }

    public void getAllDetailsOfPersonalPath(HomeView homeView,String nomeSentiero){
        Path tmpPath = new Path(nomeSentiero);
        Call<Path> call = pathDAO.getPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null) {
                    Controller c = Controller.getInstance();
                    c.openPersonalDetailsView(homeView, response.body());
                }
                else {
                    bigError(homeView);
                    Log.e("Error", "Errore recupero dettagli sentiero");
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(homeView);
                Log.e("BigError","Errore recupero dettagli sentiero");

            }
        });
    }


    private void openPersonalDetailsView(HomeView homeview, Path p){
        Intent i = new Intent(homeview, PersonalDetailView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("accessibilità",p.isAccessibilita());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("creatore", p.getCreatore());
        i.putExtra("data",p.getDataModifica());
        homeview.startActivity(i);
    }

    //CANCELLAZIONE SENTIERO


    public deletePathOverlay deletePathOverlay(PersonalDetailView personaldetailview, String nomeSentiero){
        FragmentManager fragmentManager = personaldetailview.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        deletePathOverlay deletepathoverlay = new deletePathOverlay();
        Bundle bundle = new Bundle();
        bundle.putString("nomeSentiero",nomeSentiero);
        deletepathoverlay.setArguments(bundle);
        fragmentTransaction.add(R.id.deletePathContainer, deletepathoverlay, null);
        fragmentTransaction.commit();
        return deletepathoverlay;
    }

    public void deletePath(String nomeSentiero, PersonalDetailView personalDetailView) {
        Path path = new Path(nomeSentiero);
        Call<Path> call = pathDAO.deletePath(path);
        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                personalDetailView.finish();
                Toast.makeText(personalPlaylistFragment.getContext(),"Sto ricaricando i sentieri", Toast.LENGTH_LONG).show();
                getPersonalPaths(personalPlaylistFragment);
                Log.i("Success","Sentiero eliminato");
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(personalDetailView);
                Log.e("BigError","Errore eliminazione sentiero");
            }
        });
    }

    //MODIFICA SENTIERO

    public void openModificationView(PersonalDetailView personalDetailView, String nomesentiero, String descrizione, Boolean accessibilità, Float durata, Integer difficolta){
        Intent i = new Intent(personalDetailView, ModificationView.class);
        i.putExtra("nomeSentiero",nomesentiero);
        i.putExtra("descrizione",descrizione);
        i.putExtra("accessibilità",accessibilità);
        i.putExtra("durata",durata);
        i.putExtra("difficoltà",difficolta.intValue());
        personalDetailView.startActivity(i);
        this.personalDetailView = personalDetailView;

    }

    public void updatePath(ModificationView modificationView,String nomeSentiero, String descrizione, float durata, int difficolta, boolean accessibilità) {
        modificationView.finish();
        Path path = new Path(nomeSentiero,  null, null, difficolta, descrizione, accessibilità, null, durata);
        Call<Path> call = pathDAO.updatePath(path);
        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null) {
                    personalDetailView.updatePath(descrizione, durata, difficolta);
                    Log.e("Success","Path modificato");
                }
                else {
                    bigError(modificationView);
                    Log.e("Error", "Errore modifica sentiero");
                }
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(modificationView);
                Log.e("BigError","Errore modifica sentiero");
            }
        });
    }

    //CREAZIONE DEL SENTIERO

    public void openCreatePathView(HomeFragment homeFragment){
        Intent i = new Intent(homeFragment.getActivity(), CreateView.class);
        homeFragment.startActivity(i);
    }

    public MapViewFragment openInsertPath(CreateView createView){
        FragmentManager fragmentManager = createView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapViewFragment mapViewFragment = new MapViewFragment();
        fragmentTransaction.add(R.id.mapViewContainer, mapViewFragment, null);
        fragmentTransaction.commit();
        return mapViewFragment;
    }

    public void checkPath(CreateView createView, String nome, String descrizione, float durata, int difficolta, boolean access, String puntoiniziale, String coordinate, Controller controller) throws NamePathWrongSizeException, PathWrongSizeException, DescriptionWrongSizeException, DurationOutOfRangeException, DifficultyOutOfRangeException {
        if(nome!=null&&nome.length()!=0&&nome.length()<100){
            if(puntoiniziale!=null&&!puntoiniziale.equals("")&&puntoiniziale.length()<50&&coordinate!=null&&coordinate.length()<5000){
                if(descrizione!=null&&descrizione.length()<200){
                    if(durata>=0&&durata<=10){
                        if(difficolta>=0&&difficolta<=10){
                            controller.createPath(createView, nome, descrizione, durata, difficolta, access, puntoiniziale, coordinate);
                        }
                        else throw new DifficultyOutOfRangeException();
                    }
                    else throw new DurationOutOfRangeException();
                }
                else throw new DescriptionWrongSizeException();
            }
            else throw new PathWrongSizeException();
        }
        else throw new NamePathWrongSizeException();
    }

    public void createPath(CreateView createView, String nome, String descrizione, float durata, int difficolta, boolean access, String puntoiniziale, String coordinate) {
        String creatore = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        Path tmpPath = new Path( nome, coordinate, puntoiniziale, difficolta, descrizione, access, creatore, durata);
        Call<Path> call = pathDAO.insertPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body().getNomeSentiero()!=null){
                    Toast.makeText(createView, "Sentiero creato con successo", Toast.LENGTH_LONG).show();
                    Log.i("Success","Sentiero creato con successo");
                    createView.finish();
                }
                else {
                    Toast.makeText(createView, "Esiste già un sentiero con questo nome o questo punto iniziale", Toast.LENGTH_LONG).show();
                    Log.e("Error", "Esiste già un sentiero con questo nome o questo punto iniziale");
                }
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(createView);
                Log.e("BigError","Impossibile creare sentiero");
            }
        });
    }

    //RICERCA SENTIERO

    public void searchView(HomeFragment homeFragment){
        Intent i= new Intent(homeFragment.getActivity(), SearchView.class);
        homeFragment.startActivity(i);
    }

    public void checkFilters(SearchView searchView, float mindur, float maxdur, float mindiff, float maxdiff, String pos, boolean access, Controller controller) throws DifficultyOutOfRangeException, DurationOutOfRangeException, PositionNullException, DurationMinMoreThanMaxException, DifficultyMinMoreThanMaxException {
        if(pos!=null&&!pos.equals("")&&pos.length()<50){
            if(maxdiff>=0&&maxdiff<=10&&mindiff>=0&&mindiff<=10){
                if(mindur>=0&&mindur<=10&&maxdur>=0&&maxdur<=10){
                    if(maxdur>=mindur){
                        if(maxdiff>=mindiff){
                            controller.getFilteredPaths(searchView, mindur, maxdur,  mindiff,  maxdiff,  pos,  access);
                        }
                        else throw new DifficultyMinMoreThanMaxException();
                    }
                    else throw new DurationMinMoreThanMaxException();
                }
                else throw new DurationOutOfRangeException();
            }
            else throw new DifficultyOutOfRangeException();
        }
        else throw new PositionNullException();
    }



    public void getFilteredPaths(SearchView searchView, float mindur, float maxdur, float mindiff, float maxdiff, String pos, boolean access) {
        String[] parts = pos.split(" ");
        Path.PathToFilter pathToFilter = new Path.PathToFilter(mindur,maxdur,mindiff,maxdiff, parts[0], parts[1], access);
        Call<ArrayList<Path>> call = pathDAO.getAllFilteredPath(pathToFilter);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                ArrayList<Path> paths = response.body();
                if(paths != null&& paths.size()!=0) {
                    searchPaths(searchView, paths);
                }
                else {
                    Toast.makeText(searchView,"Non esistono sentieri che corrispondono a questi filtri",Toast.LENGTH_LONG).show();
                    Log.e("Error","Impossibile creare sentiero");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {
                bigError(searchView);
                Log.e("BigError","Errore recupero sentiero filtrati");
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

    private void resultView(SearchView searchView, ArrayList<String> nomisentieri, ArrayList<String> puntiiniziali, ArrayList<Integer> difficoltasentieri, float[] duratasentieri) {
        Intent i = new Intent(searchView, ResultView.class);
        i.putExtra("Nomi", nomisentieri);
        i.putExtra("PuntiIniziali", puntiiniziali);
        i.putExtra("Difficoltà", difficoltasentieri);
        i.putExtra("Durate", duratasentieri);
        searchView.startActivity(i);
    }

    public void getAllDetailsOfPath(ResultView resultview,String nomeSentiero){
        Path tmpPath = new Path(nomeSentiero);
        Call<Path> call = pathDAO.getPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null) {
                    detailView(resultview, response.body());
                }
                else {
                    bigError(resultview);
                    Log.e("Error","Impossibile recuperare dettagli sentiero");
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(resultview);
                Log.e("BigError","Impossibile recuperare dettagli sentiero ");
            }
        });
    }

    private void detailView(ResultView resultView, Path p) {
        Intent i = new Intent(resultView, DetailView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("creatore", p.getCreatore());
        i.putExtra("data", p.getDataModifica());
        resultView.startActivity(i);
    }

    //SEGNALAZIONE SENTIERO

    public ReportOverlay openReportOverlay(DetailView detailView, String nomesentiero, String creatore){
        FragmentManager fragmentManager = detailView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReportOverlay reportOverlay = new ReportOverlay();
        Bundle bundle = new Bundle();
        bundle.putString("nomesentiero",nomesentiero);
        bundle.putString("creatore",creatore);
        reportOverlay.setArguments(bundle);
        fragmentTransaction.add(R.id.detailOverlayContainer, reportOverlay, null);
        fragmentTransaction.commit();
        return reportOverlay;
    }

    public void checkReport(DetailView detailView, String nomesentiero, String risposta, String motivazione,String segnalante, String segnalato, Controller controller) throws NamePathWrongSizeException, CreatorWrongSizeException, AnswerNotEmptyException, ReporterWrongSizeException, MotivationWrongSizeException, CreatorEqualsReporterException {
        if(segnalante!=null&&!segnalante.equals("")&&segnalante.length()<50){
            if(segnalato!=null&&!segnalato.equals("")&&segnalato.length()<50){
                if(nomesentiero!=null&&!nomesentiero.equals("")&&nomesentiero.length()<100){
                    if(!segnalante.equals(segnalato)){
                        if(motivazione!=null&&!motivazione.equals("")&&motivazione.length()<200){
                            if(risposta!=null&&risposta.equals("")){
                                controller.reportPath(detailView, nomesentiero, risposta, motivazione, segnalato, segnalante);
                            }
                            else throw new AnswerNotEmptyException();
                        }
                        else throw new MotivationWrongSizeException();
                    }
                    else throw new CreatorEqualsReporterException();
                }
                else throw new NamePathWrongSizeException();
            }
            else throw new CreatorWrongSizeException();
        }
        else throw new ReporterWrongSizeException();
    }

    public void reportPath(DetailView detailView, String nomesentiero, String risposta, String motivazione, String segnalato, String segnalante) {

        Report report = new Report(1, motivazione,risposta, nomesentiero, segnalante, segnalato);
        Call<Report> call = reportDAO.reportPath(report);

        call.enqueue(new Callback<Report>() {
                @Override
                public void onResponse(Call<Report> call, Response<Report> response) {
                    if (response.body() != null) {
                        Toast.makeText(detailView, "Segnalazione fatta", Toast.LENGTH_LONG).show();
                        Log.i("Success","Sentiero reportato");
                    } else
                        Toast.makeText(detailView, "Segnalazione fallita", Toast.LENGTH_LONG).show();
                    Log.e("Error","Impossibile segnalare sentiero ");
                }

                @Override
                public void onFailure(Call<Report> call, Throwable t) {
                    bigError(detailView);
                    Log.e("BigError","Impossibile segnalare sentiero ");
                }
        });
    }

    // AGGIUNTA ALLA PLAYLIST

    public addToPlaylistFragment addToPlaylistFragment(DetailView detailView, String nomesentiero){
        FragmentManager fragmentManager = detailView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        addToPlaylistFragment addtoplaylistoverlay = new addToPlaylistFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nomeSentiero",nomesentiero);
        addtoplaylistoverlay.setArguments(bundle);
        fragmentTransaction.add(R.id.detailOverlayContainer, addtoplaylistoverlay, null);
        fragmentTransaction.commit();
        return addtoplaylistoverlay;
    }

    public void addPathToPlaylist(DetailView detailView, String nomesentiero, String nomePlaylist) {
        String creatorePlaylist = sharedPref.getString(String.valueOf(R.string.logged_email),"");
        AssPlaylistSentiero assPlaylistSentiero = new AssPlaylistSentiero(nomePlaylist,creatorePlaylist,nomesentiero);

        Call<AssPlaylistSentiero> call = assPlaylistSentieroDAO.addPathToPlaylist(assPlaylistSentiero);
        call.enqueue(new Callback<AssPlaylistSentiero>() {
            @Override
            public void onResponse(Call<AssPlaylistSentiero> call, Response<AssPlaylistSentiero> response) {
                if(response.body()!=null){
                    if(response.body().getNomeSentiero()!=null) {
                        Toast.makeText(detailView, "Sentiero aggiunto alla playlist", Toast.LENGTH_LONG).show();
                        Log.i("Error","Sentiero aggiunto alla playlist ");
                        cleanFragment(detailView.findViewById(R.id.detailOverlayContainer));
                    }
                    else{
                        Toast.makeText(detailView,"Hai già il sentiero nella playlist",Toast.LENGTH_LONG).show();
                        Log.e("Error","Hai già il sentiero nella playlist");
                    }
                }
                else {
                    Toast.makeText(detailView,"Hai già il sentiero nella playlist",Toast.LENGTH_LONG).show();
                    Log.e("Error","Hai già il sentiero nella playlist");
                }
            }

            @Override
            public void onFailure(Call<AssPlaylistSentiero> call, Throwable t) {
                bigError(detailView);
                Log.e("BigError","Errore inserimento sentiero nella playlist");
            }
        });
    }

    //AMMINISTRATORI

    public void checkAdmin(HomeFragment homeFragment, String email){
        User user = new User(email);
        Call<User> call = userDAO.checkAdmin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!=null && response.body().isAdmin()){
                    homeFragment.setAdmin(response.body().isAdmin());
                    Log.i("Success","É admin");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                bigError(homeFragment.getActivity());
                Log.e("BigError","Errore controllo admin");
            }
        });
    }

    public void openAdminView(HomeView homeView) {
        Intent i = new Intent(homeView, AdminView.class);
        homeView.startActivity(i);
    }

    //RICERCA AMMINISTRATORI
    public void adminSearchPath(AdminView adminView, String primiCaratteri) {
        Path path = new Path(primiCaratteri);
        Call<ArrayList<Path>> call = pathDAO.adminSearchPath(path);

        call.enqueue(new Callback<ArrayList<Path>>() {
            @Override
            public void onResponse(Call<ArrayList<Path>> call, Response<ArrayList<Path>> response) {
                openAdminResultView(adminView, response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Path>> call, Throwable t) {
                bigError(adminView);
                Log.e("BigError","Errore ricerca Path Admin");
            }
        });


    }

    public void openAdminResultView(AdminView adminView, ArrayList<Path> sentieriTrovati ){
        ArrayList<String> nomiSentieri = new ArrayList<>();
        ArrayList<String> creatoriSentieri = new ArrayList<>();
        if(sentieriTrovati.size()!=0) {
            for (Path p : sentieriTrovati) {
                nomiSentieri.add(p.getNomeSentiero());
                creatoriSentieri.add(p.getCreatore());
            }
            Intent i = new Intent(adminView, AdminResultView.class);
            i.putExtra("nomiSentieri",nomiSentieri);
            i.putExtra("creatoriSentieri", creatoriSentieri);
            adminView.startActivity(i);
        }
        else {
            Toast.makeText(adminView,"Non esistono sentieri che cominciano con questi caratteri",Toast.LENGTH_LONG).show();
            Log.e("Error","Non esistono sentieri che cominciano con questi caratteri");
        }
    }

    //DETTAGLI AMMINISTRATORI

    public void openAdminDetails(AdminResultView adminResultView, String nomeSentiero) {
        Path tmpPath = new Path(nomeSentiero);
        Call<Path> call = pathDAO.getPath(tmpPath);

        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null) {
                    adminDetailView(adminResultView, response.body());
                }
                else{
                    bigError(adminResultView);
                    Log.e("BigError","Errore nel recupero dei dettagli del sentiero");
                }
            }
            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(adminResultView);
                Log.e("BigError","Errore nel recupero dei dettagli del sentiero");
            }
        });
    }

    private void adminDetailView(AdminResultView adminResultView, Path p) {
        Intent i = new Intent(adminResultView, AdminDetailView.class);
        i.putExtra("nomesentiero", p.getNomeSentiero());
        i.putExtra("coordinate", p.getCoordinateAsArray());
        i.putExtra("puntoiniziale", p.getPuntoIniziale());
        i.putExtra("difficolta", p.getDifficolta());
        i.putExtra("durata", p.getDurata());
        i.putExtra("accessibilità",p.isAccessibilita());
        i.putExtra("descrizione", p.getDescrizione());
        i.putExtra("creatore", p.getCreatore());
        i.putExtra("data", p.getDataModifica());
        adminResultView.startActivity(i);
    }


    //MODIFICA SENTIERO ADMIN

    public void openAdminModificationView(AdminDetailView adminDetailView, String nomesentiero, String descrizione, Boolean accessibilità, Float durata, Integer difficolta) {
        Intent i = new Intent(adminDetailView, AdminModificationView.class);
        i.putExtra("nomeSentiero", nomesentiero);
        i.putExtra("descrizione", descrizione);
        i.putExtra("accessibilità", accessibilità);
        i.putExtra("durata", durata);
        i.putExtra("difficoltà", difficolta.intValue());
        adminDetailView.startActivity(i);
    }




    public void updatePathAdmin(AdminModificationView adminModificationView ,String nomeSentiero, String descrizione, float durata, int difficolta, boolean accessibilità, String nuovoNome) {
        Path path = new Path(nomeSentiero,  null, nuovoNome, difficolta, descrizione, accessibilità, null, durata);
        path.setDataModifica(Calendar.getInstance().getTime());
        Call<Path> call = pathDAO.updatePathAdmin(path);
        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                if(response.body()!=null) {
                    adminModificationView.finish();
                    Log.i("Success","Sentiero modificato da admin");
                }
                else bigError(adminModificationView);
                Log.e("BigError","Errore nella modifica del sentiero");
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(adminModificationView);
                Log.e("BigError","Errore nella modifica del sentiero");
            }
        });
    }

    //CANCELLAZIONE SENTIERO ADMIN

    public AdminDeleteFragment adminDeleteFragment(AdminDetailView adminDetailView, String nomesentiero) {
        FragmentManager fragmentManager = adminDetailView.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AdminDeleteFragment adminDeleteFragment = new AdminDeleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("nomeSentiero",nomesentiero);
        adminDeleteFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.deletePathAdminContainer,adminDeleteFragment, null);
        fragmentTransaction.commit();
        return adminDeleteFragment;
    }

    public void deletePathAdmin(String nomeSentiero, AdminDetailView adminDetailView ) {
        Path path = new Path(nomeSentiero);
        Call<Path> call = pathDAO.deletePath(path);
        call.enqueue(new Callback<Path>() {
            @Override
            public void onResponse(Call<Path> call, Response<Path> response) {
                adminDetailView.finish();
                Log.i("Success","Errore nella modifica del sentiero");
            }

            @Override
            public void onFailure(Call<Path> call, Throwable t) {
                bigError(adminDetailView);
                Log.e("BigError","Errore cancellazione sentiero");
            }
        });
    }
}

package Controller;

import Create.CreateView;
import Search.DetailView;
import Search.SearchView;

public class Controller_Stub extends Controller{

    private static Controller_Stub instance;
    public Controller_Stub() {}
    public static Controller_Stub getInstance() {
        if (instance == null) {
            instance = new Controller_Stub();
        }
        return(instance);
    }

    @Override
    public void createPath(CreateView createView, String nome, String descrizione, float durata, int difficolta, boolean access, String puntoiniziale, String coordinate){
    }

    @Override
    public void getFilteredPaths(SearchView searchView, float mindur, float maxdur, float mindiff, float maxdiff, String pos, boolean access){
    }

    @Override
    public void reportPath(DetailView detailView, String nomesentiero, String risposta, String motivazione, String segnalato, String segnalante){
    }
}

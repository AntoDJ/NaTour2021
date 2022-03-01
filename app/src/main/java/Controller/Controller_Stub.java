package Controller;

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
    public void createPath(String nome, String descrizione, float durata, int difficolta, boolean access, String puntoiniziale, String coordinate){
    }
}

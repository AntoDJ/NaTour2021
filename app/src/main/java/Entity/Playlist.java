package Entity;

import java.util.ArrayList;

public class Playlist {
    private String nome;
    private String creatore;
    private ArrayList<String> arrayPath;

    public Playlist(String nome, String creatore) {
        this.nome = nome;
        this.creatore = creatore;
        arrayPath = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCreatore() {
        return creatore;
    }

    public void setCreatore(String creatore) {
        this.creatore = creatore;
    }

    public ArrayList<String> getPath(){
        return arrayPath;
    }

    public void addPath(String path){
        arrayPath.add(path);
    }
}

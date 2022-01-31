package Entity;

import java.util.ArrayList;
import java.util.Date;

public class Path {
    private String nomeSentiero;
    private String coordinate;
    private String puntoIniziale;
    private Integer difficolta;
    private String descrizione;
    private Boolean accessibilita;
    private Date dataModifica;
    private String creatore;
    private Float durata;

    public Path(String nomeSentiero) {
        this.nomeSentiero = nomeSentiero;
    }


    public Path(String nomeSentiero, String coordinate, String puntoIniziale, Integer difficolta, String descrizione, Boolean accessibilita, Date dataModifica, String creatore, float durata) {
        this.nomeSentiero = nomeSentiero;
        this.coordinate = coordinate;
        this.puntoIniziale = puntoIniziale;
        this.difficolta = difficolta;
        this.descrizione = descrizione;
        this.accessibilita = accessibilita;
        this.dataModifica = dataModifica;
        this.creatore = creatore;
        this.durata = durata;
    }

    public Path(){}

    public Path(String nomeSentiero, Integer difficolta, float durata) {
        this.nomeSentiero = nomeSentiero;
        this.difficolta = difficolta;
        this.durata = durata;
    }

    public String getNomeSentiero() {
        return this.nomeSentiero;
    }

    public String getCoordinate() {
        return this.coordinate;
    }

    public String getPuntoIniziale() {
        return this.puntoIniziale;
    }

    public int getDifficolta() {
        return this.difficolta;
    }

    public Float getDurata() {
        return this.durata;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public Boolean isAccessibilita() {
        return this.accessibilita;
    }

    public Date getDataModifica() {
        return this.dataModifica;
    }

    public String getCreatore(){
        return this.creatore;
    }

    public ArrayList<String> getCoordinateAsArray(){
        ArrayList<String> array = new ArrayList<>();
        if(!this.coordinate.equals("")){
            String[] parts = this.coordinate.split(" ");
            for(int i=0; i<parts.length/2; i++){
                array.add(parts[i*2]+" "+parts[i*2+1]);
            }
        }
        return array;
    }

    @Override
    public String toString() {
        return "Path{" +
                "nomeSentiero='" + nomeSentiero + '\'' +
                ", coordinate=" + coordinate +
                ", puntoIniziale='" + puntoIniziale + '\'' +
                ", difficolta=" + difficolta +
                ", durata=" + durata +
                ", descrizione='" + descrizione + '\'' +
                ", accessibilitaDisabili=" + accessibilita +
                ", dataModifica=" + dataModifica +
                ", creatore='" + creatore + '\'' +
                '}';
    }


}

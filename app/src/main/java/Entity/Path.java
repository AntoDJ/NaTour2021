package Entity;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Date;

public class Path {
    private String nomeSentiero;
    private ArrayList<String> coordinate;
    private String puntoIniziale;
    private int difficolta;
    private float durata;
    private String descrizione;
    private boolean accessibilitaDisabili;
    private Date dataModifica;
    private String creatore;


    public Path(String nomeSentiero, ArrayList<String> coordinate, String puntoIniziale,
                int difficolta, float durata, String descrizione, boolean accessibilitaDisabili, Date dataModifica, String creatore) {
        this.nomeSentiero = nomeSentiero;
        this.coordinate = coordinate;
        this.puntoIniziale = puntoIniziale;
        this.difficolta = difficolta;
        this.durata = durata;
        this.descrizione = descrizione;
        this.accessibilitaDisabili = accessibilitaDisabili;
        this.dataModifica = dataModifica;
        this.creatore = creatore;
    }

    public Path(){}

    public Path(String nomeSentiero, int difficolta, float durata) {
        this.nomeSentiero = nomeSentiero;
        this.difficolta = difficolta;
        this.durata = durata;
    }


    public String getNomeSentiero() {
        return nomeSentiero;
    }

    public void setNomeSentiero(String nomeSentiero) {
        this.nomeSentiero = nomeSentiero;
    }

    public ArrayList<String> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(ArrayList<String> coordinate) {
        this.coordinate = coordinate;
    }

    public String getPuntoIniziale() {
        return puntoIniziale;
    }

    public void setPuntoIniziale(String puntoIniziale) {
        this.puntoIniziale = puntoIniziale;
    }

    public int getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(int difficolta) {
        this.difficolta = difficolta;
    }

    public float getDurata() {
        return durata;
    }

    public void setDurata(float durata) {
        this.durata = durata;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isAccessibilitaDisabili() {
        return accessibilitaDisabili;
    }

    public void setAccessibilitaDisabili(boolean accessibilitaDisabili) {
        this.accessibilitaDisabili = accessibilitaDisabili;
    }

    public Date getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(Date dataModifica) {
        this.dataModifica = dataModifica;
    }
}

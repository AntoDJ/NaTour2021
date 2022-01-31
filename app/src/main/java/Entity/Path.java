package Entity;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

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

    public Path(String nomeSentiero, int difficolta, float durata, String puntoiniziale) {
        this.nomeSentiero = nomeSentiero;
        this.difficolta = difficolta;
        this.durata = durata;
        this.puntoIniziale = puntoiniziale;
    }


    public String getNomeSentiero() {
        return nomeSentiero;
    }



    public String getCoordinate() {
        return coordinate;
    }


    public String getPuntoIniziale() {
        return puntoIniziale;
    }

    public int getDifficolta() {
        return difficolta;
    }


    public Float getDurata() {
        return durata;
    }

    public String getDescrizione() {
        return descrizione;
    }


    public Boolean isAccessibilita() {
        return accessibilita;
    }


    public Date getDataModifica() {
        return dataModifica;
    }



}

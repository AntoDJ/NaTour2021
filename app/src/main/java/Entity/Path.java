package Entity;

import android.provider.ContactsContract;

import java.util.Date;

public class Path {
    private String nomeSentiero;
    private String coordinate;
    private String puntoIniziale;
    private int difficolta;
    private double durata;
    private String descrizione;
    private boolean accessibilitaDisabili;
    private Date dataModifica;
    //aggiungere nel caso il creatore


    public Path(String nomeSentiero, String coordinate, String puntoIniziale,
                int difficolta, double durata, String descrizione, boolean accessibilitaDisabili, Date dataModifica) {
        this.nomeSentiero = nomeSentiero;
        this.coordinate = coordinate;
        this.puntoIniziale = puntoIniziale;
        this.difficolta = difficolta;
        this.durata = durata;
        this.descrizione = descrizione;
        this.accessibilitaDisabili = accessibilitaDisabili;
        this.dataModifica = dataModifica;
    }

    public Path(){}

    public Path(String nomeSentiero, int difficolta, double durata) {
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
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

    public double getDurata() {
        return durata;
    }

    public void setDurata(double durata) {
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

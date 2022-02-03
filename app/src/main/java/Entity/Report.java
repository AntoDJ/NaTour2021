package Entity;

public class Report {
    int idSegnalazione;
    String descrizione;
    String risposta="";
    String nomeSentiero;
    String segnalante;
    String segnalato;

    public Report(int idSegnalazione, String descrizione, String nomeSentiero, String segnalante, String segnalato) {
        this.idSegnalazione = idSegnalazione;
        this.descrizione = descrizione;
        this.nomeSentiero = nomeSentiero;
        this.segnalante = segnalante;
        this.segnalato = segnalato;
    }
}

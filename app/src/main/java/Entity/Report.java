package Entity;

public class Report {
    private int idSegnalazione;
    private String descrizione;
    private String risposta;
    private String nomeSentiero;
    private String segnalante;
    private String segnalato;

    public Report(int idSegnalazione, String descrizione,String risposta, String nomeSentiero, String segnalante, String segnalato) {
        this.idSegnalazione = idSegnalazione;
        this.descrizione = descrizione;
        this.risposta = risposta;
        this.nomeSentiero = nomeSentiero;
        this.segnalante = segnalante;
        this.segnalato = segnalato;
    }

    public Report(int idSegnalazione, String risposta){
        this.idSegnalazione=idSegnalazione;
        this.risposta=risposta;
    }


    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getRisposta() {
        return risposta;
    }

    public String getNomeSentiero() {
        return nomeSentiero;
    }

    public String getSegnalante() {
        return segnalante;
    }

    public String getSegnalato() {
        return segnalato;
    }
}

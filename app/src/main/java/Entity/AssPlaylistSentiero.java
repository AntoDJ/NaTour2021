package Entity;

public class AssPlaylistSentiero {
    private String nomePlaylist;
    private String creatorePlaylist;
    private String nomeSentiero;

    public AssPlaylistSentiero(String nomePlaylist, String creatorePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.creatorePlaylist = creatorePlaylist;
    }

    public AssPlaylistSentiero(String nomePlaylist, String creatorePlaylist, String nomeSentiero){
        this.nomePlaylist = nomePlaylist;
        this.creatorePlaylist = creatorePlaylist;
        this.nomeSentiero = nomeSentiero;
    }

    public String getNomeSentiero() {
        return nomeSentiero;
    }
}

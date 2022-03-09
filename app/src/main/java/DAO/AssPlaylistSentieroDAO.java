package DAO;

import Entity.AssPlaylistSentiero;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AssPlaylistSentieroDAO {
    @POST("addpathtoplaylist")
    Call<AssPlaylistSentiero> addPathToPlaylist(@Body AssPlaylistSentiero assPlaylistSentiero);

    @POST("removefromplaylist")
    Call<AssPlaylistSentiero> removeFromPlaylist(@Body AssPlaylistSentiero removefromplaylist);
}

package DAO;

import Entity.Playlist;
import Entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlaylistDAO {
    @POST("createplaylistofuser")
    Call<Playlist> createPlaylistOfUser(@Body User user);


}

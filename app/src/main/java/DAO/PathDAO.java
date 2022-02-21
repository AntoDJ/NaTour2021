package DAO;

import java.util.ArrayList;

import Entity.AssPlaylistSentiero;
import Entity.Path;
import Entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PathDAO {
    @POST("getpathbyname")
    Call<Path> getPath(@Body Path path);

    @POST("insertpath")
    Call<Path> insertPath(@Body Path path);

    @POST("getfilteredpaths")
    Call<ArrayList<Path>> getAllFilteredPath(@Body Path.PathToFilter pt);

    @POST("getalldetailsofpersonalpath")
    Call<Path> getAllDetailsOfPersonalPath(@Body Path path);

    @POST("getpathofplaylist")
    Call<ArrayList<Path>> getPathsOfPlaylist(@Body AssPlaylistSentiero assPlaylistSentiero);

    @POST("getpersonalpathofplaylist")
    Call<ArrayList<Path>> getPersonalPathsOfPlaylist(@Body User user);

    @POST("updatepath")
    Call<Path> updatePath(@Body Path path);

    @POST("deletepath")
    Call<Path> deletePath(@Body Path path);






}

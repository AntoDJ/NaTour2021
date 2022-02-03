package DAO;

import java.util.ArrayList;

import Entity.Path;
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
}

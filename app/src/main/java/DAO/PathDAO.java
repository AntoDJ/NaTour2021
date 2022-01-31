package DAO;

import Entity.Path;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PathDAO {
    @POST("getpathbyname")
    Call<Path> getPathByName(@Body String nomeSentiero);
}

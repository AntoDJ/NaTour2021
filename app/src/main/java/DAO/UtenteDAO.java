package DAO;

import Entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UtenteDAO {
    @POST("insertuser")
    Call<User> insertUser(@Body User user);
}

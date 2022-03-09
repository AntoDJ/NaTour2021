package DAO;

import Entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserDAO {
    @POST("insertuser")
    Call<User> insertUser(@Body User user);

//ADMIN
    @POST("checkadmin")
    Call<User> checkAdmin(@Body User user);
}

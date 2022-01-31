package DAO;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitIstance {
    private static Retrofit retrofit;


    public static Retrofit getIstanza() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://4q1umv8ni4.execute-api.us-east-1.amazonaws.com/NaTour/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

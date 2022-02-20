package DAO;


import java.util.ArrayList;

import Entity.Report;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportDAO {
    @POST("reportpath")
    Call<Report> reportPath(@Body Report report);

    @POST("getnotification")
    Call<ArrayList<Report>> getNotification(@Body Report report);

    @POST("rispodisegnalazione")
    Call<Report> addReply(@Body Report report);
}

package DAO;


import Entity.Report;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportDAO {
    @POST("reportpath")
    Call<Report> reportPath(@Body Report report);
}

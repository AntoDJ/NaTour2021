package DBConnection;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection=null;
    private final String USERNAME = "postgres";
    private final String PASSWORD = "i32jd9021kew94m";
    private final String url = "jdbc:postgresql://natourdb.cutwttntztpd.eu-west-2.rds.amazonaws.com:5432/postgres:";





    private DBConnection() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (Exception e) {
            System.out.println("Errore di connesione ad DB");
        }

    }

    public Connection getConnection() throws SQLException {
        Log.i("prima", "prima2");
        connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
        Log.i("dopo", "prima2");
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null)
        {
            Log.i("prima1", "prima1");
            instance = new DBConnection();
            Log.i("dopo1", "dopo1");
        }
        else
        if (instance.getConnection().isClosed())
        {
            Log.i("prima2", "prima2");
            instance = new DBConnection();
            Log.i("dopo2", "dopo2");
        }

        return instance;
    }
}

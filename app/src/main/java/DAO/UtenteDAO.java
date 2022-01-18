package DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DBConnection.DBConnection;
import Entity.User;

public class UtenteDAO {
    private Connection connection = null;
    private DBConnection dbconnection = null;

    public UtenteDAO() {
        try {
            dbconnection = DBConnection.getInstance();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void registraUnUtente(User utente)  {
        try{
            Log.i("getConnection", "getto");
            connection = dbconnection.getConnection();
            Log.i("1", "1");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Utente\"(email, password) VALUES( ?, ?)");
            Log.i("1", "2");
            ps.setString(1, utente.getEmail());
            Log.i("1", "3");
            ps.setString(2, utente.getPassword());
            ps.executeQuery();
            Log.i("1", "4");
            ps.close();
            connection.close();
        }catch (SQLException e){}


    }


}

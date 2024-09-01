package main.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.GameObjects.Fish;

public class Database {
    private static final String URL = "jdbc:sqlite:src\\main\\database\\data.db";

    public Database(){
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public void getFishByName(String name) {
    String sql = "SELECT * FROM fish WHERE name = ?";

    try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("name"));
            System.out.println(rs.getString("type"));
            System.out.println(rs.getString("habitat"));
            System.out.println(rs.getString("weight"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public void getFishInfoByHabitat(String habitat){
        List<Fish> fishList = new ArrayList<Fish>();
        String sql = "SELECT * FROM fish WHERE habitat = ?";
    
    
    try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, habitat);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            //fishList.add(new Fish(0,0,0,0,))
            System.out.println(rs.getString("name"));
            System.out.println(rs.getString("type"));
            System.out.println(rs.getString("habitat"));
            System.out.println(rs.getString("weight"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}



//IDEA of how wil work

//will get all of fish of a habitat when enter habitat
// picks one at random
//spawns
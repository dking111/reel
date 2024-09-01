package main.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import main.GameObjects.Fish;

public class Database {
    private static final String URL = "jdbc:sqlite:src\\main\\database\\data.db";

    public Database(){
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }



    public static List<Fish> getAllFishByHabitat(String habitat){
        List<Fish> fishList = new ArrayList<Fish>();
        String sql = "SELECT * FROM fish WHERE habitat = ? ORDER BY rarity DESC";
    try (Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, habitat);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {            
            fishList.add(new Fish(0,0,0,0,rs.getInt("weight_max"),rs.getInt("weight_min"),rs.getString("name"),rs.getInt("rarity"),rs.getString("path")));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return fishList;
}


public static List<Fish> getAllFish(String habitat){
    List<Fish> fishList = new ArrayList<Fish>();
    String sql = "SELECT * FROM fish ORDER BY DESC";
try (Connection conn = Database.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

    stmt.setString(1, habitat);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {            
        fishList.add(new Fish(0,0,0,0,rs.getInt("weight_max"),rs.getInt("weight_min"),rs.getString("name"),rs.getInt("rarity"),rs.getString("path")));
    }
} catch (SQLException e) {
    e.printStackTrace();
}
return fishList;
}


}



//IDEA of how wil work

//will get all of fish of a habitat when enter habitat
// picks one at random
//spawns
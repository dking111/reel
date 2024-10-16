package main.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.GUI.Trophy;
import main.GameObjects.Fish;

/**
 * Represents the database interaction layer for managing game-related data such as fish and trophies.
 * This class provides methods to retrieve and update fish data from a SQLite database.
 */
public class Database {
    private static final String URL = "jdbc:sqlite:src\\main\\database\\data.db";

    /**
     * Constructs a Database instance.
     */
    public Database() {
    }

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A {@link Connection} object representing the connection to the database.
     * @throws SQLException if there is an error while connecting to the database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Retrieves all fish from the database that are associated with a specific habitat.
     * This method will also return fish that are marked as being present in both habitats.
     *
     * @param habitat The habitat to filter the fish by (e.g., "River", "Lake").
     * @return A list of {@link Fish} objects that are present in the specified habitat.
     */
    public static List<Fish> getAllFishByHabitat(String habitat) {
        List<Fish> fishList = new ArrayList<Fish>();
        String sql = "SELECT * FROM fish WHERE habitat = ? OR habitat = 'Both' ORDER BY rarity DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, habitat);
            ResultSet rs = stmt.executeQuery();

            // Process each fish record retrieved from the database
            while (rs.next()) {
                fishList.add(new Fish(0, 0, 0, 0, rs.getInt("weight"),
                        rs.getInt("weight_max"), rs.getInt("weight_min"),
                        rs.getString("name"), rs.getInt("rarity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fishList;
    }

    /**
     * Retrieves a list of all fish from the database and organizes them into trophies.
     * The trophy is displayed differently depending on whether the fish has been caught or not.
     *
     * @return A list of {@link Trophy} objects representing all available fish.
     */
    public static List<Trophy> getAllFish() {
        List<Trophy> caughtFishList = new ArrayList<Trophy>();
        String sql = "SELECT * FROM fish ORDER BY type DESC";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            // Process each fish record to create a trophy representation
            while (rs.next()) {
                if (rs.getInt("weight") == 0) {
                    caughtFishList.add(new Trophy(0, 0, 100, 100, rs.getString("path_uncaught"),
                            rs.getString("name"), rs.getInt("weight")));
                } else {
                    caughtFishList.add(new Trophy(0, 0, 100, 100, rs.getString("path_caught"),
                            rs.getString("name"), rs.getInt("weight")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return caughtFishList;
    }

    /**
     * Updates the weight of a specific fish in the database.
     * The fish is only updated if the new weight is greater than the current weight stored in the database.
     *
     * @param fish The {@link Fish} object containing the updated weight information.
     */
    public static void setFishWeight(Fish fish) {
        String sql = "UPDATE fish SET weight = ? WHERE name = ? AND weight < ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fish.getWeight());
            stmt.setString(2, fish.getName());
            stmt.setInt(3, fish.getWeight());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

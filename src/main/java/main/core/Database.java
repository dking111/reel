package main.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    // Relative path to the database inside the resources folder
    private static final String DB_RESOURCE_PATH = "/database/data.db";  // Path inside resources

    /**
     * Gets the connection to the database.
     * Uses the database file directly from the resources folder.
     * 
     * @return Connection to the SQLite database
     * @throws SQLException if a database access error occurs
     */
public static Connection getConnection() throws SQLException {
    // Extract the database file from the resources (JAR) to a temporary directory
    File tempDB = new File(System.getProperty("java.io.tmpdir") + File.separator + "game_data.db");

    // If the file does not exist, extract it from the JAR
    if (!tempDB.exists()) {
        try (InputStream in = Database.class.getResourceAsStream(DB_RESOURCE_PATH);
             OutputStream out = new FileOutputStream(tempDB)) {

            if (in == null) {
                throw new IOException("Database resource not found in JAR: " + DB_RESOURCE_PATH);
            }

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException("Error extracting database from JAR to temp directory.");
        }
    }

    // Create the connection to the extracted database file
    String url = "jdbc:sqlite:" + tempDB.getAbsolutePath();
    return DriverManager.getConnection(url);
}

    /**
     * Retrieves all fish from the database that are associated with a specific habitat.
     * This method will also return fish that are marked as being present in both habitats.
     *
     * @param habitat The habitat to filter the fish by (e.g., "River", "Lake").
     * @return A list of {@link Fish} objects that are present in the specified habitat.
     */
    public static List<Fish> getAllFishByHabitat(String habitat) {
        List<Fish> fishList = new ArrayList<>();
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
        List<Trophy> caughtFishList = new ArrayList<>();
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

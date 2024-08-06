package game;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameData {
    private List<Obstacle> obstacles; 

    public GameData(String filepath) {
        // Initialize gameData with obstacles
        this.gameData = loadGameData(filepath);
    }

    private List<Obstacle> loadGameData(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            TypeToken<List<Obstacle>> typeToken = new TypeToken<List<Obstacle>>() {};
            this.obstacles = gson.fromJson(reader, typeToken.getType());
        } catch (IOException e) {
            e.printStackTrace(); // Print error message
            return null; // Return null or an empty list based on your preference
        }
    }

    // Return the list of obstacles
    public List<Obstacle> getObstacles() {
        return obstacles; // Directly return the list of obstacles
    }
}




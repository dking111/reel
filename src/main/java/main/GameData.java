package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

public class GameData {
    private List<Sprite> sprites;
    private List<Door> doors;
    private List<Sprite> cameraBounds;

    // Constructor that initializes game data from the given file path
    public GameData(String filepath) {
        // Load game data from the specified file path
        loadGameData(filepath);
    }

    // Method to load game data from the JSON file
    private void loadGameData(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            // Deserialize JSON into GameData object (this)
            GameData data = gson.fromJson(reader, GameData.class);
            this.sprites = data.sprites;
            this.doors = data.doors;
            this.cameraBounds = data.cameraBounds;
        } catch (IOException e) {
            e.printStackTrace(); // Print error message
        }
    }

    // Return the list of obstacles
    public List<Sprite> getSprites() {
        return sprites; // Return obstacles
    }
    // Return the list of doors
    public List<Door> getDoors() {
        return doors; // Return doors
    }

    public List<Sprite> getCameraBounds(){
        return cameraBounds;
    }
}

package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

public class GameData {
    private List<Sprite> sprites;
    private List<Door> doors;
    private float cameraBounds;
    private int width;
    private int height;
    private String background;
    private String player;

    // Constructor that initializes game data from the given file path
    public GameData(String filepath) {
        // Load game data from the specified file path
        loadGameData(filepath);
    }

    // Method to load game data from the JSON file
    public void loadGameData(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            // Deserialize JSON into GameData object (this)
            GameData data = gson.fromJson(reader, GameData.class);
            this.sprites = data.sprites;
            this.doors = data.doors;
            this.cameraBounds = data.cameraBounds;
            this.width = data.width;
            this.height = data.height;
            this.player = data.player;
            this.background = data.background;
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

    public float getCameraBounds(){
        return cameraBounds;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public String getPlayer(){
        return player;
    }

    public String getBackground(){
        return background;
    }
}

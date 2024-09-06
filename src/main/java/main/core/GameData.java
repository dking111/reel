package main.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

import main.GameObjects.Door;
import main.GameObjects.FishingSpot;
import main.GameObjects.Sprite;
import main.GameObjects.Water;

/**
 * Represents the game data, including game objects, dimensions, and configuration settings.
 * The data is loaded from a JSON file and provides access to various game elements.
 */
public class GameData {
    private List<Sprite> sprites;
    private List<Door> doors;
    private float cameraBounds;
    private String background;
    private String player;
    private List<FishingSpot> fishingSpots;
    private String fish;
    private Water water;
    private String habitat;

    /**
     * Constructs a GameData instance and initializes it by loading data from the specified JSON file.
     *
     * @param filepath The path to the JSON file containing the game data.
     */
    public GameData(String filepath) {
        loadGameData(filepath);
    }

    /**
     * Loads game data from the specified JSON file.
     * This method deserializes the JSON content into a GameData object.
     *
     * @param filepath The path to the JSON file containing the game data.
     */
    public void loadGameData(String filepath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filepath)) {
            // Deserialize JSON into GameData object
            GameData data = gson.fromJson(reader, GameData.class);
            this.sprites = data.sprites;
            this.doors = data.doors;
            this.cameraBounds = data.cameraBounds;
            this.player = data.player;
            this.background = data.background;
            this.fishingSpots = data.fishingSpots;
            this.fish = data.fish;
            this.water = data.water;
            this.habitat = data.habitat;
        } catch (IOException e) {
            e.printStackTrace(); // Print error message
        }
    }

    /**
     * Returns the list of sprites representing various obstacles and game objects.
     *
     * @return A list of {@link Sprite} objects.
     */
    public List<Sprite> getSprites() {
        return sprites;
    }

    /**
     * Returns the list of doors in the game.
     *
     * @return A list of {@link Door} objects.
     */
    public List<Door> getDoors() {
        return doors;
    }

    /**
     * Returns the camera bounds as a float value.
     *
     * @return The camera bounds value.
     */
    public float getCameraBounds() {
        return cameraBounds;
    }



    /**
     * Returns the player information or identifier.
     *
     * @return The player information as a string.
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Returns the background image or resource path.
     *
     * @return The background resource path as a string.
     */
    public String getBackground() {
        return background;
    }

    /**
     * Returns the list of fishing spots in the game.
     *
     * @return A list of {@link FishingSpot} objects.
     */
    public List<FishingSpot> getFishingSpots() {
        return fishingSpots;
    }

    /**
     * Returns the path of the fish
     *
     * @return The fish resource path as a string.
     */
    public String getFish() {
        return fish;
    }

    public Water getWater(){
        return water;
    }

    public String getHabitat() {
        return habitat;
    }
}

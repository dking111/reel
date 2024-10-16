package main.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import main.GameObjects.Door;
import main.GameObjects.FireLight;
import main.GameObjects.FishingSpot;
import main.GameObjects.Light;
import main.GameObjects.Shelf;
import main.GameObjects.Sprite;
import main.GameObjects.Water;
import main.GameObjects.WindowLight;

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
    private int playerWidth, playerHeight;
    private List<Shelf> shelves;
    private List<Light> lights;  // List of lights
    private FireLight fireLight;
    private WindowLight windowLight;

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
            this.playerWidth = data.playerWidth;
            this.playerHeight = data.playerHeight;
            this.shelves = data.shelves;
            this.fireLight = data.fireLight;
            this.windowLight = data.windowLight;

            // Initialize the lights list to avoid NullPointerException
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
     * Returns the path of the fish.
     *
     * @return The fish resource path as a string.
     */
    public String getFish() {
        return fish;
    }

    /**
     * Returns the water object representing water in the game.
     *
     * @return The water object.
     */
    public Water getWater() {
        return water;
    }

    /**
     * Returns the habitat information.
     *
     * @return The habitat information as a string.
     */
    public String getHabitat() {
        return habitat;
    }

    /**
     * Returns the player height in pixels.
     *
     * @return The player's height.
     */
    public int getPlayerHeight() {
        return playerHeight;
    }

    /**
     * Returns the player width in pixels.
     *
     * @return The player's width.
     */
    public int getPlayerWidth() {
        return playerWidth;
    }

    /**
     * Returns the list of shelves in the game.
     *
     * @return A list of {@link Shelf} objects.
     */
    public List<Shelf> getShelves() {
        return shelves;
    }

    /**
     * Returns the FireLight object representing a fire light in the game.
     *
     * @return The FireLight object.
     */
    public FireLight getFireLight() {
        return fireLight;
    }

    /**
     * Returns the WindowLight object representing a window light in the game.
     *
     * @return The WindowLight object.
     */
    public WindowLight getWindowLight() {
        return windowLight;
    }

    /**
     * Returns the list of lights in the game. This list includes both window and fire lights if they exist.
     *
     * @return A list of {@link Light} objects.
     */
    public List<Light> getLights() {
        lights = new ArrayList<>();
        if (getWindowLight() != null) {
            this.lights.add(getWindowLight());
        }
        if (getFireLight() != null) {
            this.lights.add(getFireLight());
        }
        return lights; 
    }
}

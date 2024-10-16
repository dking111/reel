package main.GameObjects;


/**
 * Represents a background image in the game.
 * This class extends AnimatedSprite to handle background rendering.
 */
public class Background extends AnimatedSprite {

    /**
     * Constructs a Background instance with the specified parameters.
     *
     * @param x The x-coordinate of the background image.
     * @param y The y-coordinate of the background image.
     * @param w The width of the background image.
     * @param h The height of the background image.
     * @param path The file path to the background image.
     */
    public Background(int x, int y, int w, int h, String path) {
        super(x, y, w, h, path,30);
        // Load the image using the path provided in the superclass constructor
    }
}

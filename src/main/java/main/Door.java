package main;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a door in the game that can trigger a level change when collided with.
 * The door is visually represented as a colored rectangle.
 */
public class Door extends Sprite {
    private String path;
    private Boolean levelChange;

    /**
     * Constructs a Door instance with the specified parameters.
     *
     * @param x The x-coordinate of the door.
     * @param y The y-coordinate of the door.
     * @param w The width of the door.
     * @param h The height of the door.
     * @param path The path to the level or resource associated with this door.
     */
    public Door(int x, int y, int w, int h, String path) {
        super(x, y, w, h);
        this.path = path;
        levelChange = false;
    }

    /**
     * Draws the door on the screen.
     * The door is rendered as a filled rectangle using a predefined color.
     *
     * @param g The Graphics2D object used for drawing the door.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN); // Set the color for the door
        g.fillRect(x, y, w, h); // Draw the door as a filled rectangle using inherited fields
    }

    /**
     * Marks the door as having been collided with, indicating that a level change should occur.
     */
    @Override
    public void collided() {
        levelChange = true;
    }

    /**
     * Returns the path associated with the door if a level change has been triggered.
     * Resets the levelChange flag to false after returning the path.
     *
     * @return The path to the level or resource if the door has been collided with; otherwise, returns null.
     */
    public String levelChanged() {
        try {
            if (levelChange) {
                levelChange = false;
                return path;
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
}

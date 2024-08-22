package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a fishing spot in the game where players can engage in fishing activities.
 * The spot is visually represented as a colored rectangle.
 */
public class FishingSpot extends Sprite {
    private Boolean fishing;

    /**
     * Constructs a FishingSpot instance with the specified parameters.
     *
     * @param x The x-coordinate of the fishing spot.
     * @param y The y-coordinate of the fishing spot.
     * @param w The width of the fishing spot.
     * @param h The height of the fishing spot.
     */
    public FishingSpot(int x, int y, int w, int h) {
        super(x, y, w, h);
        fishing = false;
    }

    /**
     * Draws the fishing spot on the screen.
     * The spot is rendered as a filled rectangle using a predefined color.
     *
     * @param g The Graphics2D object used for drawing the fishing spot.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK); // Set the color for the fishing spot
        g.fillRect(x, y, w, h); // Draw the fishing spot as a filled rectangle using inherited fields
    }

    /**
     * Marks the fishing spot as being active for fishing when a collision occurs.
     */
    @Override
    public void collided() {
        fishing = true;
    }

    /**
     * Returns whether the fishing spot is active for fishing.
     *
     * @return True if the fishing spot is active; otherwise, returns false.
     */
    public Boolean getFishing() {
        return fishing;
    }

    /**
     * Sets the fishing status of the fishing spot.
     *
     * @param fishing The new fishing status to be set.
     */
    public void setFishing(Boolean fishing) {
        this.fishing = fishing;
    }
}

package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a isFishing spot in the game where players can engage in isFishing activities.
 * The spot is visually represented as a colored rectangle.
 */
public class FishingSpot extends Sprite {

    private Boolean isFishing; 

    /**
     * Constructs a FishingSpot instance with the specified parameters.
     *
     * @param x The x-coordinate of the isFishing spot.
     * @param y The y-coordinate of the isFishing spot.
     * @param w The width of the isFishing spot.
     * @param h The height of the isFishing spot.
     */
    public FishingSpot(int x, int y, int w, int h) {
        super(x, y, w, h);
        isFishing = false; 
    }

    /**
     * Draws the isFishing spot on the screen as a filled rectangle.
     *
     * @param g The Graphics2D object used for drawing the isFishing spot.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, w, h);
    }

    /**
     * Marks the isFishing spot as being active for isFishing when a collision occurs.
     */
    @Override
    public void collided() {
        isFishing = true; 
    }

    /**
     * Returns whether the isFishing spot is active for fishing.
     *
     * @return True if the isFishing spot is active; otherwise, returns false.
     */
    public Boolean getFishing() {
        return isFishing != null ? isFishing : false; 
    }

    /**
     * Sets the isFishing status of the isFishing spot.
     *
     * @param isFishing The new isFishing status to be set.
     */
    public void setFishing(Boolean isFishing) {
        this.isFishing = isFishing; 
    }
}

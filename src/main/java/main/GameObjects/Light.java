package main.GameObjects;

import java.awt.Graphics2D;

/**
 * Represents a light source in the game.
 * The light source can be positioned and has a radius that defines its influence.
 */
public class Light {
    protected static final float DEFAULT_RADIUS = 100f;

    protected float radius;
    protected int lightPosX;
    protected int lightPosY;
    protected float timeOfDay;

    /**
     * Constructs a new {@code Light} object with specified position and radius.
     *
     * @param lightPosX The x-coordinate of the light's position.
     * @param lightPosY The y-coordinate of the light's position.
     * @param radius    The radius of the light's influence.
     */
    public Light(int lightPosX, int lightPosY, float radius) {
        this.lightPosX = lightPosX;
        this.lightPosY = lightPosY;
        this.radius = radius > 0 ? radius : DEFAULT_RADIUS; // Ensure radius is positive
        this.timeOfDay = 0;
    }

    /**
     * Updates the time of day for the light, affecting its appearance.
     *
     * @param timeOfDay The current time of day as a float value.
     */
    public void update(float timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    /**
     * Draws the light on the specified {@code Graphics2D} context.
     * Currently, this method does nothing.
     *
     * @param g The {@code Graphics2D} context used for drawing the light.
     */
    public void draw(Graphics2D g) {
        // No implementation yet
        return;
    }
}

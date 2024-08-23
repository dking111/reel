package main.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;

/**
 * Represents a player character in the game, extending {@link AnimatedSprite} to include animation capabilities.
 * This class handles player movement, rotation, and drawing with angle adjustments.
 */
public class Player extends AnimatedSprite {
    private int angle;
    private int maxSpeed;
    private Boolean isFishing;

    /**
     * Constructs a new {@code Player} object with the specified position, size, image path, and maximum speed.
     *
     * @param x         The x-coordinate of the player's position.
     * @param y         The y-coordinate of the player's position.
     * @param w         The width of the player.
     * @param h         The height of the player.
     * @param path      The path to the image used for the player's sprite.
     * @param maxSpeed  The maximum speed of the player.
     */
    public Player(int x, int y, int w, int h, String path, int maxSpeed) {
        super(x, y, w, h, path,5);
        angle = 0;
        this.maxSpeed = maxSpeed;
        isFishing = false;
    }

    /**
     * Draws the player on the specified {@code Graphics2D} context with rotation based on movement direction.
     *
     * @param g The {@code Graphics2D} context on which to draw the player.
     */
    @Override
    public void draw(Graphics2D g) {
        // Save the original transformation
        AffineTransform originalTransform = g.getTransform();
        angle = calcAngle();
        rotate(angle, g);
        if(!isFishing)
        {state = calcState();}

        super.draw(g);
        // Restore the original transformation
        g.setTransform(originalTransform);
    }



    /**
     * Calculates the rotation angle of the player based on its current movement direction.
     *
     * @return The angle in degrees for the player's current direction.
     */
    private int calcAngle() {
        int newAngle = angle;
        if (dx == maxSpeed && dy == maxSpeed) {
            newAngle = 135;
        } else if (dx == maxSpeed && dy == -maxSpeed) {
            newAngle = 45;
        } else if (dx == -maxSpeed && dy == maxSpeed) {
            newAngle = 225;
        } else if (dx == -maxSpeed && dy == -maxSpeed) {
            newAngle = 315;
        } else if (dx == 0 && dy == maxSpeed) {
            newAngle = 180;
        } else if (dx == 0 && dy == -maxSpeed) {
            newAngle = 0;
        } else if (dx == maxSpeed && dy == 0) {
            newAngle = 90;
        } else if (dx == -maxSpeed && dy == 0) {
            newAngle = 270;
        }
        return newAngle;
    }

    /**
     * Calculates the current state of the player based on its movement.
     *
     * @return The state of the player ("idle" or "walk").
     */
    private String calcState() {
        String newState = "idle";

        if (dx != 0 || dy != 0) {
            newState = "walk";
        }

        return newState;
    }

    /**
     * Sets the maximum speed of the player.
     *
     * @param maxSpeed The maximum speed to set.
     */
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Gets the maximum speed of the player.
     *
     * @return The maximum speed of the player.
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public void setIsFishing(Boolean isFishing) {
        this.isFishing = isFishing;
    }
}


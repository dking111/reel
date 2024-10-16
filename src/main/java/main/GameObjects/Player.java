package main.GameObjects;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

/**
 * Represents a player character in the game, capable of movement, rotation, and animation.
 * The player's state is determined by their movement and position.
 */
public class Player extends AnimatedSprite {
    private static final int DEFAULT_ANGLE = 0;
    private static final float LARGE_SHADOW_RADIUS = 75f;
    private static final float SMALL_SHADOW_RADIUS = 45f;
    private static final int SHADOW_Y_OFFSET = 30;
    private static final Color SHADOW_COLOR = new Color(0, 0, 0, 150);

    private int angle;
    private int maxSpeed;
    private Boolean isFishing;

    /**
     * Constructs a new {@code Player} object.
     *
     * @param x        The x-coordinate of the player.
     * @param y        The y-coordinate of the player.
     * @param w        The width of the player.
     * @param h        The height of the player.
     * @param path     The file path to the player's sprite image.
     * @param maxSpeed The maximum speed of the player.
     */
    public Player(int x, int y, int w, int h, String path, int maxSpeed) {
        super(x, y, w, h, path, 5);
        this.angle = DEFAULT_ANGLE;
        this.maxSpeed = maxSpeed;
        this.isFishing = false;
        this.state = "idle_front";
    }

    /**
     * Draws the player with appropriate rotation based on movement direction and
     * displays the player's shadow on the screen.
     *
     * @param g The Graphics2D context used for drawing the player.
     */
    @Override
    public void draw(Graphics2D g) {
        angle = calcAngle();
        if (!isFishing) {
            String newState = calcState();
            if (!newState.equals(state)) {
                refreshAnimation();
            }
            state = newState;
        }
        drawShadow(g);
        super.draw(g);
    }

    /**
     * Draws a shadow beneath the player based on their size.
     *
     * @param g The Graphics2D context used for drawing the shadow.
     */
    public void drawShadow(Graphics2D g) {
        float radius = (this.getW() != 250) ? SMALL_SHADOW_RADIUS : LARGE_SHADOW_RADIUS;
        Point2D.Float shadowPosition = new Point2D.Float(this.getX() + this.getW() / 2, this.getY() + this.getH() + SHADOW_Y_OFFSET);

        Arc2D.Double arc = new Arc2D.Double(shadowPosition.getX() - radius, shadowPosition.getY() - radius, 2 * radius, radius, 0, 360, Arc2D.PIE);
        g.setPaint(SHADOW_COLOR);
        g.fill(arc);
    }

    /**
     * Calculates the rotation angle of the player based on their movement direction.
     *
     * @return The angle (in degrees) representing the player's movement direction.
     */
    private int calcAngle() {
        if (dx == maxSpeed && dy == maxSpeed) return 135;
        if (dx == maxSpeed && dy == -maxSpeed) return 45;
        if (dx == -maxSpeed && dy == maxSpeed) return 225;
        if (dx == -maxSpeed && dy == -maxSpeed) return 315;
        if (dx == 0 && dy == maxSpeed) return 180;
        if (dx == 0 && dy == -maxSpeed) return 0;
        if (dx == maxSpeed && dy == 0) return 90;
        if (dx == -maxSpeed && dy == 0) return 270;
        return angle;
    }

    /**
     * Determines the player's state based on their current movement and angle.
     *
     * @return A string representing the player's current animation state (e.g., idle, walk).
     */
    private String calcState() {
        String prefix = (dx != 0 || dy != 0) ? "walk" : "idle";
        String suffix = "_front";
        switch (angle) {
            case 0: suffix = "_back"; break;
            case 90: suffix = "_right"; break;
            case 180: suffix = "_front"; break;
            case 270: suffix = "_left"; break;
            case 45: suffix = "_top_right"; break;
            case 135: suffix = "_bottom_right"; break;
            case 225: suffix = "_bottom_left"; break;
            case 315: suffix = "_top_left"; break;
        }
        return prefix + suffix;
    }

    /**
     * Sets the maximum speed of the player.
     *
     * @param maxSpeed The maximum speed to be set.
     */
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * Returns the maximum speed of the player.
     *
     * @return The maximum speed of the player.
     */
    public int getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Sets whether the player is currently fishing.
     *
     * @param isFishing Boolean indicating the player's fishing state.
     */
    public void setIsFishing(Boolean isFishing) {
        this.isFishing = isFishing;
    }
}

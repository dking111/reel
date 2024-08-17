package main;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a basic drawable and movable sprite in the game.
 * This class provides functionality for position, size, movement, and rendering.
 */
public class Sprite {
    protected int x, y, w, h; // Position and dimensions of the sprite
    protected int dx, dy;    // Velocity of the sprite
    protected boolean isVisible;

    /**
     * Constructs a new {@code Sprite} with the specified position and dimensions.
     *
     * @param x The x-coordinate of the sprite's position.
     * @param y The y-coordinate of the sprite's position.
     * @param w The width of the sprite.
     * @param h The height of the sprite.
     */
    public Sprite(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        dx = 0;
        dy = 0;
        isVisible = true;
    }

    /**
     * Draws the sprite on the specified {@code Graphics2D} context.
     *
     * @param g The {@code Graphics2D} context on which to draw the sprite.
     */
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE); // Set the color for the sprite
        g.fillRect(x, y, w, h); // Draw the sprite as a filled rectangle    
            
    }

    /**
     * Moves the sprite by the specified velocity.
     *
     * @param dx The change in x-coordinate (horizontal movement).
     * @param dy The change in y-coordinate (vertical movement).
     */
    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }


    /**
     * This method is called when the sprite collides with another object.
     * It is intended to be overridden by subclasses to provide specific collision handling.
     */
    public void collided() {
        // Empty method for polymorphism
    }

    // Getters and setters

    /**
     * Gets the x-coordinate of the sprite's position.
     *
     * @return The x-coordinate of the sprite's position.
     */
    public int getX() { return x; }

    /**
     * Sets the x-coordinate of the sprite's position.
     *
     * @param x The new x-coordinate of the sprite's position.
     */
    public void setX(int x) { this.x = x; }

    /**
     * Gets the y-coordinate of the sprite's position.
     *
     * @return The y-coordinate of the sprite's position.
     */
    public int getY() { return y; }

    /**
     * Sets the y-coordinate of the sprite's position.
     *
     * @param y The new y-coordinate of the sprite's position.
     */
    public void setY(int y) { this.y = y; }

    /**
     * Gets the width of the sprite.
     *
     * @return The width of the sprite.
     */
    public int getW() { return w; }

    /**
     * Sets the width of the sprite.
     *
     * @param w The new width of the sprite.
     */
    public void setW(int w) { this.w = w; }

    /**
     * Gets the height of the sprite.
     *
     * @return The height of the sprite.
     */
    public int getH() { return h; }

    /**
     * Sets the height of the sprite.
     *
     * @param h The new height of the sprite.
     */
    public void setH(int h) { this.h = h; }

    /**
     * Gets the x-component of the sprite's velocity.
     *
     * @return The x-component of the sprite's velocity.
     */
    public int getDx() { return dx; }

    /**
     * Sets the x-component of the sprite's velocity.
     *
     * @param dx The new x-component of the sprite's velocity.
     */
    public void setDx(int dx) { this.dx = dx; }

    /**
     * Gets the y-component of the sprite's velocity.
     *
     * @return The y-component of the sprite's velocity.
     */
    public int getDy() { return dy; }

    /**
     * Sets the y-component of the sprite's velocity.
     *
     * @param dy The new y-component of the sprite's velocity.
     */
    public void setDy(int dy) { this.dy = dy; }

    public Boolean getIsVisbile(){
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible){
        this.isVisible =isVisible;
    }
}

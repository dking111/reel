package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The {@code Sprite} class represents a basic drawable and movable object in the game.
 * It includes position, size, visibility, and movement capabilities, along with methods to render
 * the sprite on a {@code Graphics2D} object and handle collisions.
 */
public class Sprite {

    private static final Color DEFAULT_COLOR = Color.BLUE;  
    protected int x, y; 
    protected int w, h; 
    protected int dx, dy; 
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
        this.dx = 0;  
        this.dy = 0;  
        this.isVisible = true;  
    }

    /**
     * Draws the sprite on the specified {@code Graphics2D} context.
     * By default, the sprite is drawn as a filled rectangle in the {@link DEFAULT_COLOR}.
     *
     * @param g The {@code Graphics2D} context on which to draw the sprite.
     */
    public void draw(Graphics2D g) {
            g.setColor(DEFAULT_COLOR);   
            g.fillRect(x, y, w, h);      
        
    }

    /**
     * Moves the sprite by updating its x and y coordinates based on the given velocity.
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
     * It is intended to be overridden by subclasses to provide specific collision handling logic.
     */
    public void collided() {
        return;
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

    /**
     * Checks if the sprite is currently visible.
     *
     * @return {@code true} if the sprite is visible, {@code false} otherwise.
     */
    public boolean getIsVisible() {
        return isVisible;
    }

    /**
     * Sets the visibility of the sprite.
     *
     * @param isVisible {@code true} to make the sprite visible, {@code false} to hide it.
     */
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}

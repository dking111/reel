package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Main;

/**
 * Represents a fishing spot in the game where players can engage in fishing activities.
 * The spot is visually represented as a colored rectangle.
 */
public class Shelf extends Sprite {

    /**
     * Constructs a FishingSpot instance with the specified parameters.
     *
     * @param x The x-coordinate of the fishing spot.
     * @param y The y-coordinate of the fishing spot.
     * @param w The width of the fishing spot.
     * @param h The height of the fishing spot.
     */
    public Shelf(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Draws the fishing spot on the screen.
     * The spot is rendered as a filled rectangle using a predefined color.
     *
     * @param g The Graphics2D object used for drawing the fishing spot.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.PINK); // Set the color for the fishing spot
        g.fillRect(x, y, w, h); // Draw the fishing spot as a filled rectangle using inherited fields
    }

    /**
     * Marks the fishing spot as being active for fishing when a collision occurs.
     */
    @Override
    public void collided() {
        Main.switchToGUI("shelf");
    }


        
    }




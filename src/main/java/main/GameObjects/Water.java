package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The {@code Water} class represents the area a fish can spawn in, extending the {@code Sprite} class.
 * It provides functionality for rendering a simple blue rectangle representing the water on the screen.
 */
public class Water extends Sprite {

    /**
     * Constructs a {@code Water} object with the specified position and dimensions.
     *
     * @param x The X coordinate of the top-left corner of the water.
     * @param y The Y coordinate of the top-left corner of the water.
     * @param w The width of the water.
     * @param h The height of the water.
     */
    public Water(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Draws the water on the screen using the specified {@code Graphics2D} object.
     * The water is represented by a blue rectangle.
     *
     * @param g The {@code Graphics2D} object to render the water.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE); 
        g.drawRect(x, y, w, h); 
    }
}

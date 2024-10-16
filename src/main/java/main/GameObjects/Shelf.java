package main.GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Main;

/**
 * Represents a shelf in the game where interactions can occur.
 * The shelf is displayed as a colored rectangle.
 */
public class Shelf extends Sprite {

    // Constant for the color of the shelf
    private static final Color DEFAULT_COLOR = Color.PINK;

    /**
     * Constructs a Shelf instance with the specified parameters.
     *
     * @param x The x-coordinate of the shelf.
     * @param y The y-coordinate of the shelf.
     * @param w The width of the shelf.
     * @param h The height of the shelf.
     */
    public Shelf(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /**
     * Draws the shelf on the screen.
     *
     * @param g The Graphics2D object used for drawing the shelf.
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(DEFAULT_COLOR);
        g.fillRect(x, y, w, h);
    }

    /**
     * Handles collision by switching to the shelf GUI.
     */
    @Override
    public void collided() {
        Main.switchToGUI("shelf");
    }
}

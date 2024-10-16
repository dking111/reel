package main.GUI;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Represents a temporary text that appears on the screen for a limited duration.
 * Once the duration expires, the text is no longer drawn.
 */
public class TextTemp extends Text {
    private int duration;  
    private boolean done;  

    /**
     * Constructs a temporary text object.
     *
     * @param x        The x-coordinate of the text.
     * @param y        The y-coordinate of the text.
     * @param fontSize The font size of the text.
     * @param text     The actual text to display.
     * @param color    The color of the text.
     * @param duration The duration for which the text should be displayed (in frames).
     */
    public TextTemp(int x, int y, int fontSize, String text, Color color, int duration) {
        super(x, y, fontSize, text, color);
        this.duration = duration;
        this.done = false;
    }

    /**
     * Draws the text if it is still within the display duration.
     *
     * @param g The Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        if (!done) {
            super.draw(g);
        }
    }

    /**
     * Updates the text's state. Decreases the duration and sets done to true when the duration expires.
     */
    @Override
    public void update() {
        if (!done) {
            duration--;
            if (duration <= 0) {
                done = true;
            }
        }
    }
}

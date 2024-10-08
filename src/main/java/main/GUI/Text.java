package main.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Represents a text element to be drawn on the screen.
 * Provides methods to set the text content, color, font size, and position.
 */
public class Text {
    private String text;
    private Color color;
    private int fontSize;
    private int x, y;

    /**
     * Constructs a Text instance with the specified parameters.
     *
     * @param x The x-coordinate where the text will be drawn.
     * @param y The y-coordinate where the text will be drawn.
     * @param fontSize The size of the font used to render the text.
     * @param text The content of the text to be displayed.
     * @param color The color of the text.
     */
    public Text(int x, int y, int fontSize, String text, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
    }

    /**
     * Draws the text on the provided Graphics2D context.
     *
     * @param g2d The Graphics2D object used for drawing the text.
     */
    public void draw(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.setColor(color);

        // Draw the string at the specified coordinates (x, y)
        g2d.drawString(text, x, y);
    }

    public void update(){
        
    }
}

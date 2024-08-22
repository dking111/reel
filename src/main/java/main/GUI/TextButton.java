package main.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Represents a button with text that can be displayed on the screen.
 * The button changes appearance based on its state (inactive, hover, active).
 * The text is centered within the button.
 */
public class TextButton extends Button {
    private String text;
    private int fontSize;
    private Color textColor;

    /**
     * Constructs a TextButton instance with the specified parameters.
     *
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param w The width of the button.
     * @param h The height of the button.
     * @param inactiveColor The color of the button when inactive.
     * @param hoverColor The color of the button when hovered.
     * @param activeColor The color of the button when active (pressed).
     * @param action The logic to execute when the button is pressed.
     * @param arcWidth The width of the arc used to round the corners of the button.
     * @param arcHeight The height of the arc used to round the corners of the button.
     * @param text The text to be displayed on the button.
     * @param fontSize The size of the font used to render the text.
     * @param textColor The color of the text.
     */
    public TextButton(int x, int y, int w, int h, Color inactiveColor, Color hoverColor, Color activeColor, Runnable action, int arcWidth, int arcHeight, String text, int fontSize, Color textColor) {
        super(x, y, w, h, inactiveColor, hoverColor, activeColor, action, arcWidth, arcHeight);
        this.text = text;
        this.fontSize = fontSize;
        this.textColor = textColor;
    }

    /**
     * Draws the button and its text on the provided Graphics2D context.
     * The button's appearance is determined by its current state (inactive, hover, active).
     * The text is centered within the button.
     *
     * @param g2d The Graphics2D object used for drawing the button and text.
     */
    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);

        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.setColor(textColor);

        // Calculate the font metrics to get the height of the text
        Font font = g2d.getFont();
        FontMetrics metrics = g2d.getFontMetrics(font);

        // Calculate the position of the text to be centered both horizontally and vertically within the button
        int textX = x + (w - metrics.stringWidth(text)) / 2;
        int textY = y + ((h - metrics.getHeight()) / 2) + metrics.getAscent();

        // Draw the string at the calculated coordinates
        g2d.drawString(text, textX, textY);
    }
}

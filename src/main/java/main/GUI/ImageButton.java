package main.GUI;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a button with an image and rounded corners.
 * It extends the Button class to add image rendering functionality.
 */
public class ImageButton extends Button {
    private String path;
    private Image image;

    /**
     * Constructs an ImageButton instance with the specified parameters.
     *
     * @param x The x-coordinate of the button.
     * @param y The y-coordinate of the button.
     * @param w The width of the button.
     * @param h The height of the button.
     * @param inactiveColor The color of the button when inactive.
     * @param hoverColor The color of the button when hovered.
     * @param activeColor The color of the button when active (pressed).
     * @param action The logic to execute when the button is pressed.
     * @param arcWidth The width of the arc used to round the corners.
     * @param arcHeight The height of the arc used to round the corners.
     * @param path The file path to the image to be displayed on the button (relative to the resources folder).
     */
    public ImageButton(int x, int y, int w, int h, Color inactiveColor, Color hoverColor, Color activeColor, Runnable action, int arcWidth, int arcHeight, String path) {
        super(x, y, w, h, inactiveColor, hoverColor, activeColor, action, arcWidth, arcHeight);
        this.path = path;
        this.image = loadImage();
    }

    /**
     * Draws the button on the screen with its current state color and image.
     * The image is drawn inside the button with rounded corners.
     *
     * @param g2d The Graphics2D object used for drawing the button.
     */
    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, w, h, arcWidth, arcHeight);
        g2d.setClip(roundedRectangle);
        if (image != null) {
            g2d.drawImage(image, x, y, w, h, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.drawString("Image not found", x + 10, y + h / 2);
        }
        g2d.setClip(null);
    }

    /**
     * Loads the image from the classpath (from within the JAR if needed).
     * 
     * @return The loaded Image object, or null if there was an error.
     */
    private Image loadImage() {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            } else {
                System.err.println("Resource not found: " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}

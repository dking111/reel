package main.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import main.GameObjects.Sprite;

/**
 * Represents a graphical sprite that displays an image.
 * It extends the Sprite class and adds functionality to load and draw an image.
 */
public class ImageSprite extends Sprite {
    private Image image;
    private String path;

    /**
     * Constructs an ImageSprite instance with the specified parameters.
     *
     * @param x The x-coordinate of the sprite.
     * @param y The y-coordinate of the sprite.
     * @param w The width of the sprite.
     * @param h The height of the sprite.
     * @param path The file path to the image to be displayed (relative to resources folder).
     */
    public ImageSprite(int x, int y, int w, int h, String path) {
        super(x, y, w, h);
        this.path = path;
        image = loadImage();
    }

    /**
     * Loads the image from the classpath (from within the JAR if needed).
     * 
     * @return The loaded Image object, or null if there was an error.
     */
    private Image loadImage() {
        try {
            // Load the resource using class loader
            InputStream is = getClass().getResourceAsStream(path);

            if (is != null) {
                // Use ImageIO to read the image from the InputStream
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

    /**
     * Draws the image on the screen.
     * This method is called by the rendering system to display the sprite.
     *
     * @param g2d The Graphics2D object used for drawing the image.
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (image != null) {
            g2d.drawImage(image, getX(), getY(), getW(), getH(), null);
        } else {
            // Fallback if the image cannot be loaded
            g2d.setColor(Color.RED);
            g2d.fillRect(getX(), getY(), getW(), getH());
            g2d.setColor(Color.BLACK);
            g2d.drawString("Image not found", getX() + 10, getY() + getH() / 2);
        }
    }
}

package main.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

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
     * @param path The file path to the image to be displayed.
     */
    public ImageSprite(int x, int y, int w, int h, String path) {
        super(x, y, w, h);
        this.path = path;
        image = loadImage();
    }

    /**
     * Loads the image from the specified file path.
     * 
     * @return The loaded Image object, or null if there was an error.
     */
    private Image loadImage() {
        try {
            File file = new File(path);
            if (file.isFile()) {
                ImageIcon icon = new ImageIcon(file.getPath());
                return icon.getImage();
            } else {
                System.err.println("File not found: " + path);
                return null;
            }
        } catch (Exception e) {
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
        g2d.drawImage(image, getX(), getY(), getW(), getH(), null);
    }
}

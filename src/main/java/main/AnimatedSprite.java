package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Represents an animated sprite that can switch between different animations based on its state.
 * The sprite's animations are loaded from a directory structure where each subdirectory contains
 * images for a particular animation state.
 */
public class AnimatedSprite extends Sprite {
    protected List<Image> currentAnimation; // List of images representing the current animation
    protected Dictionary<String, List<Image>> animationDict; // Dictionary of all animations by state
    protected int currentFrame; // Current frame index in the animation
    protected int frameCounter; // Counter to control frame rate
    protected String state; // Current animation state (e.g., "idle", "run")
    protected int animationSpeed; // Speed of animation in frames per second

    /**
     * Constructs an AnimatedSprite object with the specified position, size, and animation directory.
     * 
     * @param x the x-coordinate of the sprite.
     * @param y the y-coordinate of the sprite.
     * @param w the width of the sprite.
     * @param h the height of the sprite.
     * @param path the path to the directory containing animation subdirectories.
     */
    public AnimatedSprite(int x, int y, int w, int h, String path) {
        super(x, y, w, h);
        currentFrame = 0;
        state = "idle";
        animationDict = loadAnimation(path);
        currentAnimation = animationDict.get("idle");
        frameCounter = 0;
        animationSpeed = 60; // Default speed is 1 frame per second (assuming 60 FPS)
    }

    /**
     * Draws the current frame of the animation at the sprite's position.
     * 
     * @param g the Graphics2D object used for drawing.
     */
    @Override
    public void draw(Graphics2D g) {
        currentAnimation = animationDict.get(state);
        g.drawImage(currentAnimation.get(currentFrame), getX(), getY(), getW(), getH(), null);
        
        // Update frame if frameCounter is at the update interval
        if (frameCounter == 1) {
            currentFrame = (currentFrame + 1) % currentAnimation.size();
        }

        // Increment frameCounter and reset it when it reaches the animation speed
        frameCounter = (frameCounter + 1) % animationSpeed;
    }

    /**
     * Loads animations from the specified directory path. Each subdirectory represents a different
     * animation state and contains images that make up the frames of that animation.
     * 
     * @param path the path to the directory containing animation subdirectories.
     * @return a dictionary mapping animation states to lists of images.
     */
    public Dictionary<String, List<Image>> loadAnimation(String path) {
        Dictionary<String, List<Image>> dict = new Hashtable<>();
        File folder = new File(path);
        File[] listOfSubFolders = folder.listFiles();

        for (File directory : listOfSubFolders) {
            List<Image> animation = new ArrayList<>();
            
            if (directory.isDirectory()) {
                File subFolder = new File(path + "\\" + directory.getName());
                File[] listOfFiles = subFolder.listFiles();

                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        ImageIcon icon = new ImageIcon(file.getPath());
                        animation.add(icon.getImage());
                    }
                }
            }
            dict.put(directory.getName(), animation);
        }

        return dict;
    }
}
